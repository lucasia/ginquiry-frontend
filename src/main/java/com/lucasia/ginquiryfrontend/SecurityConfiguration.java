package com.lucasia.ginquiryfrontend;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Value("${spring.security.user.name}")
    private String username;

    @Value(("${spring.security.user.password}"))
    private String password;

  /*  @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.eraseCredentials(false);
    }
*/
 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
                *//*
                .authorizeRequests()
                    //.antMatchers("/", "/home").permitAll()
                .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();

                 *//*
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");
    }*/

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }

}
