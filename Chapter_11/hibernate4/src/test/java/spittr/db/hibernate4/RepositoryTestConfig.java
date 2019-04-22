package spittr.db.hibernate4;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import spittr.domain.Spitter;
import spittr.domain.Spittle;

@Configuration
@EnableTransactionManagement
/*
* @Comment : 这里的组件扫描没有指明在哪个包下，所以默认是当前包
*            神奇的是在另一个main模块的【同名包】下的两个repository类也能被扫描到
*            @Author  : yii.fant@gmail.com
* @Date    : 2019-04-01
*/
@ComponentScan
public class RepositoryTestConfig implements TransactionManagementConfigurer {

  @Inject
  private SessionFactory sessionFactory;

  @Bean
  public DataSource dataSource() {
    //这里有两种写法
//    EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
//    edb.setType(EmbeddedDatabaseType.H2);
//    edb.addScript("spittr/db/hibernate4/schema.sql");
//    edb.addScript("spittr/db/hibernate4/test-data.sql");
//    EmbeddedDatabase embeddedDatabase = edb.build();
//    return embeddedDatabase;
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScripts("spittr/db/hibernate4/schema.sql","spittr/db/hibernate4/test-data.sql")
            .build();

  }

  public PlatformTransactionManager annotationDrivenTransactionManager() {
    System.out.println(sessionFactory);
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  @Bean
  public SessionFactory sessionFactoryBean(DataSource dataSource) {
    try {
      LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
      lsfb.setDataSource(dataSource);
      /*
      * @Comment : 使用packagesToScan属性告诉Spring扫描一个或多个包以查找域类，这些类通过注解的方式表明要使用
      * Hibernate进行持久化，这些类可以使用的注解包括JPA的@Entity或@MappedSuperclass以及Hibernate的@Entity。
      *
      * @Author  : yii.fant@gmail.com
      * @Date    : 2019-04-22
      */
      lsfb.setPackagesToScan("spittr.domain");

//      如果愿意的话，你还可以使用annotatedClasses属性来将应用程序中所有的持久化类以全限定名的方式明确列出：
//      lsfb.setAnnotatedClasses(Spitter.class, Spittle.class);

      Properties props = new Properties();
      props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
      lsfb.setHibernateProperties(props);
      lsfb.afterPropertiesSet();
      SessionFactory object = lsfb.getObject();
      return object;
    } catch (IOException e) {
      return null;
    }
  }
  /*
  * @Comment : 它会在所有拥有@Repository注解的类上添加一个通知器（advisor），这样就会
  * 捕获任何平台相关的异常并以Spring非检查型数据访问异常的形式重新抛出。
  *
  * @Author  : yii.fant@gmail.com
  * @Date    : 2019-04-22
  */
  @Bean
  public BeanPostProcessor persist(){
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
