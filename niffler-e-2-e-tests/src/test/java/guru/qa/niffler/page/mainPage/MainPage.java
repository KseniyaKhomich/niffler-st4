package guru.qa.niffler.page.mainPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.BasePage;
import guru.qa.niffler.page.mainPage.component.AddSpendingComponent;
import guru.qa.niffler.page.mainPage.component.SpendingTable;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage<MainPage> {

	private static final String MAIN_URL = "http://127.0.0.1:3000/main";
	private final SpendingTable spendingTable = new SpendingTable();
	private final AddSpendingComponent addSpendingComponent = new AddSpendingComponent();

	private final SelenideElement mainContentComponent = $(".main-content");

	@Step("Navigate to Main page")
	public MainPage open() {
		Selenide.open(MAIN_URL);
		return this;
	}

	@Step("Add new spending with {name}")
	public MainPage addNewSpending(String categoryName, Double amount) {
		addSpendingComponent.addSpending(categoryName, amount);
		return this;
	}

	@Step("Delete spending by description - {spendDescription}")
	public MainPage deleteFirstSelectedSpending(String spendDescription) {
		spendingTable.selectFirstSpendingByDescription(spendDescription).deleteSelectedSpendings();
		return this;
	}

	@Step("Check empty list of spensings")
	public MainPage checkEmptyListOfSpendings() {
		spendingTable.checkEmptyListOfSpendings();
		return this;
	}

	@Step("Check spending visibility in History of Spending  by category name - {}")
	public MainPage checkSpendingVisibilityInHistoryTable(String categoryName) {
		spendingTable.checkVisibilityOfSpendingByCategoryName(categoryName);
		return this;
	}

	@Step("Check visibility of main content")
	public MainPage mainContentShouldBeDisplayed() {
		mainContentComponent.shouldBe(Condition.visible);
		return this;
	}

	public SpendingTable getSpendingTable() {
		spendingTable.getSelf().scrollIntoView(true);
		return spendingTable;
	}
}
