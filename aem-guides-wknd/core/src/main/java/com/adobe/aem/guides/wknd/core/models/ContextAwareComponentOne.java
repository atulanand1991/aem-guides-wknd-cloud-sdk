package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.caconfig.CaConfigOne;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContextAwareComponentOne {

    private String companyName;
    private String countryName;
    private String ceoName;

    private String atul;

    private CaConfigOne caConfigOne;

    @ScriptVariable
    Page currentPage;

    @SlingObject
    ResourceResolver resourceResolver;

    @PostConstruct
    public void init() {
        atul = "atul anand";
        CaConfigOne caConfig = getContexAwareConfig(currentPage.getPath(), resourceResolver);
        companyName = caConfig.companyName();
        countryName = caConfig.countryName();
        ceoName = caConfig.ceoName();
    }

    public CaConfigOne getContexAwareConfig(String currentPagePath, ResourceResolver resourceResolver) {
        Resource resource = resourceResolver.getResource(currentPagePath);
        if (resource != null) {
            ConfigurationBuilder configurationBuilder = resource.adaptTo(ConfigurationBuilder.class);
            if (configurationBuilder != null) {
                return configurationBuilder.as(CaConfigOne.class);
            }
        }
        return null;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCeoName() {
        return ceoName;
    }

    public String getAtul() {
        return atul;
    }
}
