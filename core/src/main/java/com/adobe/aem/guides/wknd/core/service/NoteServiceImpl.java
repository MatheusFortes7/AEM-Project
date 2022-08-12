package com.adobe.aem.guides.wknd.core.service;

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

@Component(immediate = true, service = NoteService.class)
public class NoteServiceImpl implements NoteService{

    @Reference
    private NoteDao noteDao;
    @Reference
    private DatabaseService  databaseService;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(request.getParameter("idclient") != null){
            String idString = request.getParameter("idclient");
            int id = 0;

            if(!(idString.isEmpty() || idString == null)){
                try{
                    id = Integer.parseInt(idString);
                } catch (NumberFormatException e){
                    response.setStatus(400);
                    response.getWriter().write(new Gson().toJson(new Mensage("Parameter must be an int")));
                    return;
                }
            }else{
                response.setStatus(400);
                response.getWriter().write(new Gson().toJson(new Mensage("Parameter can't be null")));
                return;
            }

            try{
                String notes = listNoteByClientId(id);
                response.getWriter().write(notes);
            } catch (IOException e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else{
            String notes = listNotes();
            try{
                response.getWriter().write(notes);
            } catch (Exception e){
                response.setStatus(400);
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        }
    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        save(request);
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String listNotes() {
        List<Note> notes = null;
        try{
            notes = noteDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(notes);
    }

    @Override
    public String listNoteByClientId(int idclient) {
        List<Note> notes = null;
        try{
            notes = noteDao.noteByClient(idclient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Gson().toJson(notes);
    }

    @Override
    public void save(SlingHttpServletRequest request) {
        /*try {
            //TODO VER COMO FUNCIONA O fromJson da bib Gson
            Type typeList = new TypeToken<ArrayList<Note>>() {}.getType();
            //List<Client> list = new Gson().fromJson(bf, typeList);

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
