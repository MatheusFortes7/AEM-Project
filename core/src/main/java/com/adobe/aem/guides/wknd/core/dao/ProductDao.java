package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Product;

import java.util.List;

public interface ProductDao {

    //get
    List<Product> getAll();
    Product getProductById(int id);
    List<Product> getOrderProductByPrice();
    List<Product> getProductByCategory(String category);
    Product getProduct(String product);

    //post
    void save(Product product);

    //delete
    void delete(int id);

    //put
    void update(int id, Product product);

}
