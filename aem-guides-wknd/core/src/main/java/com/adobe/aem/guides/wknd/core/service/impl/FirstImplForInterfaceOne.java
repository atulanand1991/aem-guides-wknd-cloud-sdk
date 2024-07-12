package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.OneInterfaceForTwoMethod;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(service = OneInterfaceForTwoMethod.class, immediate = true, name = "FirstImplForInterfaceOne")
@ServiceRanking(9001)
public class FirstImplForInterfaceOne implements OneInterfaceForTwoMethod {

    @Override
    public String myName() {
        return "FirstImplForInterfaceOne";
    }
}
