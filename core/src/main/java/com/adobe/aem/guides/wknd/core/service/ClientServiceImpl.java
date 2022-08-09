package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.models.Client;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component(immediate = true, service = ClientService.class)
public class ClientServiceImpl implements ClientService{

    @Reference
    private ClientDao clientDao;
    @Reference
    private DatabaseService  databaseService;

    @Override
    public String listClient() {
        List<Client> client = null;
        try{
            client = clientDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(client);
    }

    @Override
    public String listClientById(int id) {
        Client client = null;
        try{
            client = clientDao.getClientByID(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(client);
    }

    @Override
    public void save(SlingHttpServletRequest request) {
        String userPostString = null;
        try {
            userPostString = IOUtils.toString(request.getReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Client objWordConverter;
        try {
            objWordConverter = new Gson().fromJson(userPostString, Client.class);

            clientDao.save(objWordConverter);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SlingHttpServletRequest request) {

    }

    @Override
    public void update(SlingHttpServletRequest request) {
        String userPostString = null;
        try {
            userPostString = IOUtils.toString(request.getReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Client objWordConverter;
        try {
            objWordConverter = new Gson().fromJson(userPostString, Client.class);
            request.setAttribute("name", objWordConverter.getName());
            request.setAttribute("id", objWordConverter.getIdClient());
            clientDao.update(objWordConverter.getIdClient(), objWordConverter.getName());

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
