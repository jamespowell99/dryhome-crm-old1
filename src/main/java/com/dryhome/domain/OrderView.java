package com.dryhome.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "root")
public class OrderView {
    private CustomerView customerView;
    private String invoiceNumber;
    private String orderNumber;
    private String invoiceDate;
    private String invoiceNotes1;
    private String invoiceNotes2;
    private String subTotal;
    private String vatAmount;
    private String total;
    private List<OrderItemView> orderItems = new ArrayList<>();
    private String vatRate;
    private String paymentDetails;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNotes1() {
        return invoiceNotes1;
    }

    public void setInvoiceNotes1(String invoiceNotes1) {
        this.invoiceNotes1 = invoiceNotes1;
    }

    public String getInvoiceNotes2() {
        return invoiceNotes2;
    }

    public void setInvoiceNotes2(String invoiceNotes2) {
        this.invoiceNotes2 = invoiceNotes2;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(String vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @XmlElement(name = "customer")
    public CustomerView getCustomerView() {
        return customerView;
    }

    public void setCustomerView(CustomerView customerView) {
        this.customerView = customerView;
    }

    public List<OrderItemView> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemView> orderItems) {
        this.orderItems = orderItems;
    }

    public String getVatRate() {
        return vatRate;
    }

    public void setVatRate(String vatRate) {
        this.vatRate = vatRate;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
