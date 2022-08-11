package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.models.Client;
import com.adobe.aem.guides.wknd.core.models.Mensage;
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

@Component(immediate = true, service = ClientService.class)
public class ClientServiceImpl implements ClientService{

    @Reference
    private ClientDao clientDao;
    @Reference
    private DatabaseService  databaseService;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(request.getParameter("id") != null){
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            try{
                String clients = listClientById(id);
                response.getWriter().write(clients);
            } catch (IOException e){
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage(), 401))));
            }
        } else{
            String clients = listClient();
            try{
                response.getWriter().write(clients);
            } catch (Exception e){
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage(), 401))));
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
    public void save(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            BufferedReader reader = request.getReader();
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> clients = new Gson().fromJson(reader, listType);

            for(Client u : clients){
                if(u.getName() == null || u.getName().isEmpty()){
                    response.getWriter().write(new Gson().toJson(new Mensage("Object must be complete", 401)));
                } else {
                    if(clientDao.getClientByID(u.getIdClient()) == null){
                        clientDao.save(u);
                        response.getWriter().write(new Gson().toJson(new Mensage("client added successfully", 401)));
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
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> clients = new Gson().fromJson(reader, listType);

            for(Client u : clients){
                if(clientDao.getClientByID(u.getIdClient()) == null){
                    response.getWriter().write(new Gson().toJson(new Mensage("Client doesn't existe", 401)));
                } else {
                    if(clientDao.getClientByID(u.getIdClient()) != null){
                        clientDao.delete(u.getIdClient());
                        response.getWriter().write(new Gson().toJson(new Mensage("client removed successfully", 401)));
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
        Client client;
        try {
            client = new Gson().fromJson(userPostString, Client.class);
            if(client.getName() != null){
                clientDao.update(client.getIdClient(), client.getName());
                response.getWriter().write(new Gson().toJson(new Mensage("client updated successfully", 401)));
            } else {
                response.getWriter().write(new Gson().toJson(new Mensage("Json must be complete", 401)));
            }

        }catch (Exception e){
            try {
                response.getWriter().write(new Gson().toJson(new Mensage("This isn't a Json", 401)));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
