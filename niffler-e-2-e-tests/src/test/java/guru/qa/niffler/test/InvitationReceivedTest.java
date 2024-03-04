package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SENT;
import static guru.qa.niffler.jupiter.annotation.User.UserType.RECEIVED;

@ExtendWith(UsersQueueExtension.class)
public class InvitationReceivedTest extends BaseWebTest {
	@Test
	void requestShouldBeDisplayedOnFriendsPage(@User(RECEIVED) UserJson receivedInvitationUser, @User(INVITATION_SENT) UserJson sentInvitationUser) {
		mainPage
				.open();
		welcomePage
				.clickLoginButton()
				.loginAsUser(receivedInvitationUser.username(), receivedInvitationUser.testData().password())
				.clickOnFriendsHeaderTab()
				.requestFromUserShouldBeDisplayed(sentInvitationUser);
	}

	@Test
	void requestShouldBeDisplayedOnPeoplePage(@User(RECEIVED) UserJson receivedInvitationUser, @User(INVITATION_SENT) UserJson sentInvitationUser) {
		mainPage
				.open();
		welcomePage
				.clickLoginButton()
				.loginAsUser(receivedInvitationUser.username(), receivedInvitationUser.testData().password())
				.clickOnPeopleHeaderTab()
				.requestFromUserShouldBeDisplayed(sentInvitationUser);
	}
}
