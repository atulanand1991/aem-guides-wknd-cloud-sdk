package com.adobe.aem.guides.wknd.core.caconfig;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "CaConfigOne label", description = "CaConfigOne description")
public @interface CaConfigOne {

    @Property(label = "companyName label",
            description = "companyName description")
    String companyName() default "default company name";

    @Property(label = "countryName label",
            description = "countryName description")
    String countryName() default "default company name";

    @Property(label = "ceoName label",
            description = "ceoName description")
    String ceoName() default "default company name";
}