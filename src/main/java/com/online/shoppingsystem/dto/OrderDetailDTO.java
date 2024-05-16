package com.online.shoppingsystem.dto;

public class OrderDetailDTO {

    private int orderID;
    private int productID;
    private int qty;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int orderID, int productID, int qty) {
        this.orderID = orderID;
        this.productID = productID;
        this.qty = qty;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderID=" + orderID +
                ", productID=" + productID +
                ", qty=" + qty +
                '}';
    }
}
