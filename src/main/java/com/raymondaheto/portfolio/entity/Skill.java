package com.raymondaheto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String category;

  @Column(nullable = false)
  private String name;

  @Column(name = "display_order")
  private int displayOrder;
}
