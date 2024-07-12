package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.AssignmentTwo;
import com.adobe.aem.guides.wknd.core.service.WkndHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

@Component(service = AssignmentTwo.class, immediate = true)
public class AssignmentTwoImpl implements AssignmentTwo {

    public static final Logger LOGGER = LoggerFactory.getLogger(AssignmentTwoImpl.class);
    private  static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String UNIVERSITY_DATA_BASE = "/content/usergenerated/content/universityData";

    @Reference
    WkndHelper wkndHelper;


    @Override
    public String getAddressFromJcr(){
        String address;
        ResourceResolver resourceResolver = wkndHelper.getResourceResolver();
        Resource resource = resourceResolver.getResource("/content/wknd/us/en/system-user/jcr:content/root/container/container/helloworld");
        address = resource.getValueMap().get("address").toString();
        //
        Node node = resource.adaptTo(Node.class);
        try {
            node.setProperty("qqq", "wwww");
            resourceResolver.commit();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }


        return address;
    }
}
