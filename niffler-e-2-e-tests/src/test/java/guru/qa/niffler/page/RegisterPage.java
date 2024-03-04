package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage extends BasePage<RegisterPage> {
	private final SelenideElement usernameInput = $("#username");
	private final SelenideElement passwordInput = $("#password");
	private final SelenideElement submitPasswordInput = $("#passwordSubmit");
	private final SelenideElement signUpButton = $(".form__submit");
	private final SelenideElement congratulationForm = $(withText("Congratulations!"));
	private final SelenideElement existingUserErrorMessage = $x("//label[contains(., 'Username')]//span[@class='form__error']");
	private final SelenideElement wrongPasswordLengthMessage = $x("//label[contains(., 'Password')]//span[@class='form__error']");

	@Step("Register new user with {username} username")
	public RegisterPage registerUser(String username, String password) {
		usernameInput.setValue(username);
		passwordInput.setValue(password);
		submitPasswordInput.setValue(password);
		signUpButton.click();
		return this;
	}

	@Step("Check if Congratulation form is displayed")
	public void congratulationFormShouldBedDisplayed() {
		congratulationForm.shouldBe(visible);
	}

	@Step("Check if Error Message about Already Existing User is displayed")
	public void checkErrorMessageExistingUserShouldBeDisplayed() {
		existingUserErrorMessage.shouldBe(visible);
	}

	@Step("Check if Error Message about Password Length is displayed")
	public void checkErrorMessagePasswordLengthShouldBeDisplayed() {
		wrongPasswordLengthMessage.shouldBe(visible);
	}
}
