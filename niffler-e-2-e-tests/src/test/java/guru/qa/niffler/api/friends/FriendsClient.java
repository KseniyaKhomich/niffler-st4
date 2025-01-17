package guru.qa.niffler.api.friends;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.model.FriendJson;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class FriendsClient extends RestClient {

	private final FriendsApi friendsApi;

	public FriendsClient() {
		super("http://127.0.0.1:8089");
		this.friendsApi = retrofit.create(FriendsApi.class);
	}

	@Step("Add friend with {friend.username} username for user with {username} username")
	public UserJson addFriend(String username, FriendJson friend) throws IOException {
		return friendsApi.addFriend(username, friend).execute().body();
	}

	@Step("Get friends for user with {username} username")
	public List<UserJson> getFriends(String username, boolean includePending) throws IOException {
		return friendsApi.getFriends(username, includePending).execute().body();
	}

	@Step("Get invitations for user with {username} username")
	public List<UserJson> getInvitations(String username) throws IOException {
		return friendsApi.getInvitations(username).execute().body();
	}

	@Step("Accept invitation from user with {invitation.username} to user with {username}")
	public List<UserJson> acceptInvitation(String username, FriendJson invitation) throws IOException {
		return friendsApi.acceptInvitation(username, invitation).execute().body();
	}

	@Step("Decline invitation from user with {invitation.username} to user with {username}")
	public List<UserJson> declineInvitation(String username, FriendJson invitation) throws IOException {
		return friendsApi.declineInvitation(username, invitation).execute().body();
	}

	@Step("Remove friend with {friendUsername} username from user with {username}")
	public List<UserJson> removeFriend(String username, String friendUsername) throws IOException {
		return friendsApi.removeFriend(username, friendUsername).execute().body();
	}

}
