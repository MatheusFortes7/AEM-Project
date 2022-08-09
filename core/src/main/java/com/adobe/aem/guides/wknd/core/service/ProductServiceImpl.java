package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ProductDao;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(immediate = true, service = ProductService.class)
public class ProductServiceImpl implements ProductService{

    @Reference
    private ProductDao productDao;
    @Reference
    private DatabaseService  databaseService;


    @Override
    public String listAll() {
        List<Product> products = null;
        try{
            products = productDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(products);
    }

    @Override
    public String listProductById(int id) {
        Product product = null;
        try{
            product = productDao.getProductById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(product);
    }

    @Override
    public String listProductByPrice() {
        List<Product> products = null;
        try{
            products = productDao.getOrderProductByPrice();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(products);
    }

    @Override
    public String listProductByCategory(String category) {
        List<Product> products = null;
        try{
            products = productDao.getProductByCategory(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(products);
    }

    @Override
    public String listProductByName(String name) {
        Product product = null;
        try{
            product = productDao.getProduct(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(product);
    }

    @Override
    public void save(SlingHttpServletRequest request) {
        /*try {
            //TODO VER COMO FUNCIONA O fromJson da bib Gson
            //List<Client> list = new Gson().fromJson();

            for(Client u : list ){
                if(clientDao.getClientByID(u.getIdClient()) == null){
                    clientDao.save(u);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

    @Override
    public void delete(SlingHttpServletRequest request) {

    }

    @Override
    public void update(SlingHttpServletRequest request) {

    }
}
