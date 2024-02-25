package guru.qa.niffler.api.user;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class UserClient extends RestClient {

	private final UserApi userApi;

	public UserClient() {
		super("http://127.0.0.1:8089");
		this.userApi = retrofit.create(UserApi.class);
	}

	@Step("Get all users")
	public List<UserJson> allUsers(String username) throws IOException {
		return userApi.allUsers(username).execute().body();
	}

	@Step("Get current user by {username}")
	public UserJson currentUser(String username) throws IOException {
		return userApi.currentUser(username).execute().body();
	}

	@Step("Update user with {user.username} info")
	public UserJson updateUserInfo(UserJson user) throws IOException {
		return userApi.updateUserInfo(user).execute().body();
	}
}
