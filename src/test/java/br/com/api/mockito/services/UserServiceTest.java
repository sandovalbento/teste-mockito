package br.com.api.mockito.services;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void findById() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        User response = userService.findById(1);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(1, response.getId());
        assertEquals("Sandoval", response.getName());
        assertEquals("sandovalbento@gmail.com", response.getEmail());
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        User user = new User(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        userDTO = new UserDTO(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        optionalUser = Optional.of( new User(1, "Sandoval", "sandovalbento@gmail.com", "1234"));

    }
}