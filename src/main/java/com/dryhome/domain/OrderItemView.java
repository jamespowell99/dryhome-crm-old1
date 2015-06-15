package com.dryhome.domain;


public class OrderItemView {
    private String item;
    private String qty;
    private String price;
    private String total;

    public OrderItemView(String item, String qty, String price, String total) {
        this.item = item;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
