package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component(immediate = true, service = ProductDao.class)
public class ProductDaoImpl implements ProductDao{

    @Reference
    private DatabaseService databaseService;

    @Override
    public List<Product> getAll() {
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM aem.product";
            List<Product> products = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Product product = new Product(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getFloat(4));
                        products.add(product);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

        @Override
    public Product getProductById(int id) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "SELECT * FROM aem.product WHERE idproduct = ?";
            Product product = null;
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        product = new Product(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getFloat(4));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public List<Product> getOrderProductByPrice() {
        try(Connection connection = databaseService.getConnection()){
            String sql = "SELECT * FROM product ORDER BY price ASC";
            List<Product> products = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Product product = new Product(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getFloat(4));
                        products.add(product);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "SELECT * FROM aem.product where category = ?";
            List<Product> products = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, category);
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Product product = new Product(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getFloat(4));
                        products.add(product);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public Product getProduct(String product) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "SELECT * FROM aem.product WHERE name = ?";
            Product finalProduct = null;
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, product);
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        finalProduct = new Product(rst.getInt(1),rst.getString(2), rst.getString(3), rst.getFloat(4));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return finalProduct;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void save(Product product) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "INSERT INTO aem.product (name, category, price) VALUES (?, ?, ?)";

            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setString(1, product.getName());
                pstm.setString(2, product.getCategory());
                pstm.setFloat(3, product.getPrice());
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void delete(int id) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "DELETE FROM aem.product WHERE idproduct = ?";
            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, id);
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void update(int id, Product product) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "UPDATE product SET name = ?, category = ?, price = ? WHERE idproduct = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setString(1, product.getName());
                pstm.setString(2, product.getCategory());
                pstm.setFloat(3, product.getPrice());
                pstm.setInt(4, id);
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }
}
