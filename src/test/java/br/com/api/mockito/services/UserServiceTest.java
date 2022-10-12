package br.com.api.mockito.services;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.exceptions.NotfoundException;
import br.com.api.mockito.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
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
    void findByIdObjectNotFound(){
        when(userRepository.findById(anyInt())).thenThrow(new NotfoundException("Objeto não encontrado"));
        try {
            userService.findById(1);
        }catch (Exception ex){
            assertEquals(NotfoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }

    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1,response.size());
        assertEquals(User.class, response.get(0).getClass());
        assertEquals("Sandoval", response.get(0).getName());
        assertEquals("sandovalbento@gmail.com", response.get(0).getEmail());
        assertEquals("1234", response.get(0).getPassword());
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
        user = new User(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        userDTO = new UserDTO(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        optionalUser = Optional.of( new User(1, "Sandoval", "sandovalbento@gmail.com", "1234"));

    }
}