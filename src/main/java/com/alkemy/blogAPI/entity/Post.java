package com.alkemy.blogAPI.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@SQLDelete(sql = "UPDATE Post SET enabled = false WHERE postId = ?")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer postId;
    private String title;
    private String content;
    private String image;
    @JsonFormat(pattern="dd/mm/yyyy")
    private Date dateCreation;
    
    @ManyToOne
    @JoinColumn
    private User user;
    
    @ManyToOne
    @JoinColumn
    private Category category;
}
