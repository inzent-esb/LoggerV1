package com.inzent.combine.logger.viewer;

import com.inzent.combine.logger.LogViewer;
import com.inzent.combine.logger.entity.AbstractTraceLog;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.text.WordUtils;

public class ByteLogViewer implements LogViewer
{
  @Override
  public String getDump(AbstractTraceLog traceLog)
  {
    return WordUtils.wrap(WordUtils.wrap(Hex.encodeHexString(traceLog.getLogData()), 2, " ", true), 8 * 3, "\n", true) ;
  }
}
