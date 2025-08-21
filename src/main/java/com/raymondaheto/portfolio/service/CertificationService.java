package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.CertDto;
import java.util.List;

public interface CertificationService {
  List<CertDto> list();

  CertDto create(CertDto dto);

  CertDto update(Long id, CertDto dto);

  void delete(Long id);

  void reorder(List<Long> idsInOrder);
}
