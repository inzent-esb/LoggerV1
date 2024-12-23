package com.inzent.combine.logger.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IGT_EXCEPTION_LOG")
@Proxy(lazy = false)
public class ExceptionLog extends AbstractExceptionLog
{
  private static final long serialVersionUID = -3206818535415088093L;

  public ExceptionLog() {}

  public ExceptionLog(LogPK pk)
  {
    super(pk);
  }
}
