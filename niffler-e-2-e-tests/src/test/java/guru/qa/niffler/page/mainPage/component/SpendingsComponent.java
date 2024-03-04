package guru.qa.niffler.page.mainPage.component;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class SpendingsComponent {

	private final SelenideElement spendingsTable = $(".spendings-table tbody");
	private final SelenideElement deleteSelectedButton = $(byText("Delete selected"));

	public SpendingsComponent selectFirstSpendingByDescription(String spendDescription) {
		spendingsTable
				.$$("tr")
				.find(text(spendDescription))
				.$("td")
				.scrollTo()
				.click();
		return this;
	}

	public SpendingsComponent deleteSelectedSpendings() {
		deleteSelectedButton.click();
		return this;
	}

	public SpendingsComponent checkEmptyListOfSpendings() {
		spendingsTable
				.$$("tr")
				.shouldHave(size(0));
		return this;
	}

	public SpendingsComponent checkVisibilityOfSpendingByCategoryName(String categoryName) {
		spendingsTable
				.$$("tr")
				.find(text(categoryName))
				.shouldBe(visible);
		return this;
	}
}
