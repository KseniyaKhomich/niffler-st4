package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.db.model.AuthorityEntity;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryJdbc implements UserRepository {

  private final DataSource authDs = DataSourceProvider.INSTANCE.dataSource(Database.AUTH);
  private final DataSource udDs = DataSourceProvider.INSTANCE.dataSource(Database.USERDATA);

  private final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Override
  public UserAuthEntity createInAuth(UserAuthEntity user) {
    try (Connection conn = authDs.getConnection()) {
      conn.setAutoCommit(false);

      try (PreparedStatement userPs = conn.prepareStatement(
          "INSERT INTO \"user\" " +
              "(username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
              "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
           PreparedStatement authorityPs = conn.prepareStatement(
               "INSERT INTO \"authority\" " +
                   "(user_id, authority) " +
                   "VALUES (?, ?)")
      ) {

        userPs.setString(1, user.getUsername());
        userPs.setString(2, pe.encode(user.getPassword()));
        userPs.setBoolean(3, user.getEnabled());
        userPs.setBoolean(4, user.getAccountNonExpired());
        userPs.setBoolean(5, user.getAccountNonLocked());
        userPs.setBoolean(6, user.getCredentialsNonExpired());

        userPs.executeUpdate();

        UUID authUserId;
        try (ResultSet keys = userPs.getGeneratedKeys()) {
          if (keys.next()) {
            authUserId = UUID.fromString(keys.getString("id"));
          } else {
            throw new IllegalStateException("Can`t find id");
          }
        }

        for (Authority authority : Authority.values()) {
          authorityPs.setObject(1, authUserId);
          authorityPs.setString(2, authority.name());
          authorityPs.addBatch();
          authorityPs.clearParameters();
        }

        authorityPs.executeBatch();
        conn.commit();
        user.setId(authUserId);
      } catch (Exception e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return user;
  }

  @Override
  public UserEntity createInUserdata(UserEntity user) {
    try (Connection conn = udDs.getConnection()) {
      try (PreparedStatement ps = conn.prepareStatement(
              "INSERT INTO \"user\" " +
                   "(username, currency) " +
                   "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getCurrency().name());
        ps.executeUpdate();

        UUID userId;
        try (ResultSet keys = ps.getGeneratedKeys()) {
          if (keys.next()) {
            userId = UUID.fromString(keys.getString("id"));
          } else {
            throw new IllegalStateException("Can`t find id");
          }
        }
        user.setId(userId);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return user;
  }

  @Override
  public Optional<UserAuthEntity> findByIdInAuth(UUID id) {
    UserAuthEntity userAuthEntity = new UserAuthEntity();

    try (Connection conn = authDs.getConnection()) {
      try (PreparedStatement usersPs = conn.prepareStatement("SELECT * FROM \"user\" WHERE id = ? ");
           PreparedStatement authPs = conn.prepareStatement("SELECT * FROM \"authority\" WHERE user_id = ? ")) {

        usersPs.setObject(1, id);
        authPs.setObject(1, id);

        try (ResultSet resultSet = usersPs.executeQuery()) {
          if (resultSet.next()) {
            userAuthEntity.setId(UUID.fromString(resultSet.getString("id")));
            userAuthEntity.setUsername(resultSet.getString("username"));
            userAuthEntity.setEnabled(resultSet.getBoolean("enabled"));
            userAuthEntity.setPassword(resultSet.getString("password"));
            userAuthEntity.setAccountNonExpired(resultSet.getBoolean("account_non_expired"));
            userAuthEntity.setAccountNonLocked(resultSet.getBoolean("account_non_locked"));
            userAuthEntity.setCredentialsNonExpired(resultSet.getBoolean("credentials_non_expired"));
          } else {
            return Optional.empty();
          }
        }

        try (ResultSet resultSet = authPs.executeQuery()) {
          List<AuthorityEntity> authorityEntityList = new ArrayList<>();

          while (resultSet.next()) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setId(UUID.fromString(resultSet.getString("id")));
            authorityEntity.setAuthority(Authority.valueOf(resultSet.getString("authority")));
            authorityEntity.setUser(userAuthEntity);
            authorityEntityList.add(authorityEntity);
          }

          userAuthEntity.setAuthorities(authorityEntityList);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.of(userAuthEntity);
  }

  @Override
  public Optional<UserEntity> findByIdInUserdata(UUID id) {
    try (Connection conn = udDs.getConnection()) {
      try (PreparedStatement usersPs = conn.prepareStatement("SELECT * FROM \"user\" WHERE id = ? ")) {

        usersPs.setObject(1, id);

        try (ResultSet resultSet = usersPs.executeQuery()) {
          if (resultSet.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.fromString(resultSet.getString("id")));
            userEntity.setUsername(resultSet.getString("username"));
            userEntity.setSurname(resultSet.getString("surname"));
            userEntity.setFirstname(resultSet.getString("firstname"));
            userEntity.setCurrency(CurrencyValues.valueOf(resultSet.getString("currency")));
            userEntity.setPhoto(resultSet.getBytes("photo"));

            return Optional.of(userEntity);
          }
        }
      }
    } catch (SQLException e) {
		throw new RuntimeException(e);
	}
    return Optional.empty();
  }

  @Override
  public void deleteInAuthById(UUID id) {
    try (Connection conn = authDs.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement authorityPs = conn.prepareStatement("DELETE FROM \"authority\" WHERE user_id = ?");
           PreparedStatement userPs = conn.prepareStatement("DELETE FROM \"user\" WHERE id = ?")) {

        authorityPs.setObject(1, id);
        userPs.setObject(1, id);

        authorityPs.executeUpdate();
        userPs.executeUpdate();
        conn.commit();

      } catch (Exception e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteInUserdataById(UUID id) {
    try (Connection conn = udDs.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement usersPs = conn.prepareStatement("DELETE FROM \"user\" WHERE id = ? ");
           PreparedStatement friendsPs = conn.prepareStatement("DELETE FROM friendship WHERE user_id = ?");
           PreparedStatement invitesPs = conn.prepareStatement("DELETE FROM friendship WHERE friend_id = ?")) {

        usersPs.setObject(1, id);
        friendsPs.setObject(1, id);
        invitesPs.setObject(1, id);
        usersPs.executeUpdate();
        friendsPs.executeUpdate();
        invitesPs.executeUpdate();

        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public UserEntity updateUserInUserdata(UserEntity userEntity) {
    try (Connection conn = udDs.getConnection()) {
      try (PreparedStatement usersPs = conn.prepareStatement("UPDATE \"user\" SET currency = ?, username = ? WHERE id = ?")) {
        usersPs.setString(1, userEntity.getCurrency().name());
        usersPs.setString(2, userEntity.getUsername());
        usersPs.setObject(3, userEntity.getId());
        usersPs.executeUpdate();
        return userEntity;
      }
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  public UserAuthEntity updateUserInAuth(UserAuthEntity userAuthEntity) {
    try (Connection conn = authDs.getConnection()) {
      conn.setAutoCommit(false);
      try (PreparedStatement usersPs = conn.prepareStatement("UPDATE \"user\" SET username = ? WHERE id = ?");
           PreparedStatement delAuthPs = conn.prepareStatement("DELETE FROM \"authority\" WHERE user_id = ? ");
           PreparedStatement insAuthPs = conn.prepareStatement("INSERT INTO \"authority\" (user_id, authority) VALUES (?, ?)")) {

        usersPs.setString(1, userAuthEntity.getUsername());
        usersPs.setObject(2, userAuthEntity.getId());
        usersPs.executeUpdate();

        delAuthPs.setObject(1, userAuthEntity.getId());
        delAuthPs.executeUpdate();

        List<Authority> authorities = userAuthEntity.getAuthorities()
                .stream()
                .map(AuthorityEntity::getAuthority)
                .toList();

        for (Authority authority : authorities) {
          insAuthPs.setObject(1, userAuthEntity.getId());
          insAuthPs.setObject(2, authority.name());
          insAuthPs.addBatch();
          insAuthPs.clearParameters();
        }
        insAuthPs.executeBatch();
        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException();
    }
    return userAuthEntity;
  }
}
