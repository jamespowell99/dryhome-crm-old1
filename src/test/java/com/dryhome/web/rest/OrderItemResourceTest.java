package com.dryhome.web.rest;

import com.dryhome.Application;
import com.dryhome.domain.OrderItem;
import com.dryhome.repository.OrderItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderItemResource REST controller.
 *
 * @see OrderItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderItemResourceTest {


    private static final BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_PRICE = BigDecimal.ONE;

    private static final Integer DEFAULT_QTY = 0;
    private static final Integer UPDATED_QTY = 1;
    private static final String DEFAULT_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_NOTES = "UPDATED_TEXT";

    private static final Integer DEFAULT_ORDER = 0;
    private static final Integer UPDATED_ORDER = 1;
    private static final String DEFAULT_SERIAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SERIAL_NUMBER = "UPDATED_TEXT";

    @Inject
    private OrderItemRepository orderItemRepository;

    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderItemResource orderItemResource = new OrderItemResource();
        ReflectionTestUtils.setField(orderItemResource, "orderItemRepository", orderItemRepository);
        this.restOrderItemMockMvc = MockMvcBuilders.standaloneSetup(orderItemResource).build();
    }

    @Before
    public void initTest() {
        orderItem = new OrderItem();
        orderItem.setPrice(DEFAULT_PRICE);
        orderItem.setQty(DEFAULT_QTY);
        orderItem.setNotes(DEFAULT_NOTES);
        orderItem.setOrder(DEFAULT_ORDER);
        orderItem.setSerialNumber(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem
        restOrderItemMockMvc.perform(post("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItems.get(orderItems.size() - 1);
        assertThat(testOrderItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrderItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testOrderItem.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testOrderItem.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testOrderItem.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(orderItemRepository.findAll()).hasSize(0);
        // set the field null
        orderItem.setPrice(null);

        // Create the OrderItem, which fails.
        restOrderItemMockMvc.perform(post("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(0);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(orderItemRepository.findAll()).hasSize(0);
        // set the field null
        orderItem.setQty(null);

        // Create the OrderItem, which fails.
        restOrderItemMockMvc.perform(post("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItems
        restOrderItemMockMvc.perform(get("/api/orderItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
                .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/orderItems/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/orderItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
		
		int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        orderItem.setPrice(UPDATED_PRICE);
        orderItem.setQty(UPDATED_QTY);
        orderItem.setNotes(UPDATED_NOTES);
        orderItem.setOrder(UPDATED_ORDER);
        orderItem.setSerialNumber(UPDATED_SERIAL_NUMBER);
        restOrderItemMockMvc.perform(put("/api/orderItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderItem)))
                .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItems.get(orderItems.size() - 1);
        assertThat(testOrderItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testOrderItem.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testOrderItem.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testOrderItem.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
		
		int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Get the orderItem
        restOrderItemMockMvc.perform(delete("/api/orderItems/{id}", orderItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderItem> orderItems = orderItemRepository.findAll();
        assertThat(orderItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
