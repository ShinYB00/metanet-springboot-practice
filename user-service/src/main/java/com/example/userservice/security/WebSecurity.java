package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //권한과 관련된 메서드 (권한 처리)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users").permitAll(); //회원가입 요청 허락
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll(); //콘솔 요청 허락
        http.authorizeRequests().antMatchers("/error/**").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();


        //나머지는 로컬호스트로 들어오는 요청에 대한 권한을 주지 않음
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("127.0.0.1")
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    //해당 필터를 거쳐야 한다는 의미
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter
                = new AuthenticationFilter(authenticationManager(), userService, env);

        return authenticationFilter;
    }

    //인증과 관련된 메서드 (인증 처리)
    //준비한 것을 실행하기 위한 것
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
