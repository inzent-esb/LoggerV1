package com.inzent.combine.logger.viewer;

import com.inzent.combine.logger.LogViewer;
import com.inzent.combine.logger.entity.AbstractTraceLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class XmlLogViewer implements LogViewer
{
  protected final Log logger = LogFactory.getLog(getClass()) ;

  @Override
  public String getDump(AbstractTraceLog traceLog)
  {
    try (StringWriter sw = new StringWriter())
    {
      new XMLWriter(sw, OutputFormat.createPrettyPrint()).write(SAXReader.createDefault().read(new ByteArrayInputStream(traceLog.getLogData()))) ;

      return sw.toString();
    }
    catch (Throwable th)
    {
      if (logger.isWarnEnabled())
        logger.warn(th.getMessage(), th) ;

      return null ;
    }
  }
}
