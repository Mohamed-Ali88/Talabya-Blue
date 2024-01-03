package com.talabeya.talabyablue.Helper;

import android.content.Context;

public class Constant {
    Context context;
    static String TypeOfUser;

    public Constant(Context context, String typeOfUser) {
        this.context = context;
        TypeOfUser = typeOfUser;
    }

    public static final String URL = "https://orderr.website/api";
    public static final String login = URL + "/login";
    public static final String logOut = URL + "/logout";


    public static final String companies = URL + TypeOfUser + "/companies";
    public static final String departments = URL + TypeOfUser + "/departments";
    public static final String subCategories = URL + TypeOfUser + "/categories";
    public static final String companyProduct = URL + TypeOfUser + "/CompanyProducts";
    public static final String categoryProduct = URL + TypeOfUser + "/CategoryProducts";
    public static final String makeOrder = URL + TypeOfUser + "/MakeOrder";
    public static final String clientOrders = URL + TypeOfUser + "/OrderDetail";
    public static final String offerProducts = URL + "/showOffer";

}
