package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiFieldLearningModel {

    @SlingObject
    private Resource resource;

    @ValueMapValue
    @Default(values = "Wknd")
    private String authorname;

    @ValueMapValue
    private String [] books;

    private List<Map<String, String>> bookmap;

    @PostConstruct
    protected void init() {
        bookmap = new ArrayList<>();
        Resource bookswithmap = resource.getChild("bookswithmap");

        for (Resource book : bookswithmap.getChildren()) {
            Map<String, String> bb = new HashMap<>();
            bb.put("bookname", book.getValueMap().get("bookname").toString());
            bb.put("booksubject", book.getValueMap().get("booksubject").toString());
            bb.put("booksyear", book.getValueMap().get("booksyear").toString());
            bookmap.add(bb);
        }

    }

    public String getAuthorname() {
        return authorname;
    }

    public String[] getBooks() {
        return books;
    }

    public List<Map<String, String>> getBookmap() {
        return bookmap;
    }
}
