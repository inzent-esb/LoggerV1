package com.inzent.combine.logger.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "IGT_TRANSACTION_LOG")
@Proxy(lazy = false)
public class TraceLog extends AbstractTraceLog
{
  private static final long serialVersionUID = 5099012194581436547L;

  public TraceLog() {}

  public TraceLog(LogPK pk)
  {
    super(pk);
  }
}
