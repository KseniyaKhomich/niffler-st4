package guru.qa.niffler.test;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.db.repository.UserRepositoryJdbc;
import guru.qa.niffler.jupiter.DBUserExtension;
import guru.qa.niffler.jupiter.UserRepositoryExtension;
import guru.qa.niffler.jupiter.annotation.DbUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UserRepositoryExtension.class)
public class LoginTestJDBC extends BaseWebTest {
//	@DbUser
//	@Test
//	void loginJDBC(UserAuthEntity userAuth) {
//		mainPage.open();
//		welcomePage.clickLoginButton()
//				.loginAsUser(userAuth.getUsername(), userAuth.getPassword());
//	}


	@DbUser
	@Test
	void update(UserAuthEntity userAuth) {

	}



}
