package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Client;

import java.util.List;

public interface ClientDao {

    //get
    List<Client> getAll();
    Client getClientByID(int id);

    //post
    void save(Client client);

    //delete
    void delete(int idclient);

    //put
    void update(int id, String name);

}
