package com.raymondaheto.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "badge_url")
  private String badgeUrl;

  private String link;

  @Column(name = "display_order")
  private int displayOrder;
}
