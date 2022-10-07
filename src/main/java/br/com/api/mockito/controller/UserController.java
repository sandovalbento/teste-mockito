package br.com.api.mockito.controller;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.services.UserServiceImplement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    public static final String ID = "/{id}";
    @Autowired
    private UserServiceImplement userServiceImplement;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = ID)
   public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(userServiceImplement.findById(id),UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
      return  ResponseEntity.ok()
              .body(userServiceImplement.findAll()
              .stream().map(x -> mapper.map(x, UserDTO.class))
              .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(userServiceImplement.create(userDTO).getId()).toUri()).build();
    }
}
