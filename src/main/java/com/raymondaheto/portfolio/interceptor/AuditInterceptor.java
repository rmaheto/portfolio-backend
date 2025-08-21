package com.raymondaheto.portfolio.interceptor;

import com.raymondaheto.portfolio.model.Audit;
import com.raymondaheto.portfolio.model.Auditable;
import com.raymondaheto.portfolio.utils.SecurityUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AuditInterceptor {

  @PrePersist
  public void setCreationAudit(final Object entity) {
    if (entity instanceof final Auditable auditable) {
      Audit audit = auditable.getAudit();
      if (ObjectUtils.isEmpty(audit)) {
        audit = new Audit();
      }
      final String user =
          StringUtils.isBlank(SecurityUtils.getCurrentUsername())
              ? SecurityUtils.getCurrentUsername()
              : Audit.SYSTEM;
      audit.setCreates(user, Audit.PROGRAM);
    }
  }

  @PreUpdate
  public void setUpdateAudit(final Object entity) {
    if (entity instanceof final Auditable auditable) {
      Audit audit = auditable.getAudit();
      if (ObjectUtils.isEmpty(audit)) {
        audit = new Audit();
      }
      audit.setUpdates(SecurityUtils.getCurrentUsername(), Audit.PROGRAM);
    }
  }
}
