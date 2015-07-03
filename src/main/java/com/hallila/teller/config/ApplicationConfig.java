package com.hallila.teller.config;

import com.hallila.teller.App;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackageClasses = App.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {
/*

   @Bean
   public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
      ppc.setLocation(new ClassPathResource("/persistence.properties"));
      return ppc;
   }
*/

}