package br.com.api.mockito.services;

import br.com.api.mockito.dto.UserDTO;
import br.com.api.mockito.entity.User;
import br.com.api.mockito.exceptions.DataIntegrationViolationException;
import br.com.api.mockito.exceptions.NotfoundException;
import br.com.api.mockito.interfaces.UserService;
import br.com.api.mockito.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
         Optional<User> object = userRepository.findById(id);
         return object.orElseThrow(() -> new NotfoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO userDTO) {
        findByEmail(userDTO);
        return userRepository.save(mapper.map(userDTO, User.class));
    }

    private void  findByEmail(UserDTO userDTO){
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if(user.isEmpty()){
            throw new DataIntegrationViolationException("E-mail já cadastrado no sistema");
        }
    }
}
