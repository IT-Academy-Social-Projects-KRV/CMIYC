package com.ms.data.controller;

import com.ms.data.dto.SchemaFile;
import com.customstarter.model.schema.Schema;
import com.ms.data.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @DeleteMapping("/{name}")
    public void deleteSchema(@PathVariable("name") String name) throws IOException {
        cloudStorageService.deleteSchema(name);
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
            Schema schema = cloudStorageService.readSchema(name);
            return ResponseEntity.of(Optional.of(schema));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/selected")
    public Schema getSelectedSchema() {
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
