package com.talabeya.talabyablue.Helper;

import android.content.Context;

import com.talabeya.talabyablue.Domain.Product;
import com.talabeya.talabyablue.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Product item) {
        ArrayList<Product> listFood = getListCart();
        Boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                n = i;
                break;
            }

        }
        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listFood.add(item);
        }
        tinyDB.putListObject("cardList", listFood);


    }

    public ArrayList<Product> getListCart() {
        return tinyDB.getListObject("cardList");

    }

    public void minusNumberFood(ArrayList<Product> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listFood.get(position).getNumberInCart() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("cardList", listFood);
        changeNumberItemsListener.Changed();
    }

    public void plusNumberFood(ArrayList<Product> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("cardList", listFood);
        changeNumberItemsListener.Changed();

    }

    public Double getTotalFee() {

        ArrayList<Product> listFeed2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listFeed2.size(); i++) {
            fee = fee + (listFeed2.get(i).getFee() * listFeed2.get(i).getNumberInCart());
        }
        return fee;
    }
}
