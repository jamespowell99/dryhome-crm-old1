package com.dryhome.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dryhome.domain.OrderItem;
import com.dryhome.repository.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderItem.
 */
@RestController
@RequestMapping("/api")
public class OrderItemResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemResource.class);

    @Inject
    private OrderItemRepository orderItemRepository;

    /**
     * POST  /orderItems -> Create a new orderItem.
     */
    @RequestMapping(value = "/orderItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OrderItem orderItem) throws URISyntaxException {
        log.debug("REST request to save OrderItem : {}", orderItem);
        if (orderItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new orderItem cannot already have an ID").build();
        }
        orderItemRepository.save(orderItem);
        return ResponseEntity.created(new URI("/api/orderItems/" + orderItem.getId())).build();
    }

    /**
     * PUT  /orderItems -> Updates an existing orderItem.
     */
    @RequestMapping(value = "/orderItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OrderItem orderItem) throws URISyntaxException {
        log.debug("REST request to update OrderItem : {}", orderItem);
        if (orderItem.getId() == null) {
            return create(orderItem);
        }
        orderItemRepository.save(orderItem);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orderItems -> get all the orderItems.
     */
    @RequestMapping(value = "/orderItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrderItem> getAll() {
        log.debug("REST request to get all OrderItems");
        return orderItemRepository.findAll();
    }

    /**
     * GET  /orderItems/:id -> get the "id" orderItem.
     */
    @RequestMapping(value = "/orderItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderItem> get(@PathVariable Long id) {
        log.debug("REST request to get OrderItem : {}", id);
        return Optional.ofNullable(orderItemRepository.findOne(id))
            .map(orderItem -> new ResponseEntity<>(
                orderItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderItems/:id -> delete the "id" orderItem.
     */
    @RequestMapping(value = "/orderItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OrderItem : {}", id);
        orderItemRepository.delete(id);
    }
}
