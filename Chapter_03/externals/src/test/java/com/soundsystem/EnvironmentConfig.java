package com.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:/com/soundsystem/app.properties")
public class EnvironmentConfig {

  @Autowired
  Environment env;

  @Value("${disc.title}")
  String title;

  @Value("${disc.artist}")
  String artist;

  @Bean
  public BlankDisc blankDisc() {
    return new BlankDisc(
        env.getProperty("disc.title"),
        env.getProperty("disc.artist"));
//    return new BlankDisc(this.title,this.artist);
  }



  @Bean
  // 这里一定要是static 方法，否则会出错
  public static PropertySourcesPlaceholderConfigurer properties() {
    PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//    Resource[] resources = new ClassPathResource[]{new ClassPathResource("/com/soundsystem/app.properties")};
//    pspc.setLocations(resources);
//    pspc.setIgnoreUnresolvablePlaceholders(true);
    return pspc;
  }
}
