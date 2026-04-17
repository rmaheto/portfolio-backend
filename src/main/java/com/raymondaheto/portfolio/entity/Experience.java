package com.raymondaheto.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String role;
  private String company;
  private String period;
  private String stack;

  @Column(name = "display_order")
  private int displayOrder;

  @JsonManagedReference
  @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("displayOrder ASC")
  @Builder.Default
  private List<ExperienceBullet> bullets = new ArrayList<>();
}
