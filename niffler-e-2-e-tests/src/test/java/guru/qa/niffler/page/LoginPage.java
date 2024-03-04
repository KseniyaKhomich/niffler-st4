package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.mainPage.MainPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage> {

	private final SelenideElement usernameInput = $("input[name='username']");
	private final SelenideElement passwordInput = $("input[name='password']");
	private final SelenideElement submitButton = $("button[type='submit']");
	private final SelenideElement signUpButton = $("a[href*='register']");

	@Step("Log in to as User with {username} username")
	public MainPage loginAsUser(String username, String password) {
		usernameInput.setValue(username);
		passwordInput.setValue(password);
		submitButton.click();
		return new MainPage();
	}

	@Step("Click on Sign Up button")
	public RegisterPage clickSignUpButton() {
		signUpButton.click();
		return new RegisterPage();
	}
}
