package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.RestSpendExtension;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RestSpendExtension.class)
public class SpendingTest extends BaseWebTest {

	@GenerateSpend(
			username = "duck",
			description = "QA.GURU Advanced 4",
			category = "Test",
			amount = 72500.00,
			currency = CurrencyValues.RUB)
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
