package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public interface RelatoryService {

    void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException;

}
