package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.DatabaseSpendExtension;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DatabaseSpendExtension.class)
public class SpendingDatabaseTest extends BaseWebTest{

	@GenerateSpend(
			username = "duck",
			description = "QA.GURU Advanced 4",
			category = "Test2",
			amount = 72500.00,
			currency = CurrencyValues.RUB)
	@Test
	void spendingShouldBeDeletedUsingDB(SpendJson spend) {
		mainPage
				.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(spend.username(), "12345")
				.deleteFirstSelectedSpending(spend.description())
				.checkEmptyListOfSpendings();
	}
}
