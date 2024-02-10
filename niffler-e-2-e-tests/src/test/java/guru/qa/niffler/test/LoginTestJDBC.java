package guru.qa.niffler.test;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.UserRepositoryExtension;
import guru.qa.niffler.jupiter.annotation.DbUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Type;

@ExtendWith(UserRepositoryExtension.class)
public class LoginTestJDBC extends BaseWebTest {
	@DbUser()
	@Test
	void loginJDBC(UserAuthEntity userAuth) {
		mainPage.open();
		welcomePage.clickLoginButton()
				.loginAsUser(userAuth.getUsername(), userAuth.getPassword())
				.mainContentShouldBeDisplayed();
	}
}
