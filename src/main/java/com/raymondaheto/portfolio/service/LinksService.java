package com.raymondaheto.portfolio.service;

import com.raymondaheto.portfolio.dto.LinksDto;
import com.raymondaheto.portfolio.dto.LinksUpdateDto;

public interface LinksService {

  LinksDto get();

  LinksUpdateDto getBoth();

  LinksUpdateDto update(LinksUpdateDto dto);
}
