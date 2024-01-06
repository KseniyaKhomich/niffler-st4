package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.DisabledByIssue;
import guru.qa.niffler.jupiter.GenerateCategory;
import guru.qa.niffler.jupiter.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.WelcomePage;
import guru.qa.niffler.pages.mainPage.MainPage;
import org.junit.jupiter.api.Test;


public class SpendingTest extends BaseWebTest {

	static {
		Configuration.browserSize = "1980x1024";
	}

	@GenerateCategory(
			username = "duck",
			category = "Обучение"
	)
	@GenerateSpend(
			username = "duck",
			description = "QA.GURU Advanced 4",
			amount = 72500.00,
			category = "Обучение",
			currency = CurrencyValues.RUB
	)
	// @DisabledByIssue("74")
	@Test
	void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
		new MainPage()
				.open();

		new WelcomePage()
				.clickLoginButton()
				.loginAsUser(spend.username(), "12345")
				.deleteFirstSelectedSpending(spend.description())
				.checkEmptyListOfSpendings();
	}
}
