package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Mensage {

    private String mensage;

    public Mensage(String mensage) {
        this.mensage = mensage;
    }
}
