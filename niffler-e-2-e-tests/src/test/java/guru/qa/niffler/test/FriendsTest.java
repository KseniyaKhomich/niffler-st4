package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

@ExtendWith(UsersQueueExtension.class)
public class FriendsTest extends BaseWebTest {

	@BeforeEach
	void doLogin(@User(WITH_FRIENDS) UserJson user) {
		System.out.println(user.username());
		System.out.println(user.testData().password());
		mainPage
				.open();
		welcomePage
				.clickLoginButton()
				.loginAsUser(user.username(), user.testData().password());
	}

	@Test
	void friendShouldBeInFriendsTable(@User(WITH_FRIENDS) UserJson user) {
		mainPage
				.clickOnFriendsHeaderTab()
				.friendShouldBeInTable(user);
	}

	@Test
	void friendShouldBeInPeopleTable(@User(WITH_FRIENDS) UserJson user) {
		mainPage
				.clickOnPeopleHeaderTab()
				.friendShouldBeInTable(user);
	}
}
