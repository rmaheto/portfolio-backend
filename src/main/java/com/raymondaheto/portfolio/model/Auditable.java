package com.raymondaheto.portfolio.model;

import java.io.Serializable;

public interface Auditable extends Serializable {
  Audit getAudit();
}
