package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

public interface ClientService {

    String listClient();
    String listClientById(int id);
    void save(SlingHttpServletRequest request);
    void delete(SlingHttpServletRequest request);
    void update(SlingHttpServletRequest request);

}
