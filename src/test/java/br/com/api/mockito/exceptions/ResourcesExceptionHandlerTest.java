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

import java.net.http.HttpClient;

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

    }

    @Test
    void dataIntegrationViolationException() {
    }
}