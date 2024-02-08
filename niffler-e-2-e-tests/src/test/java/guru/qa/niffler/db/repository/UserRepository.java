package guru.qa.niffler.db.repository;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.config.DockerConfig;
import guru.qa.niffler.config.LocalConfig;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  //update here if noting or different value from both
  static UserRepository getInstance() {

    return "jdbc".equals(System.getProperty("repository"))
            ? new UserRepositoryJdbc()
            : new UserRepositorySJdbc();
  }

  UserAuthEntity createInAuth(UserAuthEntity user);

  Optional<UserAuthEntity> findByIdInAuth(UUID id);

  UserEntity createInUserdata(UserEntity user);

  Optional<UserEntity> findByIdInUserdata(UUID id);

  void deleteInAuthById(UUID id);

  void deleteInUserdataById(UUID id);

  UserAuthEntity updateUserInAuth(UserAuthEntity userAuthEntity);

  UserEntity updateUserInUserdata(UserEntity userEntity);
}
