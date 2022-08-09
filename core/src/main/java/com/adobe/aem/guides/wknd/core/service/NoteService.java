package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

public interface NoteService {

    String listNotes();
    String listNoteByClientId(int idclient);
    void save(SlingHttpServletRequest request);
    void delete(SlingHttpServletRequest request);
    void update(SlingHttpServletRequest request);

}
