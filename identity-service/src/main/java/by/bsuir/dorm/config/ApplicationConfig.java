package by.bsuir.dorm.config;

import by.bsuir.dorm.dao.UserRepository;
import by.bsuir.dorm.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsService() {
            @Override
            @Transactional(readOnly = true)
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                final UUID id = UUID.fromString(username);
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("User { id = %s } is not found", id)));
                user.getAuthorities(); //triggers lazy fetch
                return user;
            }
        };
    }
}
