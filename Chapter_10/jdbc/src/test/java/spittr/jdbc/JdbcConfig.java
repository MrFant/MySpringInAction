package spittr.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import spittr.db.SpitterRepository;
import spittr.db.SpittleRepository;
import spittr.db.jdbc.JdbcSpitterRepository;
import spittr.db.jdbc.JdbcSpittleRepository;

@Configuration
public class JdbcConfig {

  @Profile("dev")
  @Bean
  public DataSource embeddedDataSource() {
    // 使用h2数据库的内存运行模式
    return new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .addScripts("classpath:spittr/db/jdbc/schema.sql", "classpath:spittr/db/jdbc/test-data.sql")
      .build();
  }

  @Profile("qa")
  @Bean
  public DataSource Data(){
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:tcp://localhost/~/spitter");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    dataSource.setInitialSize(5);
    dataSource.setMaxIdle(10);
    return dataSource;
  }

  @Profile("prod")
  @Bean
  public DataSource prodDataSource(){
    JndiObjectFactoryBean jndiObjectFactoryBean=new JndiObjectFactoryBean();
    jndiObjectFactoryBean.setJndiName("jdbc/SpittrDS");
    jndiObjectFactoryBean.setResourceRef(true);
    jndiObjectFactoryBean.setProxyInterface(DataSource.class);
    return (DataSource) jndiObjectFactoryBean.getObject();
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
  
  @Bean
  public SpitterRepository spitterRepository(JdbcTemplate jdbcTemplate) {
    return new JdbcSpitterRepository(jdbcTemplate);
  }

  @Bean
  public SpittleRepository spittleRepository(JdbcTemplate jdbcTemplate) {
    return new JdbcSpittleRepository(jdbcTemplate);
  }
  
  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

}
