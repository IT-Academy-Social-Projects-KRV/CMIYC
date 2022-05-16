package com.ms.data.controller;

import com.ms.data.dto.SchemaFile;
import com.ms.data.dto.form.HtmlForm;
import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schemas")
public class DataController {

    @Autowired
    private CloudStorageService cloudStorageService;

    @GetMapping
    public List<SchemaFile> getAllSchemas() {
        return cloudStorageService.getAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<SchemaFile> getSchema(@PathVariable("name") String name) {
        return ResponseEntity.of(cloudStorageService.getOne(name));
    }

    @GetMapping("/{name}/content")
    public ResponseEntity<String> getSchemaContent(@PathVariable("name") String name) {
        return ResponseEntity.of(cloudStorageService.getFileContent(name));
    }

    @GetMapping("/{name}/json")
    public ResponseEntity<?> readSchemaToObject(@PathVariable("name") String name) {
        try {
            HtmlForm htmlForm = cloudStorageService.getHtmlForm(name);
            System.out.println(htmlForm);
            return ResponseEntity.of(Optional.of(htmlForm));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/selected")
    public HtmlForm getSelectedSchema() {
        return cloudStorageService.getSelectedSchema();
    }

    @PostMapping("/{name}/select")
    public void selectSchemaByName(@PathVariable("name") String name) {
        cloudStorageService.selectSchema(name);
    }

    @PostMapping
    public void uploadSchema(@RequestParam("file") MultipartFile file) {
        cloudStorageService.uploadSchema(file);
    }
}
