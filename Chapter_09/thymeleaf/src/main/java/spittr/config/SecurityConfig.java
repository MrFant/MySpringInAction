package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
//@EnableWebSecurity
// 上面这个可以启用任何web项目的security
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
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
        .antMatchers("/").authenticated()
        .antMatchers("/spitter/me").authenticated()
        .antMatchers(HttpMethod.POST, "/spittles").authenticated()
        .anyRequest().permitAll();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("user").password("password")
            .roles("USER").and()
        // roles方法是authorities方法简写，roles方法里会调用authorities方法
        .withUser("admin").password("password")
            .authorities("ROLE_USER","ROLE_ADMIN");

  }

  @Override
  // 配置Spring Security的Filter链
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }
}
