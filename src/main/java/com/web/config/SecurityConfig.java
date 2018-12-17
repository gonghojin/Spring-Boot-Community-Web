package com.web.config;

import com.web.oauth.ClientResources;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
            .authorizeRequests()
                .antMatchers("/", "/login/**", "/css/**", "/images/**", "/js/**"
                            , "/console/**"
                ).permitAll()
                .anyRequest().authenticated()
            .and()
                .headers().frameOptions().disable() //응답에 해당하는 헤더 .XFrameOptionHeaderWriter의 최적화 설정을 허용하지 않음
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")) // 인증되지 않은 사용자가 허용되지 않은 경로로 리퀘스트를 요청할 경우 '
            .and()                                                                          // login으로 이동
                .formLogin()
                .successForwardUrl("/board/list")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
                .addFilterBefore(filter, CsrfFilter.class) // 첫번째 인자보다 먼저 시작될 필터를 등록 즉, 문자 인코딩 필터보다 CsrfFilter가 먼저
                .csrf().disable();
    }

    @Bean
    @ConfigurationProperties("facebook") // 어노테이션 접두사를 사용하여 바인딩
    public ClientResources facebook() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("kakao")
    public ClientResources kakao() {
        return new ClientResources();
    }
}

/**
 소셜 미디어 리소스 정보는 시큐리티 설정에서 사용하기 때문에 빈으로 등록
 **/