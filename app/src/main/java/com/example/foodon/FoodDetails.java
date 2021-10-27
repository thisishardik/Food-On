package com.example.foodon;

public class FoodDetails {
    public String dishes, quantity,price,description,imageURL,randomUID,chefId;

    public FoodDetails(String dishes, String quantity, String price, String description, String imageURL, String randomUID, String chefId) {
        this.dishes = dishes;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.randomUID = randomUID;
        this.chefId = chefId;
    }
}
