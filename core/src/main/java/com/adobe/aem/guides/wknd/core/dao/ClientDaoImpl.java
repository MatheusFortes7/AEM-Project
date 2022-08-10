package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Client;
import com.adobe.aem.guides.wknd.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component(immediate = true, service = ClientDao.class)
public class ClientDaoImpl implements ClientDao{

    @Reference
    private DatabaseService databaseService;

    @Override
    public List<Client> getAll() {
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM aem.client";
            List<Client> clients = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Client client = new Client(rst.getInt(1), rst.getString(2));
                        clients.add(client);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        @Override
    public Client getClientByID(int id) {

        Client client = null;
        try(Connection connection = databaseService.getConnection()){
            String sql = "SELECT * FROM client WHERE idclient = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                       client = new Client(rst.getInt(1), rst.getString(2));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            return client;
    }

    @Override
    public void save(Client client) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "INSERT INTO aem.client (name) VALUES (?)";

            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setString(1, client.getName());
                pstm.execute();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage() + "Error while saving client");
        }
    }

    @Override
    public void delete(int idclient) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "DELETE FROM aem.client WHERE idclient = ?";
            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, idclient);
                pstm.execute();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage() + "Error while deleting client");
        }
    }

    @Override
    public void update(int id, String name) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "UPDATE client SET name = ? WHERE idclient = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setString(1, name);
                pstm.setInt(2, id);
                pstm.execute();
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage() + "Error while updating client");
        }
    }
}
