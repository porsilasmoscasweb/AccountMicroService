package reloadly.ms_01.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import reloadly.ms_01.jwt.JWTAuthorizationFilter;
import reloadly.ms_01.model.Account;
import reloadly.ms_01.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AccountService service;

    AuthenticationManager auth;

    @Bean
    public InMemoryUserDetailsManager detailsManager() throws Exception {
//        return new InMemoryUserDetailsManager(this.getUsers());
        return new InMemoryUserDetailsManager(this.getAccounts());
    }

    private List<UserDetails> getUsers() {
        List<UserDetails> users = Arrays.asList(
                User.withUsername("developer")
                        .password("{noop}developer")
                        .authorities("USERS")
                        .build(),
                User.withUsername("admin")
                        .password("{noop}admin")
                        .authorities("ADMIN", "USERS")
                        .build()
        );

        return users;
    }

    private List<UserDetails> getAccounts() {
        List<UserDetails> accounts = new ArrayList<>();

        for (Account ac : service.all()) {
            accounts.add(
                    User.withUsername(ac.getName())
                            .password("{noop}" + ac.getPwd())
                            .authorities(ac.getRole())
                            .build()
            );
        }

        return accounts;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/accounts").authenticated()
                .antMatchers("/account").authenticated()
                .antMatchers(HttpMethod.PUT, "/account").authenticated()
                .antMatchers(HttpMethod.PUT, "/account/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/account/**").authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(auth));

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration conf) throws Exception {
        auth = conf.getAuthenticationManager();
        return auth;
    }
}
