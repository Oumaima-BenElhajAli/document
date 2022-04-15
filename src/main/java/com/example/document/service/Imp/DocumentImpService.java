package com.example.document.service.Imp;

import com.example.document.model.entity.Document;
import com.example.document.repository.DocumentRepository;
import com.example.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class DocumentImpService implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository ;
    @Override
    public Document store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Document Document = new Document(fileName, file.getContentType(), file.getBytes());
        return documentRepository.save(Document);
    }
    @Override
    public Document getDocument(String id) {
        return documentRepository.findById(id).get();
    }
    @Override
    public Stream<Document> getAllDocuments() {
        return documentRepository.findAll().stream();
    }
}
