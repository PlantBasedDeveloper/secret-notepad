package com.example.user.security;

import com.example.user.core.ApplicationUserManager;
import com.example.user.core.model.UserRole;
import com.example.user.security.jwt.JwtConfiguration;
import com.example.user.security.jwt.filters.JwtTokenVerifierFilter;
import com.example.user.security.jwt.filters.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final ApplicationUserManager applicationUserManager;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey jwtSecretKey;
    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public ApplicationSecurityConfig(ApplicationUserManager applicationUserManager,
                                     AuthenticationConfiguration authenticationConfiguration,
                                     PasswordEncoder passwordEncoder,
                                     SecretKey jwtSecretKey,
                                     JwtConfiguration jwtConfiguration) {
        this.applicationUserManager = applicationUserManager;
        this.authenticationConfiguration = authenticationConfiguration;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity
                .sessionManagement() //JWT Auth
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and() //JWT Auth
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtConfiguration, jwtSecretKey)) //JWT Auth
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfiguration, jwtSecretKey), JwtUsernamePasswordAuthenticationFilter.class) //JWT Verify
                .authorizeRequests()
                    .antMatchers("/h2-console/**","/", "/index", "/css/*", "/js/*").permitAll()
                    .antMatchers("/api/**").hasRole(UserRole.USER.name())
                    .anyRequest()
                    .authenticated();
//                .and()
//                .formLogin() //Form Based Auth
//                    .loginPage("/login")
//                    .permitAll()
//                    .defaultSuccessUrl("/courses", true)
//                    .passwordParameter("password")
//                    .usernameParameter("username")
//                    .and()
//                    .rememberMe()
//                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                        .key("somethingVerySecured")
//                        .rememberMeParameter("remember-me")
//                    .and()
//                    .logout()
//                        .logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout" ,"GET"))
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID", "remember-me")
//                        .logoutSuccessUrl("/login");

        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity
                .csrf().disable();
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        return httpSecurity.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(applicationUserManager);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
