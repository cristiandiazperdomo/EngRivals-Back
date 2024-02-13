package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;
import web.app.engrivals.engrivals.persistance.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import web.app.engrivals.engrivals.Dtos.UserDTO;
import web.app.engrivals.engrivals.persistance.entities.EnglishLevel;
import web.app.engrivals.engrivals.persistance.repository.LevelRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private LevelRepository levelRepository;

    public UserEntity create(UserDTO userDTO){
        UserEntity user = new UserEntity();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return userRepo.save(user);
    }
    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }
    
    public void deleteById(String id) {
        existById(id);
        userRepo.deleteById(id);
    }
    
    public UserEntity findById(String id) {
        Optional<UserEntity> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("no existe un usuario con id: " + id);
        }
        return userOptional.get();
    }
    
//    public UserEntity update(UserDTO userDTO) {
//        existById(userDTO.getId());
//
//        UserEntity user = userRepo.findById(userDTO.getId()).get();
//
//        user.setName(userDTO.getName());
//        user.setProfile_url(userDTO.getProfile_url());
//        user.setEmail(userDTO.getEmail());
//        user.setBirthdate(userDTO.getBirthdate());
//
//        Optional<EnglishLevel> level = levelRepository.findById(userDTO.getLevel_id_level().getIdLevel());
//
//        if (!level.isPresent()) {
//            throw new EntityNotFoundException("No existe un nivel de inglés asociado a este id: " + userDTO.getLevel_id_level().getIdLevel());
//        }
//
//        user.setEnglishLevel_id_level(level.get());
//
//        return userRepo.save(user);
//    }

    private void existById(String id) {
        boolean isExist = userRepo.existsById(id);
        if (!isExist) throw new EntityNotFoundException("no existe un usuario con id: " + id);
    }

}
