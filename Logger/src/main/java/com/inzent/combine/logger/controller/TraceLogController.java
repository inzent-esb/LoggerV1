package com.inzent.combine.logger.controller;

import com.inzent.combine.logger.context.LogViewerFinder;
import com.inzent.combine.logger.entity.TraceLog;
import com.inzent.combine.logger.service.TraceLogExportImport;
import com.inzent.combine.logger.service.TraceLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(AbstractTraceLogController.URI)
public class TraceLogController extends AbstractTraceLogController<TraceLog>
{
  public TraceLogController(TraceLogService traceLogService, LogViewerFinder logViewerFinder, TraceLogExportImport traceLogExportImport)
  {
    super(TraceLog.class, traceLogService, logViewerFinder, traceLogExportImport) ;
  }
}
