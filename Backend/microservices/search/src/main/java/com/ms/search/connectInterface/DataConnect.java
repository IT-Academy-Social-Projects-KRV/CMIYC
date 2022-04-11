package com.ms.search.connectInterface;

import com.ms.search.model.XmlObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.xml.bind.JAXBException;
import java.util.List;

@FeignClient(name = "data")
public interface DataConnect {

    @GetMapping("/schemas")
    List<XmlObject> xmlSchema(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) throws JAXBException;
}
