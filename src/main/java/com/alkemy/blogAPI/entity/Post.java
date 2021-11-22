package com.alkemy.blogAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE post SET enabled = false WHERE post_Id = ?")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer postId;
    private String title;
    private String content;
    private String image;
    private boolean enabled;
    
    @CreatedDate
    @Column(nullable=false, updatable=false)
    private LocalDateTime dateCreation;
    
    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private User user;
    
    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Category category;

    
}
