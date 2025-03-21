package itacademy.rentalapp2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(antMatcher("/main")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/error")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/addresses")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/addresses/save")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/addresses/edit/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/addresses/delete/*")).hasAuthority("ADMIN")

                        .requestMatchers(antMatcher("/apartments")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/apartments/save1")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/apartments/save2")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/apartments/choose-edit/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/apartments/edit1/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/apartments/edit2/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/apartments/delete/*")).hasAuthority("ADMIN")

                        .requestMatchers(antMatcher("/landlords")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/landlords/save")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/landlords/edit/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/landlords/delete/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/landlords/*/apartments")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/landlords/*/add-apartments")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/landlords/*/apartments/detach")).hasAuthority("ADMIN")

                        .requestMatchers(antMatcher("/tenants")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/tenants/save")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/tenants/edit/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/tenants/delete/*")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/tenants/*/apartments")).hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(antMatcher("/tenants/*/add-apartments")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher("/tenants/*/apartments/detach")).hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/main", true)
                        .permitAll()).logout(logout -> logout.logoutSuccessUrl("/login"));
        return http.build();
    }
}
