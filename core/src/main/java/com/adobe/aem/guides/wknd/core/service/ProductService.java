package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public interface ProductService {

    void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    String listAll();
    String listProductById(int id);
    String listProductByPrice();
    String listProductByCategory(String category);
    String listProductByName(String name);
    void save(SlingHttpServletRequest request, SlingHttpServletResponse response);
    void delete(SlingHttpServletRequest request, SlingHttpServletResponse response);
    void update(SlingHttpServletRequest request, SlingHttpServletResponse response);

}
