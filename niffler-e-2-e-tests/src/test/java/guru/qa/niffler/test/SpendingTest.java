package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;


public class SpendingTest extends BaseWebTest {

	@GenerateCategory(
			username = "duck",
			category = "Обучение"
	)
	@GenerateSpend(
			username = "duck",
			description = "QA.GURU Advanced 4",
			amount = 72500.00,
			currency = CurrencyValues.RUB
	)
	// @DisabledByIssue("74")
	@Test
	void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
		mainPage
				.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(spend.username(), "12345")
				.deleteFirstSelectedSpending(spend.description())
				.checkEmptyListOfSpendings();
	}
}
