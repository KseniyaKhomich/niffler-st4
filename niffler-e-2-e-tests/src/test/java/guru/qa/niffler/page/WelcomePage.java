package guru.qa.niffler.page;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage extends BasePage<WelcomePage> {

	private final SelenideElement loginButton = $("a[href*='redirect']");
	private final SelenideElement registerButton = $("a[href*='register']");

	@Step("Click on Login button")
	public LoginPage clickLoginButton() {
		loginButton.click();
		return new LoginPage();
	}

	@Step("Click on Register button")
	public RegisterPage clickRegisterButton() {
		registerButton.click();
		return new RegisterPage();
	}
}
