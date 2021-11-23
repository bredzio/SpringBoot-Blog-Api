package com.alkemy.blogAPI.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@SQLDelete(sql = "UPDATE category SET enabled = false WHERE category_Id = ?")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoryId;
    private String name;
    private boolean enabled;
    
    @JsonManagedReference
    @OneToMany(mappedBy="category")
    private List<Post> posts;
}
