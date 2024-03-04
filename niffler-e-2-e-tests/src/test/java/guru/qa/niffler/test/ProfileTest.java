package guru.qa.niffler.test;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.page.message.SuccessMsg;
import guru.qa.niffler.test.BaseWebTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(UserRepositoryExtension.class)
public class ProfileTest extends BaseWebTest {

	@DbUser
	@Test
	public void addCategory(UserAuthEntity userAuth) {
		String categoryName = "categoryName";

		mainPage.open();

	    welcomePage
				.clickLoginButton()
				.loginAsUser(userAuth.getUsername(), userAuth.getPassword())
				.clickOnProfileHeaderTab()
				.addCategory(categoryName)
				.checkMessage(SuccessMsg.CATEGORY_MSG);

		List<String> categories = profilePage.getCategories();
		assertTrue(categories.contains(categoryName));
	}

	@DbUser
	@Test
	public void updateProfile(UserAuthEntity userAuth) {
		mainPage.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(userAuth.getUsername(), userAuth.getPassword())
				.clickOnProfileHeaderTab()
				.updateUserInfo("random","random", CurrencyValues.USD)
				.checkMessage(SuccessMsg.PROFILE_MSG);
	}
}
