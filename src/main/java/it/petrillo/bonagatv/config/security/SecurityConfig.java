package it.petrillo.bonagatv.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/api/canale/getSpecial").authenticated()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .cors()
                .and()
                .csrf().disable()
                .securityContext((securityContext) -> securityContext
                .securityContextRepository(new HttpSessionSecurityContextRepository())).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new CustomPasswordEncoder());

        DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
        inMemoryProvider.setUserDetailsService(inMemoryUserDetailsService());
        inMemoryProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoAuthenticationProvider, inMemoryProvider);
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        UserDetails user = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService()  {
        return new UserDetailsServiceCustom();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
