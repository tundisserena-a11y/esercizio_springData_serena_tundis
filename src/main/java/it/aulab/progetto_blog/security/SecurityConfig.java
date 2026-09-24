package it.aulab.progetto_blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
        // attributo statico
        private final static String cspDirectives = "default-src 'self'; img-src 'self'; script-src 'self' https://cdn.jsdelivr.net 'unsafe-inline'; style-src 'self' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; font-src https://cdnjs.cloudflare.com";

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

        @Bean
        public SecurityFilterChain configSecurityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(
                                (authorize) -> authorize.requestMatchers("/", "/index.html", "/css/**", "/api/**")
                                                .permitAll()

                                                .anyRequest().authenticated())
                                .formLogin((formLogin) -> formLogin.loginPage("/login")
                                                .defaultSuccessUrl("/authors", true)
                                                .permitAll())
                                .logout((logout) -> logout.logoutUrl("/logout")
                                                .logoutSuccessUrl("/"))
                                .csrf(
                                                (csrf) -> csrf.ignoringRequestMatchers("/api/**")) // non chiede csrf da
                                                                                                   // richieste api
                                .headers(
                                                (headers) -> headers.xssProtection(Customizer.withDefaults()) // protegge
                                                                                                              // da
                                                                                                              // attacchi
                                                                                                              // xss
                                                                .contentSecurityPolicy(Customizer.withDefaults())
                                                                .contentSecurityPolicy((csp) -> csp
                                                                                .policyDirectives(cspDirectives)));
                return http.build();
        }

}
