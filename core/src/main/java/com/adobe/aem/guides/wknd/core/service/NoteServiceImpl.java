package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.dao.ClientDao;
import com.adobe.aem.guides.wknd.core.dao.NoteDao;
import com.adobe.aem.guides.wknd.core.models.Client;
import com.adobe.aem.guides.wknd.core.models.Note;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(immediate = true, service = NoteService.class)
public class NoteServiceImpl implements NoteService{

    @Reference
    private NoteDao noteDao;
    @Reference
    private DatabaseService  databaseService;

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
            //List<Client> list = new Gson().fromJson();

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
