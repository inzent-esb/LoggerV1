package com.inzent.combine.logger.viewer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inzent.combine.logger.LogViewer;
import com.inzent.combine.logger.entity.AbstractTraceLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonLogViewer implements LogViewer
{
  protected static ObjectMapper objectMapper = new ObjectMapper() ;

  protected final Log logger = LogFactory.getLog(getClass()) ;

  @Override
  public String getDump(AbstractTraceLog traceLog)
  {
    try
    {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(traceLog.getLogData())) ;
    }
    catch (Throwable th)
    {
      if (logger.isWarnEnabled())
        logger.warn(th.getMessage(), th) ;

      return null ;
    }
  }
}
