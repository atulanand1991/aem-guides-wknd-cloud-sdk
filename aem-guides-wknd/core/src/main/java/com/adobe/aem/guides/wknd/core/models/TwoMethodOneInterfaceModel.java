package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.service.OneInterfaceForTwoMethod;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "wknd/components/twomethodoneinterface")
@Exporter(name = "jackson", extensions ="json",
options = {
        @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value="true"),
        @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true")
})
@JsonRootName("atul-main")
public class TwoMethodOneInterfaceModel {


    @OSGiService(filter = "(component.name=FirstImplForInterfaceOne)")
    OneInterfaceForTwoMethod oneInterfaceFirst;

    @OSGiService(filter = "(component.name=SecondImplForInterfaceOne)")
    OneInterfaceForTwoMethod oneInterfaceSecond;

    private String myName;

    private String myCountry;

    @PostConstruct
    private void init() {

        myName = oneInterfaceFirst.myName() + "  -----  " + oneInterfaceSecond.myName();

        myCountry = "India";


    }

    public String getMyName() {
        return myName;
    }

    public String getMyCountry() {
        return myCountry;
    }
}
