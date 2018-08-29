package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/TaskSite/index").permitAll()
                .antMatchers("/TaskSiteIndex").permitAll()
                .antMatchers("/assemblerDoorChallenge").permitAll()
                .antMatchers("/assemblerDoorChallenge/sendCommand").permitAll()
                .antMatchers("/TaskSite/*").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/TaskSite/login").permitAll()
                .and().logout()
                .and()
                .httpBasic().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource) ;
                //.passwordEncoder(new BCryptPasswordEncoder());
    }
}
