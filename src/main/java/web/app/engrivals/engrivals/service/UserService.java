package web.app.engrivals.engrivals.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;
import web.app.engrivals.engrivals.persistance.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepo;

  public UserService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public UserEntity create (UserEntity user){
    return this.userRepo.save(user);
  }
  public List<UserEntity> getAll (){
    return this.userRepo.findAll();
  }
  public void deleteById(String id){
    this.existById(id);
    this.userRepo.deleteById(id);
  }
  public UserEntity findById (String id){
    Optional<UserEntity> userOptional = this.userRepo.findById(id);
    if(userOptional.isEmpty()){
      throw new EntityNotFoundException("no existe un usuario con id: " + id);
    }
    return userOptional.get();
  }
  public UserEntity update (UserEntity user){
    this.existById(user.getId());
    return this.userRepo.save(user);
  }
  private void existById(String id){
    boolean isExist = this.userRepo.existsById(id);
    if(!isExist)  throw new EntityNotFoundException("no existe un usuario con id: " + id);
  }
}
