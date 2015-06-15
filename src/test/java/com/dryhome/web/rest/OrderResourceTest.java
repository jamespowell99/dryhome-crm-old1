package com.dryhome.web.rest;

import com.dryhome.Application;
import com.dryhome.domain.Order;
import com.dryhome.repository.OrderRepository;

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
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderResource REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderResourceTest {

    private static final String DEFAULT_ORDER_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_ORDER_NUMBER = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_ORDER_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_ORDER_DATE = new LocalDate();

    private static final LocalDate DEFAULT_DISPATCH_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DISPATCH_DATE = new LocalDate();

    private static final LocalDate DEFAULT_INVOICE_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = new LocalDate();
    private static final String DEFAULT_PLACED_BY = "SAMPLE_TEXT";
    private static final String UPDATED_PLACED_BY = "UPDATED_TEXT";
    private static final String DEFAULT_METHOD = "SAMPLE_TEXT";
    private static final String UPDATED_METHOD = "UPDATED_TEXT";
    private static final String DEFAULT_INVOICE_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_INVOICE_NOTES1 = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE_NOTES1 = "UPDATED_TEXT";
    private static final String DEFAULT_INVOICE_NOTES2 = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE_NOTES2 = "UPDATED_TEXT";
    private static final String DEFAULT_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_NOTES = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_PAYMENT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = new LocalDate();
    private static final String DEFAULT_PAYMENT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_PAYMENT_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_PAYMENT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_PAYMENT_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_PAYMENT_AMOUNT = "SAMPLE_TEXT";
    private static final String UPDATED_PAYMENT_AMOUNT = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_VAT_RATE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_VAT_RATE = BigDecimal.ONE;

    @Inject
    private OrderRepository orderRepository;

    private MockMvc restOrderMockMvc;

    private Order order;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderResource orderResource = new OrderResource();
        ReflectionTestUtils.setField(orderResource, "orderRepository", orderRepository);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource).build();
    }

    @Before
    public void initTest() {
        order = new Order();
        order.setOrderNumber(DEFAULT_ORDER_NUMBER);
        order.setOrderDate(DEFAULT_ORDER_DATE);
        order.setDispatchDate(DEFAULT_DISPATCH_DATE);
        order.setInvoiceDate(DEFAULT_INVOICE_DATE);
        order.setPlacedBy(DEFAULT_PLACED_BY);
        order.setMethod(DEFAULT_METHOD);
        order.setInvoiceNumber(DEFAULT_INVOICE_NUMBER);
        order.setInvoiceNotes1(DEFAULT_INVOICE_NOTES1);
        order.setInvoiceNotes2(DEFAULT_INVOICE_NOTES2);
        order.setNotes(DEFAULT_NOTES);
        order.setPaymentDate(DEFAULT_PAYMENT_DATE);
        order.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
        order.setPaymentType(DEFAULT_PAYMENT_TYPE);
        order.setPaymentAmount(DEFAULT_PAYMENT_AMOUNT);
        order.setVatRate(DEFAULT_VAT_RATE);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orders.get(orders.size() - 1);
        assertThat(testOrder.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrder.getDispatchDate()).isEqualTo(DEFAULT_DISPATCH_DATE);
        assertThat(testOrder.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testOrder.getPlacedBy()).isEqualTo(DEFAULT_PLACED_BY);
        assertThat(testOrder.getMethod()).isEqualTo(DEFAULT_METHOD);
        assertThat(testOrder.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testOrder.getInvoiceNotes1()).isEqualTo(DEFAULT_INVOICE_NOTES1);
        assertThat(testOrder.getInvoiceNotes2()).isEqualTo(DEFAULT_INVOICE_NOTES2);
        assertThat(testOrder.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testOrder.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrder.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testOrder.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testOrder.getVatRate()).isEqualTo(DEFAULT_VAT_RATE);
    }

    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(orderRepository.findAll()).hasSize(0);
        // set the field null
        order.setOrderNumber(null);

        // Create the Order, which fails.
        restOrderMockMvc.perform(post("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orders
        restOrderMockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].dispatchDate").value(hasItem(DEFAULT_DISPATCH_DATE.toString())))
                .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
                .andExpect(jsonPath("$.[*].placedBy").value(hasItem(DEFAULT_PLACED_BY.toString())))
                .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())))
                .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].invoiceNotes1").value(hasItem(DEFAULT_INVOICE_NOTES1.toString())))
                .andExpect(jsonPath("$.[*].invoiceNotes2").value(hasItem(DEFAULT_INVOICE_NOTES2.toString())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
                .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.toString())))
                .andExpect(jsonPath("$.[*].vatRate").value(hasItem(DEFAULT_VAT_RATE.intValue())));
    }

    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.dispatchDate").value(DEFAULT_DISPATCH_DATE.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.placedBy").value(DEFAULT_PLACED_BY.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.invoiceNotes1").value(DEFAULT_INVOICE_NOTES1.toString()))
            .andExpect(jsonPath("$.invoiceNotes2").value(DEFAULT_INVOICE_NOTES2.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.toString()))
            .andExpect(jsonPath("$.vatRate").value(DEFAULT_VAT_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

		int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        order.setOrderNumber(UPDATED_ORDER_NUMBER);
        order.setOrderDate(UPDATED_ORDER_DATE);
        order.setDispatchDate(UPDATED_DISPATCH_DATE);
        order.setInvoiceDate(UPDATED_INVOICE_DATE);
        order.setPlacedBy(UPDATED_PLACED_BY);
        order.setMethod(UPDATED_METHOD);
        order.setInvoiceNumber(UPDATED_INVOICE_NUMBER);
        order.setInvoiceNotes1(UPDATED_INVOICE_NOTES1);
        order.setInvoiceNotes2(UPDATED_INVOICE_NOTES2);
        order.setNotes(UPDATED_NOTES);
        order.setPaymentDate(UPDATED_PAYMENT_DATE);
        order.setPaymentStatus(UPDATED_PAYMENT_STATUS);
        order.setPaymentType(UPDATED_PAYMENT_TYPE);
        order.setPaymentAmount(UPDATED_PAYMENT_AMOUNT);
        order.setVatRate(UPDATED_VAT_RATE);
        restOrderMockMvc.perform(put("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orders.get(orders.size() - 1);
        assertThat(testOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrder.getDispatchDate()).isEqualTo(UPDATED_DISPATCH_DATE);
        assertThat(testOrder.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testOrder.getPlacedBy()).isEqualTo(UPDATED_PLACED_BY);
        assertThat(testOrder.getMethod()).isEqualTo(UPDATED_METHOD);
        assertThat(testOrder.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testOrder.getInvoiceNotes1()).isEqualTo(UPDATED_INVOICE_NOTES1);
        assertThat(testOrder.getInvoiceNotes2()).isEqualTo(UPDATED_INVOICE_NOTES2);
        assertThat(testOrder.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testOrder.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrder.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testOrder.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testOrder.getVatRate()).isEqualTo(UPDATED_VAT_RATE);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

		int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Get the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
