package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  static UserRepository getInstance() {
    String repositoryParameter = System.getProperty("repository");

    if (repositoryParameter != null) {
      return switch (repositoryParameter) {
        case "jdbc" -> new UserRepositoryJdbc();
        case "sjdbc" -> new UserRepositorySJdbc();
        case "hibernate" -> new UserRepositoryHibernate();
        default -> throw new IllegalArgumentException("Incorrect value for Repository Parameter.");
      };

    } else {
      throw new IllegalArgumentException("Repository parameter isn't found.");

    }
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
