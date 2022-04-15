package com.example.document.rest.controller;

import com.example.document.message.ResponseFile;
import com.example.document.message.ResponseMessage;
import com.example.document.model.entity.Document;
import com.example.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @PostMapping("/adddocument")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("document") MultipartFile file) {
        String message = "";
        try {
            documentService.store(file);
            message = "Le fichier a été téléchargé avec succès:" + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Impossible de télécharger le fichier: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/documents")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> documents = documentService.getAllDocuments().map(document -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(document.getId())
                    .toUriString();
            return new ResponseFile(
                    document.getNom(),
                    fileDownloadUri,
                    document.getType(),
                    document.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(documents);
    }
    @GetMapping("/documents/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Document document = documentService.getDocument(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getNom() + "\"")
                .body(document.getData());
    }
}
