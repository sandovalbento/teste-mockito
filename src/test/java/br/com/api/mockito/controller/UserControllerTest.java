package br.com.api.mockito.controller;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDTO;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void findById() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response =  userController.findById(1);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(1, response.getBody().getId());
        assertEquals("Sandoval", response.getBody().getName());
        assertEquals("sandovalbento@gmail.com", response.getBody().getEmail());
        assertEquals("1234", response.getBody().getPassword());
    }

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        assertEquals(1,response.getBody().size());
        assertEquals("Sandoval", response.getBody().get(0).getName());
        assertEquals("sandovalbento@gmail.com", response.getBody().get(0).getEmail());
        assertEquals("1234", response.getBody().get(0).getPassword());

    }

    @Test
    void create() {
        when(userService.create(any())).thenReturn(user);
        ResponseEntity<UserDTO> response = userController.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void update() {
        when(userService.update(userDTO)).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.update(1,userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
    }

    @Test
    void delete() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<UserDTO> response = userController.delete(1);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(userService, times(1)).delete(anyInt());

    }

    private void startUser(){
        user = new User(1, "Sandoval", "sandovalbento@gmail.com", "1234");
        userDTO = new UserDTO(1, "Sandoval", "sandovalbento@gmail.com", "1234");
    }
}