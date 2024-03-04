package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.mainPage.MainPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage extends BasePage<RegisterPage> {
	private final SelenideElement usernameInput = $("#username");
	private final SelenideElement passwordInput = $("#password");
	private final SelenideElement submitPasswordInput = $("#passwordSubmit");
	private final SelenideElement signUpButton = $(".form__submit");

	@Step("Register new user with {username} username")
	public MainPage registerUser(String username, String password) {
		usernameInput.setValue(username);
		passwordInput.setValue(password);
		submitPasswordInput.setValue(password);
		signUpButton.click();
		return new MainPage();
	}
}
