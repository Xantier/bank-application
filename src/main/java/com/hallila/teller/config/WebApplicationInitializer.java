package com.hallila.teller.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   protected String[] getServletMappings() {
      return new String[]{"/"};
   }

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class<?>[]{ApplicationConfig.class, PersistenceConfig.class};
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class<?>[]{WebMvcConfig.class};
   }
}
