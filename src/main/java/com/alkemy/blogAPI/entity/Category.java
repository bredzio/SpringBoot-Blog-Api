package com.alkemy.blogAPI.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@SQLDelete(sql = "UPDATE category SET enabled = false WHERE categoryId = ?")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoryId;
    private String name;
    
    @OneToMany(mappedBy="category")
    private List<Post> posts;
}
