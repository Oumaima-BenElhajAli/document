package com.example.document.service;

import com.example.document.model.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface DocumentService {
    Document store(MultipartFile file) throws IOException;
    Document getDocument(String id);
    Stream<Document> getAllDocuments();
}
