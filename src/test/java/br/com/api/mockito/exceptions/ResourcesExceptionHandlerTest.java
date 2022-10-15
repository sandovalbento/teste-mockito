package br.com.api.mockito.exceptions;

import br.com.api.mockito.entity.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourcesExceptionHandlerTest {

    @InjectMocks
    private ResourcesExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }

    @Test
    void notFound() {
        ResponseEntity<Error> response = exceptionHandler
                .notFound(
                        new NotfoundException("Objeto não encontrado"),
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("Objeto não encontrado", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimeStamp());

    }

    @Test
    void dataIntegrationViolationException() {
        ResponseEntity<Error> response = exceptionHandler
                .dataIntegrationViolationException(
                        new DataIntegrationViolationException("E-mail já cadastrado no sistema"),
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Error.class, response.getBody().getClass());
        assertEquals("E-mail já cadastrado no sistema", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}