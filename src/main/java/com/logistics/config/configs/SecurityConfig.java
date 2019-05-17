package com.logistics.config.configs;

import com.logistics.entity.user.UserRole;
import com.logistics.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/orders/**").hasAuthority(UserRole.ADMIN.getRoleWithPrefix())
                    .antMatchers("/drivers/**").hasAuthority(UserRole.ADMIN.getRoleWithPrefix())
                    .antMatchers("/trucks/**").hasAuthority(UserRole.ADMIN.getRoleWithPrefix())
                    .antMatchers("/cargo/**").hasAuthority(UserRole.ADMIN.getRoleWithPrefix())
                    .antMatchers("/driver/**").hasAuthority(UserRole.DRIVER.getRoleWithPrefix())
                    .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                    .loginPage("/login")
                .and()
                .logout()
                    .logoutSuccessUrl("/");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
