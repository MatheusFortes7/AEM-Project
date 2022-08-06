package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Note {
    
    private int number;
    private int idProduct;
    private int idClient;
    private float value;

    public Note() {
    }

    public Note(int number, int idProduct, int idClient, float value) {
        this.number = number;
        this.idProduct = idProduct;
        this.idClient = idClient;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Note{" +
                "number=" + number +
                ", idProduct=" + idProduct +
                ", idClient=" + idClient +
                ", value=" + value +
                '}';
    }

}
