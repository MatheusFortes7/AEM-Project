package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.service.ClientService;
import com.adobe.aem.guides.wknd.core.service.NoteService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingAllMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(immediate = true, service = Servlet.class, property = {
        SLING_SERVLET_METHODS + "=" + "GET",
        SLING_SERVLET_METHODS + "=" + "POST",
        SLING_SERVLET_METHODS + "=" + "DELETE",
        SLING_SERVLET_METHODS + "=" + "PUT",
        SLING_SERVLET_PATHS + "=" + "/bin/note",
        SLING_SERVLET_EXTENSIONS + "=" + "txt", SLING_SERVLET_EXTENSIONS + "=" + "json"})

@ServiceDescription("Servlet Serve teste")
public class NoteServlet extends SlingAllMethodsServlet {

    @Reference
    private NoteService noteService;

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(request.getParameter("idclient") != null){
            String idString = request.getParameter("idclient");
            int id = Integer.parseInt(idString);
            try{
                String notes = noteService.listNoteByClientId(id);
                response.getWriter().write(notes);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } else{
            String notes = noteService.listNotes();
            try{
                response.getWriter().write(notes);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        noteService.save(request);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

    }
}
