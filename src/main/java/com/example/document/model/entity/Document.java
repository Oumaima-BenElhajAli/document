package com.example.document.model.entity;
import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Document implements Serializable {
    @Id
@GeneratedValue(generator = "uuid")
@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;
    @Column(name="nom")
    private String nom;
    @Column(name="type")
    private String type;

    @Lob
    private byte[] data;
    public Document(String nom, String type, byte[] data) {
        this.nom = nom;
        this.type = type;
        this.data = data;
    }

}
