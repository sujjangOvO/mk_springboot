package com.example.moonkey.config;

import com.example.moonkey.jwt.JwtAccessDeniedHandler;
import com.example.moonkey.jwt.JwtAuthenticationEntryPoint;
import com.example.moonkey.jwt.JwtSecurityConfig;
import com.example.moonkey.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션을 메소드 단위로 사용하기 위한 어노테이션
@EnableWebSecurity // 기본적인 web 보안을 활성하하는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurer를 implement하는 방법도 있다.

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web){ // h2-console 하위 모든 요청, 파비콘 관련 요청은 Spring Security 로직을 수행하지 않도록 함.
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                );
    }

    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근 제한을 설정
                .antMatchers("/api/hello").permitAll() // "/api/hello"에 대한 요청은 인증없이 접근을 허용
                .anyRequest().authenticated(); // 나머지 요청들은 모두 인증을 받아야 한다.
    } */

    // jwt 패키지에 만든 5가지 클래스를 추가한다.

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ){
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // h2-console 설정

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세선 사용x

                .and()
                .authorizeRequests()
                .antMatchers("/api/accountSignup").permitAll()
                .antMatchers("/api/helloAccount").permitAll()
                .antMatchers("/api/aithenticate").permitAll() // 로그인 api
                .antMatchers("/api/signup").permitAll()  // 회원가입 api 이 2개는 토큰이 없는 상태에서 요청이 오기때문에 permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }

}
