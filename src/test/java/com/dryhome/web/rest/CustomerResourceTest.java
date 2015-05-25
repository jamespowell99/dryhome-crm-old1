package com.dryhome.web.rest;

import com.dryhome.Application;
import com.dryhome.domain.Customer;
import com.dryhome.repository.CustomerRepository;

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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_FIRST = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_FIRST = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_SURNAME = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_SURNAME = "UPDATED_TEXT";
    private static final String DEFAULT_TEL = "SAMPLE_TEXT";
    private static final String UPDATED_TEL = "UPDATED_TEXT";
    private static final String DEFAULT_MOB = "SAMPLE_TEXT";
    private static final String UPDATED_MOB = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS3 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS3 = "UPDATED_TEXT";
    private static final String DEFAULT_TOWN = "SAMPLE_TEXT";
    private static final String UPDATED_TOWN = "UPDATED_TEXT";
    private static final String DEFAULT_POST_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_POST_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_PRODUCTS = "SAMPLE_TEXT";
    private static final String UPDATED_PRODUCTS = "UPDATED_TEXT";
    private static final String DEFAULT_INTERESTED = "SAMPLE_TEXT";
    private static final String UPDATED_INTERESTED = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_PAID = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_PAID = BigDecimal.ONE;
    private static final String DEFAULT_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_NOTES = "UPDATED_TEXT";

    @Inject
    private CustomerRepository customerRepository;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setName(DEFAULT_NAME);
        customer.setContactTitle(DEFAULT_CONTACT_TITLE);
        customer.setContactFirst(DEFAULT_CONTACT_FIRST);
        customer.setContactSurname(DEFAULT_CONTACT_SURNAME);
        customer.setTel(DEFAULT_TEL);
        customer.setMob(DEFAULT_MOB);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setAddress1(DEFAULT_ADDRESS1);
        customer.setAddress2(DEFAULT_ADDRESS2);
        customer.setAddress3(DEFAULT_ADDRESS3);
        customer.setTown(DEFAULT_TOWN);
        customer.setPostCode(DEFAULT_POST_CODE);
        customer.setProducts(DEFAULT_PRODUCTS);
        customer.setInterested(DEFAULT_INTERESTED);
        customer.setPaid(DEFAULT_PAID);
        customer.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getContactTitle()).isEqualTo(DEFAULT_CONTACT_TITLE);
        assertThat(testCustomer.getContactFirst()).isEqualTo(DEFAULT_CONTACT_FIRST);
        assertThat(testCustomer.getContactSurname()).isEqualTo(DEFAULT_CONTACT_SURNAME);
        assertThat(testCustomer.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testCustomer.getMob()).isEqualTo(DEFAULT_MOB);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testCustomer.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testCustomer.getAddress3()).isEqualTo(DEFAULT_ADDRESS3);
        assertThat(testCustomer.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testCustomer.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testCustomer.getProducts()).isEqualTo(DEFAULT_PRODUCTS);
        assertThat(testCustomer.getInterested()).isEqualTo(DEFAULT_INTERESTED);
        assertThat(testCustomer.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testCustomer.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contactTitle").value(hasItem(DEFAULT_CONTACT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].contactFirst").value(hasItem(DEFAULT_CONTACT_FIRST.toString())))
                .andExpect(jsonPath("$.[*].contactSurname").value(hasItem(DEFAULT_CONTACT_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
                .andExpect(jsonPath("$.[*].mob").value(hasItem(DEFAULT_MOB.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS3.toString())))
                .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
                .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
                .andExpect(jsonPath("$.[*].products").value(hasItem(DEFAULT_PRODUCTS.toString())))
                .andExpect(jsonPath("$.[*].interested").value(hasItem(DEFAULT_INTERESTED.toString())))
                .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.intValue())))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contactTitle").value(DEFAULT_CONTACT_TITLE.toString()))
            .andExpect(jsonPath("$.contactFirst").value(DEFAULT_CONTACT_FIRST.toString()))
            .andExpect(jsonPath("$.contactSurname").value(DEFAULT_CONTACT_SURNAME.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.mob").value(DEFAULT_MOB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS3.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.products").value(DEFAULT_PRODUCTS.toString()))
            .andExpect(jsonPath("$.interested").value(DEFAULT_INTERESTED.toString()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.intValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        customer.setName(UPDATED_NAME);
        customer.setContactTitle(UPDATED_CONTACT_TITLE);
        customer.setContactFirst(UPDATED_CONTACT_FIRST);
        customer.setContactSurname(UPDATED_CONTACT_SURNAME);
        customer.setTel(UPDATED_TEL);
        customer.setMob(UPDATED_MOB);
        customer.setEmail(UPDATED_EMAIL);
        customer.setAddress1(UPDATED_ADDRESS1);
        customer.setAddress2(UPDATED_ADDRESS2);
        customer.setAddress3(UPDATED_ADDRESS3);
        customer.setTown(UPDATED_TOWN);
        customer.setPostCode(UPDATED_POST_CODE);
        customer.setProducts(UPDATED_PRODUCTS);
        customer.setInterested(UPDATED_INTERESTED);
        customer.setPaid(UPDATED_PAID);
        customer.setNotes(UPDATED_NOTES);
        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getContactTitle()).isEqualTo(UPDATED_CONTACT_TITLE);
        assertThat(testCustomer.getContactFirst()).isEqualTo(UPDATED_CONTACT_FIRST);
        assertThat(testCustomer.getContactSurname()).isEqualTo(UPDATED_CONTACT_SURNAME);
        assertThat(testCustomer.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testCustomer.getMob()).isEqualTo(UPDATED_MOB);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testCustomer.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testCustomer.getAddress3()).isEqualTo(UPDATED_ADDRESS3);
        assertThat(testCustomer.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testCustomer.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustomer.getProducts()).isEqualTo(UPDATED_PRODUCTS);
        assertThat(testCustomer.getInterested()).isEqualTo(UPDATED_INTERESTED);
        assertThat(testCustomer.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testCustomer.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }


}
