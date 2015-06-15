package com.dryhome.domain;


import com.powtechconsulting.mailmerge.DocControlMerger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void testFullItems() {
        Order order = order();
        order.setItems(Arrays.asList(orderItem(1), orderItem(2), orderItem(3), orderItem(4), orderItem(5), orderItem(6), orderItem(7),
            orderItem(8), orderItem(9), orderItem(10), orderItem(11), orderItem(12), orderItem(13), orderItem(14), orderItem(15)));

        OrderView orderView = order.toView();

        assertEquals(15, orderView.getOrderItems().size());
        OrderItemView itemView = orderView.getOrderItems().get(0);
        assertEquals("name1", itemView.getItem());
        assertEquals("1.00", itemView.getPrice());
        assertEquals("1", itemView.getQty());
        assertEquals("1.00", itemView.getTotal());
    }

    @Test
    public void testPartialItems() {
        Order order = order();
        order.setItems(Arrays.asList(orderItem(1), orderItem(2), orderItem(3)));

        OrderView orderView = order.toView();

        assertEquals(14, orderView.getOrderItems().size());
        OrderItemView itemView = orderView.getOrderItems().get(2);
        assertEquals("name3", itemView.getItem());
        assertEquals("3.00", itemView.getPrice());
        assertEquals("3", itemView.getQty());
        assertEquals("9.00", itemView.getTotal());

        itemView = orderView.getOrderItems().get(13);
        assertEquals(" ", itemView.getItem());
        assertEquals(" ", itemView.getPrice());
        assertEquals(" ", itemView.getQty());
        assertEquals(" ", itemView.getTotal());
    }

    @Test
    public void testEmptyItems() {
        OrderView orderView = order().toView();

        assertEquals(14, orderView.getOrderItems().size());

        OrderItemView itemView = orderView.getOrderItems().get(13);
        assertEquals(" ", itemView.getItem());
        assertEquals(" ", itemView.getPrice());
        assertEquals(" ", itemView.getQty());
        assertEquals(" ", itemView.getTotal());

        itemView = orderView.getOrderItems().get(0);
        assertEquals(" ", itemView.getItem());
        assertEquals(" ", itemView.getPrice());
        assertEquals(" ", itemView.getQty());
        assertEquals(" ", itemView.getTotal());
    }

    @Ignore
    @Test
    public void testMerge() throws IOException {
        String templateFile = "customer_invoice.docx";
        URL resource = this.getClass().getClassLoader().getResource("merge-docs/" + templateFile);
        String filename = resource.getFile();
        Order order = order();
        order.setItems(Arrays.asList(orderItem(1), orderItem(2), orderItem(3)));
        String xml = order.marshallToXml();
        System.out.println("xml = " + xml);
        byte[] bytes = new DocControlMerger().merge(filename, xml);
        FileOutputStream fos = new FileOutputStream(templateFile.replace(".", "_test_" + System.currentTimeMillis() + "."));
        fos.write(bytes);
        fos.close();
    }

    private Order order() {
        Order order = new Order();
        order.setCustomer(CustomerTest.customer());
        order.setInvoiceNumber("xyz987");
        order.setInvoiceDate(DateTime.now().toLocalDate());
        order.setVatRate(BigDecimal.valueOf(20));
        order.setPaymentDate(LocalDate.now());
        order.setPaymentAmount("In Full");
        return order;
    }

    private OrderItem orderItem(long index) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(index);
        Product product = new Product();
        product.setName("name" + index);
        orderItem.setProduct(product);
        orderItem.setPrice(BigDecimal.valueOf(index));
        orderItem.setQty((int) index);
        return orderItem;
    }
}
