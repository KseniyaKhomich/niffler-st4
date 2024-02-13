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
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.db.model.AuthorityEntity;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
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
				.setLogin(userAuth.getUsername())
				.setPassword(userAuth.getPassword())
				.submit();

		MainPage mainPage = new MainPage();
		mainPage.checkThatStatisticDisplayed();

		open(ProfilePage.PAGE_URL, ProfilePage.class)
				.addAvatar("images/duck.jpg");

		new MainPage()
				.checkAvatar("images/duck.jpg");
	}
}
