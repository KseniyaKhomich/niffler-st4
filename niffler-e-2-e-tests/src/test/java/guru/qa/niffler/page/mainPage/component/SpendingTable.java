package guru.qa.niffler.page.mainPage.component;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.component.BaseComponent;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.condition.SpendCollectionCondition.spends;

public class SpendingTable extends BaseComponent<SpendingTable> {

	private final SelenideElement deleteSelectedButton = $(byText("Delete selected"));

	public SpendingTable() {
		super($(".spendings-table tbody"));
	}

	public SpendingTable checkSpends(SpendJson... expectedSpends) {
		getSelf()
				.$$("tr")
				.should(spends(expectedSpends));
		return this;
	}

	public SpendingTable selectFirstSpendingByDescription(String spendDescription) {
		getSelf()
				.$$("tr")
				.find(text(spendDescription))
				.$("td")
				.scrollTo()
				.click();
		return this;
	}

	public SpendingTable deleteSelectedSpendings() {
		deleteSelectedButton.click();
		return this;
	}

	public SpendingTable checkEmptyListOfSpendings() {
		getSelf()
				.$$("tr")
				.shouldHave(size(0));
		return this;
	}

	public SpendingTable checkVisibilityOfSpendingByCategoryName(String categoryName) {
		getSelf()
				.$$("tr")
				.find(text(categoryName))
				.shouldBe(visible);
		return this;
	}

	public SpendingTable selectRowByText(String searchValue) {
		getSelf()
				.$$("tr")
				.find(text(searchValue))
				.find("input[type='checkbox']")
				.scrollTo()
				.click();
		return this;
	}

	public SpendingTable selectRowByIndex(int index) {
		getSelf()
				.$$("tr")
				.get(index)
				.find("input[type='checkbox']")
				.scrollTo()
				.click();
		return this;
	}
}
