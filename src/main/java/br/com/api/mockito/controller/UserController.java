package br.com.api.mockito.controller;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    public static final String ID = "/{id}";
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = ID)
   public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(userService.findById(id),UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
      return  ResponseEntity.ok()
              .body(userService.findAll()
              .stream().map(x -> mapper.map(x, UserDTO.class))
              .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(userService.create(userDTO).getId()).toUri()).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        userDTO.setId(id);
        return ResponseEntity.ok().body(mapper.map(userService.update(userDTO), UserDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
