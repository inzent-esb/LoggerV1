package com.inzent.combine.logger.service;

import com.inzent.combine.logger.entity.LogPK;
import com.inzent.combine.logger.entity.TraceLog;
import com.inzent.combine.logger.repository.TraceLogRepository;
import com.inzent.imanager.service.LogEntityService;
import org.springframework.stereotype.Service;

@Service
public class TraceLogService extends LogEntityService<LogPK, TraceLog>
{
  public TraceLogService(TraceLogRepository traceLogRepository)
  {
    setEntityRepository(traceLogRepository);
  }
}
