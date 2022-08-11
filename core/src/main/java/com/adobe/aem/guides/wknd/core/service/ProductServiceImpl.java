package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ProductDao;
import com.adobe.aem.guides.wknd.core.models.Mensage;
import com.adobe.aem.guides.wknd.core.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Component(immediate = true, service = ProductService.class)
public class ProductServiceImpl implements ProductService{

    @Reference
    private ProductDao productDao;
    @Reference
    private DatabaseService  databaseService;


    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(request.getParameter("id") != null){
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            try{
                String products = listProductById(id);
                response.getWriter().write(products);
            } catch (IOException e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else if(request.getParameter("category") != null){
            String category = request.getParameter("category");
            try{
                String products = listProductByCategory(category);
                response.getWriter().write(products);
            }catch (Exception e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else if(request.getParameter("name") != null){
            String name = request.getParameter("name");
            try{
                String products = listProductByName(name);
                response.getWriter().write(products);
            }catch (Exception e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else if(request.getParameter("order") != null){
            try{
                String products = listProductByPrice();
                response.getWriter().write(products);
            }catch (Exception e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else {
            String clients = listAll();
            try{
                response.getWriter().write(clients);
            } catch (Exception e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        }
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        save(request, response);
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        delete(request, response);
    }

    @Override
    public void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        update(request, response);
    }

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
    public void save(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = null;
            try{
                products = new Gson().fromJson(reader, listType);
            } catch (Exception e){
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage(e.getMessage())));
                return;
            }

            for(Product u : products){
                if(u.getName() == null || u.getName().isEmpty()){
                    response.setStatus(400);
                    response.getWriter().write(new Gson().toJson(new Mensage("Object must be complete")));
                } else {
                    if(productDao.getProductById(u.getIdProduct()) == null){
                        productDao.save(u);
                        response.setStatus(201);
                        response.getWriter().write(new Gson().toJson(new Mensage("product added successfully")));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = null;
            try{
                products = new Gson().fromJson(reader, listType);
            } catch (Exception e){
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage(e.getMessage())));
                return;
            }

            for(Product u : products){
                if(productDao.getProductById(u.getIdProduct()) == null){
                    response.setStatus(400);
                    response.getWriter().write(new Gson().toJson(new Mensage("Client doesn't existe")));
                } else {
                    if(productDao.getProductById(u.getIdProduct()) != null){
                        productDao.delete(u.getIdProduct());
                        response.setStatus(202);
                        response.getWriter().write(new Gson().toJson(new Mensage("product removed successfully")));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String userPostString = null;
        try {
            userPostString = IOUtils.toString(request.getReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Product product;
        try {
            product = new Gson().fromJson(userPostString, Product.class);
            if(product.getName() != null && product.getCategory() != null){
                productDao.update(product.getIdProduct(), product);
                response.setStatus(202);
                response.getWriter().write(new Gson().toJson(new Mensage("product updated successfully")));
            } else {
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage("Json must be complete")));
            }

        }catch (Exception e){
            try {
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage("This isn't a Json")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
