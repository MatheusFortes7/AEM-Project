package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public interface NoteService {

    void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;
    String listNotes();
    String listNoteByClientId(int idclient);
    void save(SlingHttpServletRequest request);
    void delete(SlingHttpServletRequest request);
    void update(SlingHttpServletRequest request);

}
