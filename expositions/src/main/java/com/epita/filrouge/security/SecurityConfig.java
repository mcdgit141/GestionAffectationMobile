package com.epita.filrouge.security;

import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger monLogger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserDetailsService userDetailServiceImpl;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false)
                .userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoderBis());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Autorisation avec basic Auth, CREDENTIALS à transmettre dans chaque requete http.
     /*           http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("gestaffectation/**").authenticated().anyRequest().hasRole("ADMIN")
                .and()
                .formLogin().disable();*/

        // Autorisation avec login prealable à toute requête.
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/gestaffectation/**").authenticated().anyRequest().permitAll()
            .and()
            .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(new AuthentificationLoginSuccessHandler())
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .failureHandler(new CustomAuthentificationFailureHandler())
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
                .invalidateHttpSession(true)
            .and()
            .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint() {});
    }

    private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                            final Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("Authentifié sur l'application GestionAffectationMobile");
        }
    }

    private class CustomAuthentificationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getOutputStream().println("*** AUTHENTIFICATION KO ****");
            response.getOutputStream().print("Login ou mdp incorrecte");
            monLogger.debug("*** AUTHENTIFICATION ECHOUEE ****");
            for (String param : request.getQueryString().split("&")) {
                monLogger.debug(param);
            }

        }
    }

    private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                    final Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println("Deconnecté de l'application GestionAffectationMobile");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoderBis() {
        return new BCryptPasswordEncoder();
    }
}