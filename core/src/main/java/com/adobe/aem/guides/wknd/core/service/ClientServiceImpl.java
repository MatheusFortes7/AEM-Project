package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.models.Client;
import com.adobe.aem.guides.wknd.core.models.Mensage;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletException;
import java.io.IOException;
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
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else{
            String clients = listClient();
            try{
                response.getWriter().write(clients);
            } catch (Exception e){
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        }
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        save(request);
        response.getWriter().write(new Gson().toJson(new Mensage("client added successfully")));
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        update(request);
        response.getWriter().write((String) request.getAttribute("name"));
        response.getWriter().write((Integer) request.getAttribute("id"));
        response.getWriter().write(new Gson().toJson(new Mensage("client updated successfully")));
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
