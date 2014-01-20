package com.example.PRS;

public class ShoppingCartEntry {

    private Product mProduct;
    private int mQuantity;
    public double  mProCost;

    public ShoppingCartEntry(Product product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
        mProCost = (double) quantity * product.price;
    }

    public Product getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public double getQuantityCost(){
        return mProCost;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}