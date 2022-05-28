//package com.bakerybyhermann.Configuration;
//
//
//import com.bakerybyhermann.Model.Customer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//
//@Configuration // Kommer til at arbejde med godkendelse af brugere, og at denne klasse kommer til at bruge @Bean annotationen på en metode.
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
//
////         Configuration package bruges til at arbejde med authentification/godkendelse af eksempelvis brugere til et login.
////         Vores klasse extender fra WebSecurityConfigurerAdapter, for at override dens metode userDetailsService() nedenfor.
////         UserDetailsService (kig i metoden nedenfor i return type) er ansvarlig for at hente bruger-oplysningerne.
////         InMemoryUserDetailsManager bruges til at gemme en brugers username/password i Springs memory/hukommelse,
////         og henter oplysningerne derfra. Man skal ikke kalde denne her metode eller klasse i fx controlleren,
////         da Spring security kører denne klasse bag kulisserne.
//
////         Grunden til vi kan returne InMemoryUserDatailsManager nedenfor,
////         er fordi den implementerer UserDetailsService interfacet (som står øverst i metode return type)
//
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//        String username = System.getenv("username");
//        String password = System.getenv("password");
//        String role = System.getenv("role");
//
//        UserDetails newUser = User.withDefaultPasswordEncoder().
//                username(username).
//                password(password).
//                roles(role).
//                build();
//
//        InMemoryUserDetailsManager userMemory = new InMemoryUserDetailsManager(newUser);
//
//        return userMemory;
//    }
//
//
////    private static final String ENCODED_PASSWORD = "$2a$10$AIUufK8g6EFhBcumRRV2L.AQNz3Bjp7oDQVFiO5JJMBFZQ6x2/R/2";
////
////
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//      httpSecurity .csrf()
//              .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//              .and()
//              .authorizeRequests().
//              antMatchers("/","index","css/*","js/*").permitAll().
//            anyRequest().
//              authenticated().and().httpBasic();
//    }
//
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//
//
//
//}
