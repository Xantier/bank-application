package com.hallila.teller.config;

import com.hallila.teller.App;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = App.class, includeFilters = @ComponentScan.Filter(Controller.class), useDefaultFilters = false)
class WebMvcConfig extends WebMvcConfigurationSupport {

   private static final String RESOURCES_LOCATION = "/resources/";
   private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

   @Override
   public RequestMappingHandlerMapping requestMappingHandlerMapping() {
      RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
      requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
      requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
      return requestMappingHandlerMapping;
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
   }

   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }

}
