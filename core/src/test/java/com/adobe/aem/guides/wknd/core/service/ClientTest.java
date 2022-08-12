package com.adobe.aem.guides.wknd.core.service;

import com.adobe.aem.guides.wknd.core.servlets.ClientServlet;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;

@ExtendWith(AemContextExtension.class)
public class ClientTest {

    private ClientServlet clientServlet;
    @Mock
    private ClientService clientServiceMock;
    private MockSlingHttpServletRequest request;
    private MockSlingHttpServletResponse response;

    @BeforeEach
    void setup(AemContext context){
        MockitoAnnotations.openMocks(this);

        response = context.response();
        request = context.request();

        clientServlet = new ClientServlet(clientServiceMock);
    }

    @Test
    void doGet_Status200_WhenParamIsPassed(){
        request.addRequestParameter("id", "1");

        try{
            clientServiceMock.doGet(request, response);
        } catch (Exception e) {
            Assertions.fail();
        }

        Assertions.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }

    @Test
    void doGet_Status200_WhenParamIsNull(){
        request.addRequestParameter("id", "");

        try{
            clientServiceMock.doGet(request, response);
        } catch (Exception e) {
            Assertions.fail();
        }

        Assertions.assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }
}
