package com.talabeya.talabyablue.Domain;

import java.io.Serializable;

public class invoicesDomain implements Serializable {
    String order_id,order_state,request_date,checked_date,client,storeName;
    Double agalValueAmount,total_order_price;

    public invoicesDomain(String order_id, String order_state, String request_date, String checked_date, String client, String storeName, Double agalValueAmount, Double total_order_price) {
        this.order_id = order_id;
        this.order_state = order_state;
        this.request_date = request_date;
        this.checked_date = checked_date;
        this.client = client;
        this.storeName = storeName;
        this.agalValueAmount = agalValueAmount;
        this.total_order_price = total_order_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getChecked_date() {
        return checked_date;
    }

    public void setChecked_date(String checked_date) {
        this.checked_date = checked_date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getAgalValueAmount() {
        return agalValueAmount;
    }

    public void setAgalValueAmount(Double agalValueAmount) {
        this.agalValueAmount = agalValueAmount;
    }

    public Double getTotal_order_price() {
        return total_order_price;
    }

    public void setTotal_order_price(Double total_order_price) {
        this.total_order_price = total_order_price;
    }
}
