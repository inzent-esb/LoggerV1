package com.inzent.combine.logger;

import com.inzent.igate.util.message.MessageSource;
import com.inzent.imanager.dao.ExtendedHibernateTemplate;
import com.inzent.imanager.marshaller.JsonMarshaller;
import com.inzent.imanager.openapi.PageController;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoggerController extends PageController
{
  private static final String LOGGER_TITLE_FORMAT = "logger.title.format" ;
  private static final String LOGGER_TITLE_FORMAT_DEF = "[dev] - {0}" ;
  private static final String LOGGER_TITLE_NAME = "logger.title.name" ;
  private static final String LOGGER_TITLE_NAME_DEF = "LOGGER" ;

  @Value("#{systemProperties['yellowPage.login.logo.file.name'] ?: null}")
  private String loginLogoFileName ;

  @Value("#{systemProperties['yellowPage.menu.logo.file.name'] ?: null}")
  private String menuLogoFileName ;

  @Autowired
  private ResourceLoader resourceLoader ;

  @Autowired
  private MessageSource messageSource ;

  @Autowired
  @Qualifier("metaTemplate")
  protected ExtendedHibernateTemplate hibernateTemplate ;

  @Autowired
  @Qualifier("metaTransactionTemplate")
  protected TransactionTemplate transactionTemplate ;

  @Override
  public void getTitle(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    Map<String, Object> model = new HashMap<>() ;

    try
    {
      model.put(MODEL_OBJECT, MessageFormat.format(System.getProperty(LOGGER_TITLE_FORMAT, LOGGER_TITLE_FORMAT_DEF), System.getProperty(LOGGER_TITLE_NAME, LOGGER_TITLE_NAME_DEF))) ;
    }
    catch (Throwable t)
    {
      model.put(MODEL_ERROR, unwrapThrowable(t)) ;
    }

    apiRenderer.renderResponse(request, response, model) ;
  }

  @GetMapping(URI + "/logoFileName")
  public void getLogoFileName(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
    String type = (String) JsonMarshaller.unmarshal(IOUtils.toByteArray(request.getInputStream()), HashMap.class).get("type");
    
    Map<String, Object> model = new HashMap<>() ;

    try
    {
      model.put(MODEL_OBJECT, "login".equals(type) ? loginLogoFileName : menuLogoFileName) ;
    }
    catch (Throwable t)
    {
      model.put(MODEL_ERROR, unwrapThrowable(t)) ;
    }

    apiRenderer.renderResponse(request, response, model) ;
  }

  @GetMapping({ "/app/**", "/" })
  public String goIndexPage(HttpServletResponse response)
  {
    response.setContentType(new MediaType(MediaType.TEXT_HTML, StandardCharsets.UTF_8).toString()) ;

    return "forward:/index.html" ;
  }

  @GetMapping(URI + "/login")
  public String getLoginPage(HttpServletRequest request, @RequestParam(required = false) String _client_mode, Model model)
  {
    if (_client_mode != null)
      model.addAttribute("_client_mode", _client_mode) ;

    return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).replace(URI, "/auth/") ;
  }

  @GetMapping(URI + "/password")
  public String getPasswordPage(HttpServletRequest request, @RequestParam String userId, Model model)
  {
    model.addAttribute("userId", userId) ;

    return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).replace(URI, "/auth/") ;
  }

  @GetMapping(URI + "/customPage/**")
  public String getCustomPage(HttpServletRequest request, Model model)
  {
    if (null != request.getParameter("popupId"))
      model.addAttribute("popupId", request.getParameter("popupId")) ;

    return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).replace(URI, "/");
  }
}