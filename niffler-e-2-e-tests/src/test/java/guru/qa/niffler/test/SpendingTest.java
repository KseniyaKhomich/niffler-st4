package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.api.category.CategoryApi;
import guru.qa.niffler.api.category.CategoryClient;
import guru.qa.niffler.api.spend.SpendClient;
import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.jupiter.annotation.DbUser;
import guru.qa.niffler.jupiter.annotation.DisabledByIssue;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.message.SuccessMsg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(UserRepositoryExtension.class)
public class SpendingTest extends BaseWebTest {

	@GenerateSpend(
			username = "duck",
			description = "QA.GURU Advanced 4",
			amount = 72500.00,
			category = "Обучение",
			currency = CurrencyValues.RUB
	)
	//@DisabledByIssue("74")
	@Test
	void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
		mainPage.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(spend.username(), "test")
				.deleteFirstSelectedSpending(spend.description())
				.checkEmptyListOfSpendings();
	}


	@DbUser(username = "randomstatics1")
	@GenerateCategory(
			username = "randomstatics1",
			category = "Обучение"
	)
	@Test
	void checkStatisticsVisibility(UserAuthEntity userAuthEntity) {
		mainPage.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(userAuthEntity.getUsername(), userAuthEntity.getPassword())
				.mainContentShouldBeDisplayed();
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

	@DbUser
	@Test
	void deleteSelectedRowsInSpendingTable(UserAuthEntity userAuthEntity) throws IOException, ParseException {
		CategoryJson categoryJson = new CategoryJson(
				null,
				"randomCategory",
				userAuthEntity.getUsername()
		);

		new CategoryClient().addCategory(categoryJson);


		SpendJson spend1 = new SpendJson(
				null,
				new SimpleDateFormat("dd MMM yy", Locale.ENGLISH).parse("05 Mar 24"),
				categoryJson.category(),
				CurrencyValues.USD,
				10.000,
				"randomSpend1",
				userAuthEntity.getUsername()
		);
		SpendJson spend2 = new SpendJson(
				null,
				new SimpleDateFormat("dd MMM yy", Locale.ENGLISH).parse("05 Mar 24"),
				categoryJson.category(),
				CurrencyValues.EUR,
				20.000,
				"randomSpend2",
				userAuthEntity.getUsername()
		);

		SpendClient spendClient = new SpendClient();

		spendClient.addSpend(spend1);
		spendClient.addSpend(spend2);


		mainPage.open();

		welcomePage
				.clickLoginButton()
				.loginAsUser(userAuthEntity.getUsername(), userAuthEntity.getPassword())
				.getSpendingTable()
				.checkSpends(spend1, spend2)
				.selectRowByIndex(1)
				.selectRowByText(spend1.description())
				.deleteSelectedSpendings()
				.checkEmptyListOfSpendings();
	}
}