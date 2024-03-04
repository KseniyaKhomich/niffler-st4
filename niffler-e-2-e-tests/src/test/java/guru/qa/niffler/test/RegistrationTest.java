package guru.qa.niffler.test;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UserRepositoryExtension.class)
public class RegistrationTest extends BaseWebTest {

	private final Faker faker = new Faker();

	@Test
	void successfulRegistrationWithNewUser() {
		String username = faker.name().username() + faker.number().randomDigit();
		String password = faker.internet().password(3,12);

		mainPage.open();
		welcomePage.clickRegisterButton()
				.registerUser(username, password)
				.congratulationFormShouldBedDisplayed();
	}

	@DbUser
	@Test
	void failedRegistrationWithExistingUser(UserAuthEntity userAuth) {
		mainPage.open();
		welcomePage.clickRegisterButton()
				.registerUser(userAuth.getUsername(), userAuth.getPassword())
				.checkErrorMessageExistingUserShouldBeDisplayed();
	}

	@Test
	void failedRegistrationWithWrongPasswordLength() {
		String username = faker.name().username() + faker.number().randomDigit();
		String password = faker.internet().password(13,20);

		mainPage.open();
		welcomePage.clickRegisterButton()
				.registerUser(username, password)
				.checkErrorMessagePasswordLengthShouldBeDisplayed();
	}
}
