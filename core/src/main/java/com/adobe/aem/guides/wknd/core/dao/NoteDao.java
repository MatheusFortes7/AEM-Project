package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Note;

import java.util.List;

public interface NoteDao {
    //get
    List<Note> getAll();
    List<Note> noteByClient(int idClient);

    //post
    void save(Note note);

    //delete
    void delete(int number);

    //put
    void update(int id, Note note);
}
