package com.inzent.combine.logger.repository;

import com.inzent.combine.logger.entity.ExceptionLog;
import org.springframework.stereotype.Repository;

@Repository
public class ExceptionLogRepository extends AbstractExceptionLogRepositoryRDB<ExceptionLog>
{
  public ExceptionLogRepository()throws Exception
  {
    super(ExceptionLog.class);
  }
}
