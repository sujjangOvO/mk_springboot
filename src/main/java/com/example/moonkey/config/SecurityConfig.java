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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션을 메소드 단위로 사용하기 위한 어노테이션
@EnableWebSecurity // 기본적인 web 보안을 활성하하는 어노테이션
public class SecurityConfig { // WebSecurityConfigurer를 implement하는 방법도 있다.

    private final CorsFilter corsFilter;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // jwt 패키지에 만든 5가지 클래스를 추가한다.

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ){
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.corsFilter = corsFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세선 사용x

                .and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
 // 회원가입 api 이 2개는 토큰이 없는 상태에서 요청이 오기때문에 permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

}
/*
                .antMatchers("/api/accountSignup").permitAll()
                .antMatchers("/api/helloAccount").permitAll()
                .antMatchers("/api/authenticate").permitAll() // 로그인 api
                .antMatchers("/api/signup").permitAll()
 */