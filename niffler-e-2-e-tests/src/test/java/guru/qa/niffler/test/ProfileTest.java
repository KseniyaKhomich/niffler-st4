package guru.qa.niffler.test;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.page.message.SuccessMsg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.mainPage.MainPage;
import guru.qa.niffler.page.ProfilePage;
import static com.codeborne.selenide.Selenide.open;

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


	@DbUser
	@Test
	void avatarShouldBeDisplayedInHeader(UserAuthEntity userAuth) {
		mainPage.open();

		new LoginPage()
				.loginAsUser(userAuth.getUsername(), userAuth.getPassword());

		MainPage mainPage = new MainPage();
		mainPage.mainContentShouldBeDisplayed();

		open(ProfilePage.PAGE_URL, ProfilePage.class)
				.addAvatar("images/duck.jpg");

		new MainPage()
				.checkAvatar("images/duck.jpg");
	}
}
