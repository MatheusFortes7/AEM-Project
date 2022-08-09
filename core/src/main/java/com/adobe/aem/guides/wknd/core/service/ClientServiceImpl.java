package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.models.Client;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.BufferedReader;
import java.io.IOException;
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
        /*try {
            BufferedReader bf = request.getReader();
            //TODO VER COMO FUNCIONA O fromJson da bib Gson
            //List<Client> list = new Gson().fromJson(bf,);

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
