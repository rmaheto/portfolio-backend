package com.raymondaheto.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "display_order")
  private int displayOrder;

  @JsonManagedReference
  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("displayOrder ASC")
  @Builder.Default
  private List<ProjectTag> tags = new ArrayList<>();
}
