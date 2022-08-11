package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.dao.NoteDao;
import com.adobe.aem.guides.wknd.core.models.Mensage;
import com.adobe.aem.guides.wknd.core.models.Note;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(immediate = true, service = RelatoryService.class)
public class RelatoryServiceImpl implements RelatoryService{

    @Reference
    private NoteDao noteDao;
    @Reference
    private ClientDao clientDao;
    @Reference
    private DatabaseService  databaseService;


    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        try{
            int idClient = 0;
            try{
                idClient = Integer.parseInt(request.getParameter("idclient"));
            } catch (Exception e){
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage("Parameter error")));
            }
            if(clientDao.getClientByID(idClient) == null){
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage("Client doen't exist in our databse")));
            } else {
                response.setContentType("application/html");
                List<Note> notes = noteDao.noteByClient(idClient);
                if(notes.size() == 0){
                    response.getWriter().write("<html><body>");
                    response.getWriter().write("The requested client does not have a purchase in our system.");
                    response.getWriter().write("</html></body>");
                } else {
                    response.getWriter().write("<html><body>");
                    response.getWriter().write("<div> Purchase from client: </div>");
                    for(Note u : notes){
                        response.getWriter().write("<div>");
                        response.getWriter().write("<br>");
                        response.getWriter().write("\nNote number: " + u.getNumber());
                        response.getWriter().write("<br>");
                        response.getWriter().write("\nProduct Id: " + u.getIdProduct());
                        response.getWriter().write("<br>");
                        response.getWriter().write("\nClient Id: " + u.getIdClient());
                        response.getWriter().write("<br>");
                        response.getWriter().write("\nValue: " + u.getValue());
                        response.getWriter().write("<br>");
                        response.getWriter().write("\n</div>");
                    }
                    response.getWriter().write("</html></body>");
                }
            }
        } catch (Exception e){
            response.setContentType("application/json");
            response.setStatus(400);
            response.getWriter().write(new Gson().toJson(new Mensage("Error getting a relatory")));
        }
    }
}
