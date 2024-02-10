package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SENT;

@ExtendWith(UsersQueueExtension.class)
public class InvitationSentTest extends BaseWebTest {

	@BeforeEach
	void doLogin(@User(INVITATION_SENT) UserJson user) {
		mainPage
				.open();
		welcomePage
				.clickLoginButton()
				.loginAsUser(user.username(), user.testData().password());
	}

	@Test
	void pendingInvitationActionShouldBeDisplayed() {
		mainPage
				.clickOnPeopleHeaderTab()
				.pendingInvitationActionShouldBeDisplayed();
	}


	@Test
	void noFriendsShouldBeDisplayed() {
		mainPage
				.clickOnFriendsHeaderTab()
				.noFriendsMessageShouldBeDisplayed();
	}
}
