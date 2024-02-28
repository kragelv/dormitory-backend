package by.bsuir.dorm.config;

import by.bsuir.dorm.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            final UUID id = UUID.fromString(username);
            return userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User { id = %s } is not found", id)));
        };
    }
}
