package by.bsuir.dorm.config;

import by.bsuir.dorm.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsService() {
            @Override
            @Transactional(readOnly = true)
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                final UUID id;
                try {
                    id = UUID.fromString(username);
                } catch (IllegalArgumentException ex) {
                    throw new UsernameNotFoundException("Invalid user id: " + username);
                }
                return userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("User { id = " + id  +" } is not found"));
            }
        };
    }
}
