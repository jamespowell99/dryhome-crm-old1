package com.dryhome.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dryhome.domain.Order;
import com.dryhome.domain.OrderItem;
import com.dryhome.repository.OrderItemRepository;
import com.dryhome.repository.OrderRepository;
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
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderItemRepository orderItemRepository;

    @Inject
    private MergeService mergeService;

    @RequestMapping(value = "/order/doc/{orderId}/{templateName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public void getDoc(@PathVariable Long orderId, @PathVariable String templateName, HttpServletResponse response) {
        mergeService.performMerge(orderRepository.findOne(orderId), templateName, response);
    }

    /**
     * POST  /orders -> Create a new order.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order : {}", order);
        if (order.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new order cannot already have an ID").build();
        }
        orderRepository.save(order);
        for(OrderItem item : order.getItems()) {
            item.setOrderId(order);
            orderItemRepository.save(item);
        }
        return ResponseEntity.created(new URI("/api/orders/" + order.getId())).build();
    }

    /**
     * PUT  /orders -> Updates an existing order.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to update Order : {}", order);
        if (order.getId() == null) {
            return create(order);
        }
        Order existingOrder = orderRepository.findOne(order.getId());
        for(OrderItem existingItem : existingOrder.getItems()) {
            Optional<OrderItem> orderItem = order.getItems().
                stream().
                filter(oi -> oi.getId() != null && oi.getId().equals(existingItem.getId())).findFirst();
            if (!orderItem.isPresent()) {
                orderItemRepository.delete(existingItem);
            }
        }
        for(OrderItem item : order.getItems()) {
            item.setOrderId(order);
            orderItemRepository.save(item);
        }
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /orders -> get all the orders.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Order>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                              @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Order> page = orderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit, new Sort(Sort.Direction.DESC, "orderDate")));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orders", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orders/:id -> get the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> get(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        return Optional.ofNullable(orderRepository.findOne(id))
            .map(order -> new ResponseEntity<>(
                order,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orders/:id -> delete the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderRepository.delete(id);
    }
}
