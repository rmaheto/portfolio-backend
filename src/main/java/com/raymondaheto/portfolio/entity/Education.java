package com.raymondaheto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String school;
  private String years;

  @Column(name = "display_order")
  private int displayOrder;
}
