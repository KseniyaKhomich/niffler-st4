package guru.qa.niffler.test;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.message.SuccessMsg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(UserRepositoryExtension.class)
public class SpendingTest extends BaseWebTest {

	@GenerateSpend(
			username = "random54321",
			description = "QA.GURU Advanced 4",
			category = "Test1",
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
				.loginAsUser(spend.username(), "test")
				.deleteFirstSelectedSpending(spend.description())
				.checkEmptyListOfSpendings();
	}

	@DbUser(username = "random7")
	@GenerateCategory(
			username = "random7",
			category = "Обучение"
	)
	@Test
	void addNewSpending(UserAuthEntity userAuthEntity, CategoryJson categoryJson) {
		mainPage
				.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(userAuthEntity.getUsername(), userAuthEntity.getPassword())
				.addNewSpending(categoryJson.category(), 135.00)
				.checkSpendingVisibilityInHistoryTable(categoryJson.category())
				.checkMessage(SuccessMsg.SPENDING_MSG);

		mainPage.checkSpendingVisibilityInHistoryTable(categoryJson.category());
	}


	@DbUser(username = "randomstatics1")
	@GenerateCategory(
			username = "randomstatics1",
			category = "Обучение"
	)
	@Test
	void checkStatisticsVisibility(UserAuthEntity userAuthEntity) {
		mainPage
				.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(userAuthEntity.getUsername(), userAuthEntity.getPassword())
				.mainContentShouldBeDisplayed();

	}
}
