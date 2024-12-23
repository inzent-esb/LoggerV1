package com.inzent.combine.logger.repository;

import com.inzent.combine.logger.entity.TraceLog;
import org.springframework.stereotype.Repository;

@Repository
public class TraceLogRepository extends AbstractTraceLogRepositoryRDB<TraceLog>
{
  public TraceLogRepository()throws Exception
  {
    super(TraceLog.class);
  }
}
