package com.dryhome.domain;

import com.dryhome.domain.util.CustomLocalDateSerializer;
import com.dryhome.domain.util.ISO8601LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dryhome.utils.StringPrintUtils.valueOrEmpty;
import static com.google.common.base.Objects.firstNonNull;

/**
 * A Order.
 */
@Entity
@Table(name = "T_ORDER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements MergeableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    @Column(name = "order_number", length = 15, nullable = false)
    private String orderNumber;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "dispatch_date", nullable = false)
    private LocalDate dispatchDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "placed_by")
    private String placedBy;

    @Column(name = "method")
    private String method;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_notes1")
    private String invoiceNotes1;

    @Column(name = "invoice_notes2")
    private String invoiceNotes2;

    @Column(name = "notes")
    private String notes;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_amount")
    private String paymentAmount;

    @Column(name = "vat_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal vatRate;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPlacedBy() {
        return placedBy;
    }

    public void setPlacedBy(String placedBy) {
        this.placedBy = placedBy;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal getVatAmount() {
        if (vatRate != null) {
            return getSubTotal().multiply(vatRate.divide(BigDecimal.valueOf(100)));
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQty())));
        }
        return subTotal;
    }

    public BigDecimal getTotalAmount() {
        return getSubTotal().add(getVatAmount());
    }

    public OrderView toView() {
        OrderView orderView = new OrderView();
        orderView.setCustomerView(customer.toView());
        orderView.setInvoiceNumber(invoiceNumber);
        orderView.setOrderNumber(valueOrEmpty(orderNumber));
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        orderView.setInvoiceDate(invoiceDate == null ? "" : dateTimeFormatter.print(invoiceDate));
        orderView.setInvoiceNotes1(valueOrEmpty(invoiceNotes1));
        orderView.setInvoiceNotes2(valueOrEmpty(invoiceNotes2));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        orderView.setSubTotal(decimalFormat.format(getSubTotal()));
        orderView.setVatAmount(decimalFormat.format(getVatAmount()));
        orderView.setTotal(decimalFormat.format(getTotalAmount()));
        if (vatRate != null) {
            orderView.setVatRate(vatRate.toString());
        }
        orderView.setOrderItems(items.stream().
            map(orderItem -> new OrderItemView(
                orderItem.getProduct().getName(),
                orderItem.getQty().toString(),
                decimalFormat.format(orderItem.getPrice()),
                decimalFormat.format(orderItem.getTotal()))).
            collect(Collectors.<OrderItemView>toList()));
        for (int i = orderView.getOrderItems().size(); i < 14; i++) {
            orderView.getOrderItems().add(i, new OrderItemView(" ", " ", " ", " "));
        }

        if (paymentDate != null && paymentAmount != null) {
            String paymentMessage = String.format(
                "PAYMENT DETAILS\nPayment Type: %s\nPayment Date: %s\nPayment Amount: %s\n \nWith Thanks",
                paymentType, dateTimeFormatter.print(paymentDate), paymentAmount);
            orderView.setPaymentDetails(paymentMessage);
        } else {
            orderView.setPaymentDetails(" ");
        }
        return orderView;
    }

    @Override
    public String marshallToXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(OrderView.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            m.marshal(toView(), writer);
            return writer.toString();
        } catch (Exception e) {
            throw new IllegalStateException("failed to marshal customer: " + id, e);
        }
    }

    private String fieldOrEmpty(String field) {
        return firstNonNull(field, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderNumber='" + orderNumber + "'" +
            ", orderDate='" + orderDate + "'" +
            ", dispatchDate='" + dispatchDate + "'" +
            ", invoiceDate='" + invoiceDate + "'" +
            ", placedBy='" + placedBy + "'" +
            ", method='" + method + "'" +
            ", invoiceNumber='" + invoiceNumber + "'" +
            ", invoiceNotes1='" + invoiceNotes1 + "'" +
            ", invoiceNotes2='" + invoiceNotes2 + "'" +
            ", notes='" + notes + "'" +
            ", paymentDate='" + paymentDate + "'" +
            ", paymentStatus='" + paymentStatus + "'" +
            ", paymentType='" + paymentType + "'" +
            ", paymentAmount='" + paymentAmount + "'" +
            ", items='" + items + "'" +
            ", vatRate='" + vatRate + "'" +
            ", vatAmount='" + getVatAmount() + "'" +
            ", subTotal='" + getSubTotal() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            '}';
    }
}
