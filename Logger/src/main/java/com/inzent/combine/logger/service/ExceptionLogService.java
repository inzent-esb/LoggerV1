package com.inzent.combine.logger.service;

import com.inzent.combine.logger.entity.ExceptionLog;
import com.inzent.combine.logger.entity.LogPK;
import com.inzent.combine.logger.repository.ExceptionLogRepository;
import com.inzent.imanager.service.LogEntityService;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLogService extends LogEntityService<LogPK, ExceptionLog>
{
  public ExceptionLogService(ExceptionLogRepository exceptionLogRepository)
  {
    setEntityRepository(exceptionLogRepository) ;
  }
}
