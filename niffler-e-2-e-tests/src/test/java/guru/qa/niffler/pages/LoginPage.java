package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.pages.mainPage.MainPage;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

	private final SelenideElement usernameInput = $("input[name='username']");
	private final SelenideElement passwordInput = $("input[name='password']");
	private final SelenideElement submitButton = $("button[type='submit']");

	public MainPage loginAsUser(String username, String password) {
		usernameInput.setValue(username);
		passwordInput.setValue(password);
		submitButton.click();
		return new MainPage();
	}
}
