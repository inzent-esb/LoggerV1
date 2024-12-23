package com.inzent.combine.logger.controller;

import com.inzent.combine.logger.entity.ExceptionLog;
import com.inzent.combine.logger.service.ExceptionLogExportImport;
import com.inzent.combine.logger.service.ExceptionLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(AbstractExceptionLogController.URI)
public class ExceptionLogController extends AbstractExceptionLogController<ExceptionLog>
{
  public ExceptionLogController(ExceptionLogService exceptionLogService, ExceptionLogExportImport exceptionLogExportImport)
  {
    super(ExceptionLog.class, exceptionLogService, exceptionLogExportImport);
  }
}
