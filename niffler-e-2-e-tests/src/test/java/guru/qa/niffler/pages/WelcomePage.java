package guru.qa.niffler.pages;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {

	private final SelenideElement loginButton = $("a[href*='redirect']");

	public LoginPage clickLoginButton() {
		loginButton.click();
		return new LoginPage();
	}
}
