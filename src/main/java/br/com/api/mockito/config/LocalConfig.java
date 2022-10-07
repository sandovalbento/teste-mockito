package br.com.api.mockito.config;

import br.com.api.mockito.entity.User;
import br.com.api.mockito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
   public void startDB(){
        User user1 = new User(null, "Sandoval", "sandovalbento@gmail.com","123");
        User user2 = new User(null, "Kascia","kascia@gmail.com" ,"123");

        userRepository.saveAll(List.of(user1, user2));
    }
}
