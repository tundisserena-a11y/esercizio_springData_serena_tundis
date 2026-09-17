package it.aulab.progetto_blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // comunichiamo a spring che e presente la config di sicurezza
public class SecurityConfig {

    // metodo di codifica
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // crea l utente che puo accedere alla piattaforma
    @Bean
    public InMemoryUserDetailsManager userManager() {
        UserBuilder user = User.withUsername("user").password(encoder().encode("12345678"));
        UserBuilder admin = User.withUsername("admin").password(encoder().encode("admin12345678"));

        return new InMemoryUserDetailsManager(user.build(), admin.build());
    }

    // Configura i filtri di sicurezza e le regole di accesso agli endpoint
    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin.loginPage("/login")
                        // .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/authors", true)
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }

}
