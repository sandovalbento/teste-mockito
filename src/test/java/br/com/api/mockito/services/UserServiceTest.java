package br.com.api.mockito.services;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.exceptions.DataIntegrationViolationException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByEmail("sandovalbento@gmail.com")).thenReturn(optionalUser);
        User response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals("Sandoval", response.getName());
        assertEquals("sandovalbento@gmail.com", response.getEmail());
        assertEquals("1234", response.getPassword());

    }

    @Test
    void createViolation() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        try {
            optionalUser.get().setId(2);
            userService.create(userDTO);
        }catch (Exception e){
            assertEquals(DataIntegrationViolationException.class, e.getClass());
            assertEquals("E-mail já cadastrado no sistema", e.getMessage());
        }
    }

    @Test
    void update() {
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findByEmail("sandovalbento@gmail.com")).thenReturn(optionalUser);
        User response = userService.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals("Sandoval", response.getName());
        assertEquals("sandovalbento@gmail.com", response.getEmail());
        assertEquals("1234", response.getPassword());
    }

    @Test
    void updateViolation() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);
        try {
            optionalUser.get().setId(2);
            userService.create(userDTO);
        }catch (Exception e){
            assertEquals(DataIntegrationViolationException.class, e.getClass());
            assertEquals("E-mail já cadastrado no sistema", e.getMessage());
        }
    }

    @Test
    void delete() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        userService.delete(1);
        verify(userRepository, times(1)).deleteById(anyInt());
    }
    @Test
    void  deleteObjectNotFoundException(){
        when(userRepository.findById(anyInt())).thenThrow(new NotfoundException("Objeto não encontrado"));
        try {
            userService.delete(1);
        }catch (Exception e){
            assertEquals(NotfoundException.class, e.getClass());
            assertEquals("Objeto não encontrado", e.getMessage());
        }
    }


    private void startUser(){
        user = new User(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        userDTO = new UserDTO(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        optionalUser = Optional.of( new User(1, "Sandoval", "teste@gmail.com", "1234"));

    }
}