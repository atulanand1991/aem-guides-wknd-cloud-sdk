package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.OneInterfaceForTwoMethod;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

@Component(service = OneInterfaceForTwoMethod.class, immediate = true, name = "SecondImplForInterfaceOne")
@ServiceRanking(9006)
public class SecondImplForInterfaceOne implements OneInterfaceForTwoMethod {

    @Override
    public String myName() {
        return "SecondImplForInterfaceOne";
    }
}