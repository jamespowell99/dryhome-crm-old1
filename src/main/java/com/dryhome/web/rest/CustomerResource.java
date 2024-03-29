package com.dryhome.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dryhome.domain.Customer;
import com.dryhome.repository.CustomerRepository;
import com.dryhome.service.MergeService;
import com.dryhome.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private MergeService mergeService;

    @RequestMapping(value = "/doc/{customerId}/{templateName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public void getDoc(@PathVariable Long customerId, @PathVariable String templateName, HttpServletResponse response) {
        mergeService.performMerge(customerRepository.findOne(customerId), templateName, response);
    }


    /**
     * POST  /customers -> Create a new customer.
     */
    @RequestMapping(value = "/customers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new customer cannot already have an ID").build();
        }
        customerRepository.save(customer);
        return ResponseEntity.created(new URI("/api/customers/" + customer.getId())).build();
    }

    /**
     * PUT  /customers -> Updates an existing customer.
     */
    @RequestMapping(value = "/customers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customer);
        if (customer.getId() == null) {
            return create(customer);
        }
        customerRepository.save(customer);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /customers -> get all the customers.
     */
    @RequestMapping(value = "/customers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Customer>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                 @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Customer> page = customerRepository.findAll(PaginationUtil.generatePageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id")));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Customer>> search(@RequestParam(value = "searchTerm", required = true) String searchTerm, @RequestParam(value = "page", required = false) Integer offset,
                                                 @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {
        Page<Customer> page = customerRepository.findByMultipleFields(searchTerm, PaginationUtil.generatePageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id")));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers/search?searchTerm=" + searchTerm, offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customers/:id -> get the "id" customer.
     */
    @RequestMapping(value = "/customers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Customer> get(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        return Optional.ofNullable(customerRepository.findOne(id))
            .map(customer -> new ResponseEntity<>(
                customer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customers/:id -> delete the "id" customer.
     */
    @RequestMapping(value = "/customers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
