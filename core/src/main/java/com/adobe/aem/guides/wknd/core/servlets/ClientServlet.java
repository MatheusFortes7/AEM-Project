package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.models.Mensage;
import com.adobe.aem.guides.wknd.core.service.ClientService;
import com.google.gson.Gson;
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
        SLING_SERVLET_PATHS + "=" + "/bin/client",
        SLING_SERVLET_EXTENSIONS + "=" + "txt", SLING_SERVLET_EXTENSIONS + "=" + "json"})

@ServiceDescription("Servlet Serve teste")
public class ClientServlet extends SlingAllMethodsServlet {

    @Reference
    private ClientService clientService;

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(request.getParameter("id") != null){
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            try{
                String clients = clientService.listClientById(id);
                response.getWriter().write(clients);
            } catch (IOException e){
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        } else{
            String clients = clientService.listClient();
            try{
                response.getWriter().write(clients);
            } catch (Exception e){
                throw new RuntimeException(new Gson().toJson(String.valueOf(new Mensage(e.getMessage()))));
            }
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        clientService.save(request);
        response.getWriter().write(new Gson().toJson(new Mensage("client added successfully")));
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        clientService.update(request);
        response.getWriter().write((String) request.getAttribute("name"));
        response.getWriter().write((Integer) request.getAttribute("id"));
        response.getWriter().write(new Gson().toJson(new Mensage("client updated successfully")));
    }
}
