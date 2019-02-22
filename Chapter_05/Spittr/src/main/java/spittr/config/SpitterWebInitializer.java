package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import spittr.web.WebConfig;

public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  /*
    * @Comment : 这个方法返回若干个被@configure注解了的类，将用来配置contextLoader创建的应用上下文
    *
    * @Author  : FANYI731@pingan.com.cn
    * @Date    : 2019-01-25
    */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] { RootConfig.class };
  }

  /*
  * @Comment : 同上，将用来配置DispatcherServlet 上下文
  *
  * @Author  : FANYI731@pingan.com.cn
  * @Date    : 2019-01-25
  */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] { WebConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

}