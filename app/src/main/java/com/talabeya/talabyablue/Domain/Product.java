package com.talabeya.talabyablue.Domain;

import java.io.Serializable;

public class Product implements Serializable {
    private String title;
    private String pic;
    private int amount;
    private Double fee;
    private Double discountPrice;
    private int numberInCart;

    public Product(String title, String pic, int amount, Double fee, Double discountPrice) {
        this.title = title;
        this.pic = pic;
        this.amount = amount;
        this.fee = fee;
        this.discountPrice = discountPrice;
    }


    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

}
