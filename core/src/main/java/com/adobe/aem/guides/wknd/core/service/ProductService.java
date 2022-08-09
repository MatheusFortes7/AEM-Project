package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

public interface ProductService {

    String listAll();
    String listProductById(int id);
    String listProductByPrice();
    String listProductByCategory(String category);
    String listProductByName(String name);
    void save(SlingHttpServletRequest request);
    void delete(SlingHttpServletRequest request);
    void update(SlingHttpServletRequest request);

}
