package com.bakerybyhermann.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// Configuration bruges til at arbejde med at konfigurere brugere (godkendelse af brugere),
// og at denne klasse kommer til at bruge @Bean annotationen på en metode.
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//         Configuration package bruges til at arbejde med authentification/godkendelse af eksempelvis brugere til et login.
//         Vores klasse extender fra WebSecurityConfigurerAdapter, for at override dens metode userDetailsService() nedenfor.
//         UserDetailsService (kig i metoden nedenfor i return type) er ansvarlig for at hente bruger-oplysningerne.
//         InMemoryUserDetailsManager bruges til at gemme en brugers username/password i Springs memory/hukommelse,
//         og henter oplysningerne derfra. Man skal ikke kalde denne her metode eller klasse i fx controlleren,
//         da Spring security kører denne klasse bag kulisserne.

//         Grunden til vi kan returne InMemoryUserDatailsManager nedenfor,
//         er fordi den implementerer UserDetailsService interfacet (som står øverst i metode return type)


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails newUser =
                User.builder()
                        .username("admin")
                        .password("1234")
                        .roles("USER")
                        .build();

        InMemoryUserDetailsManager userMemory = new InMemoryUserDetailsManager(newUser);
        return userMemory;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
}



