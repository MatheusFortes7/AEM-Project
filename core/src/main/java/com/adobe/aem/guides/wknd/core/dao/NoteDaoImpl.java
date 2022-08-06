package com.adobe.aem.guides.wknd.core.dao;

import com.adobe.aem.guides.wknd.core.models.Note;
import com.adobe.aem.guides.wknd.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Component(immediate = true, service = NoteDao.class)
public class NoteDaoImpl implements NoteDao{

    @Reference
    private DatabaseService databaseService;

    @Override
    public List<Note> getAll() {
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM aem.note";
            List<Note> notes = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Note note = new Note(rst.getInt(1),rst.getInt(2), rst.getInt(3), rst.getFloat(4));
                        notes.add(note);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public List<Note> noteByClient(int idClient) {
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM aem.note WHERE idclient = ?";
            List<Note> notes = new LinkedList<>();
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        Note note = new Note(rst.getInt(1),rst.getInt(2), rst.getInt(3), rst.getFloat(4));
                        notes.add(note);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void save(Note note) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "INSERT INTO aem.note (idproduct, idclient, value) VALUES (?, ?, ?)";

            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, note.getIdProduct());
                pstm.setInt(2, note.getIdClient());
                pstm.setFloat(3, note.getValue());
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void delete(int number) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "DELETE FROM aem.note WHERE number = ?";
            try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, number);
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }

    @Override
    public void update(int id, Note note) {
        try(Connection connection = databaseService.getConnection()){
            String sql = "UPDATE note SET idproduct = ?, idclient = ?, value = ? WHERE number = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                pstm.setInt(1, note.getIdProduct());
                pstm.setInt(2, note.getIdClient());
                pstm.setFloat(3, note.getValue());
                pstm.setInt(4, id);
                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage() + "Error while trying to connect to database");
        }
    }
}
