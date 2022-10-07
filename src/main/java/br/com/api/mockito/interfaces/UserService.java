package br.com.api.mockito.interfaces;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;

import java.util.List;


public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO userDTO);

}
