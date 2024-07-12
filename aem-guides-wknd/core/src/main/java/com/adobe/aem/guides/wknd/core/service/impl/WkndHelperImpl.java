package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.WkndHelper;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

@Component(service = WkndHelper.class, immediate = true)
public class WkndHelperImpl implements WkndHelper {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public ResourceResolver getResourceResolver() {
        final Map<String, Object> map =new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, "wkndSystemUser");
        try {
            return resolverFactory.getServiceResourceResolver(map);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }
}
