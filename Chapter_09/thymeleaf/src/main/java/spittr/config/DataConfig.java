package spittr.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;
import spittr.data.JdbcSpitterRepository;

@Configuration
public class DataConfig {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .build();
  }
  
  @Bean
  public JdbcOperations jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

//  @Bean
//  public DataSource prodDataSource(){
//    JndiObjectFactoryBean jndiObjectFactoryBean=new JndiObjectFactoryBean();
//    jndiObjectFactoryBean.setJndiName("jndi/h2");
//    jndiObjectFactoryBean.setResourceRef(true);
//    jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
//    return (DataSource) jndiObjectFactoryBean.getObject();
//  }
}
