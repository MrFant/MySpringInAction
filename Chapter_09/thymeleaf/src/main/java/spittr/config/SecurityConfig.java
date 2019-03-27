package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import spittr.data.SpitterRepository;
import spittr.data.SpitterUserService;

import javax.sql.DataSource;

@Configuration
//@EnableWebSecurity
// 上面这个可以启用任何web项目的security
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  SpitterRepository spitterRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .formLogin()
        .loginPage("/login")
      .and()
        .logout()
          .logoutSuccessUrl("/")
      .and()
      .rememberMe()
        .tokenRepository(new InMemoryTokenRepositoryImpl())
        .tokenValiditySeconds(2419200)
        .key("spittrKey")
      .and()
       .httpBasic()
         .realmName("Spittr")
      .and()
      .authorizeRequests()
//            .antMatchers("/").hasRole("USER")
        .antMatchers("/").authenticated()
        .antMatchers("/spitter/me").authenticated()
        .antMatchers(HttpMethod.POST, "/spittles").authenticated()
        .anyRequest().permitAll();
  }

//  /*
//  * @Comment :  基于内存的用户存储
//  *
//  * @Author  : yii.fant@gmail.com
//  * @Date    : 2019-03-21
//  */
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .inMemoryAuthentication()
//        .withUser("user").password("password")
//            .roles("USER").and()
//        // roles方法是authorities方法简写，roles方法里会调用authorities方法
//        .withUser("admin").password("password")
//            .authorities("ROLE_USER","ROLE_ADMIN");
//
//  }

  /*
  * @Comment : 基于数据库表的认证，如果不配置自定义查询的话，对数据库模式有要求，参考官网：
  * https://docs.spring.io/spring-security/site/docs/5.2.0.BUILD-SNAPSHOT/reference/htmlsingle/#appendix-schema
  * 配置自定义查询在datasource后面的注释代码
  * @Author  : yii.fant@gmail.com
  * @Date    : 2019-03-21
  */
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.jdbcAuthentication().dataSource(dataSource);
//            .usersByUsernameQuery(
//                    "select username,password, true " +
//                            "from Spitter where username=?")
//            .authoritiesByUsernameQuery(
//                    "select username, 'ROLE_USER' from Spitter where username=?")
//              .groupAuthoritiesByUsername();
//  }

  /*
  * @Comment : 自定义的用户服务,SpitterUserService不管用户数据在哪存储，可以自定义loadUserByUsername方法
  *            甚至可以随意伪造，它只是要获得由Spitter对象组成的User对象（User对象即UserDetails的具体实现）
  *
  * @Author  : yii.fant@gmail.com
  * @Date    : 2019-03-21
  */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(new SpitterUserService(spitterRepository));
  }

  @Override
  // 配置Spring Security的Filter链
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }
}
