package com.ms.search.connectInterface;

import com.ms.search.model.XmlObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.bind.JAXBException;
import java.util.List;

@FeignClient(name="data")
public interface DataConnect {
    @GetMapping("/api/data")
    public List<XmlObject> welcomeFromData() throws JAXBException ;
}
