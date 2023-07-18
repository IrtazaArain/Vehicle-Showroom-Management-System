package com.example.opel;


public class DataModal {
    String modal;
    String make;
    String price;
    String image;

    public DataModal(String modal, String make, String price, String image) {
        this.modal = modal;
        this.make = make;
        this.price = price;
        this.image = image;
    }

    public String getModal() {
        return modal;
    }

    public String getMake() {
        return make;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}

