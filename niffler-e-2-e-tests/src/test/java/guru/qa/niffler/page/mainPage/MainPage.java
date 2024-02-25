package guru.qa.niffler.page.mainPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.BasePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.PeoplePage;
import guru.qa.niffler.page.components.HeaderComponent;
import guru.qa.niffler.page.mainPage.component.SpendingsComponent;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage<MainPage> {

	private static final String MAIN_URL = "http://127.0.0.1:3000/main";
	private final SpendingsComponent spendingsComponent = new SpendingsComponent();
	private final HeaderComponent headerComponent = new HeaderComponent();

	private final SelenideElement mainContentComponent = $(".main-content");

	@Step("Navigate to Main page")
	public MainPage open() {
		Selenide.open(MAIN_URL);
		return this;
	}

	@Step("Delete spending by description - {spendDescription}")
	public MainPage deleteFirstSelectedSpending(String spendDescription) {
		spendingsComponent.selectFirstSpendingByDescription(spendDescription).deleteSelectedSpendings();
		return this;
	}

	@Step("Check empty list of spensings")
	public MainPage checkEmptyListOfSpendings() {
		spendingsComponent.checkEmptyListOfSpendings();
		return this;
	}

	@Step("Navigate to Friends Page")
	public FriendsPage clickOnFriendsHeaderTab() {
		return headerComponent.clickOnFriendsTab();
	}

	@Step("Navigate to People page")
	public PeoplePage clickOnPeopleHeaderTab() {
		return headerComponent.clickOnPeopleTab();
	}

	@Step("Check visibility of main content")
	public MainPage mainContentShouldBeDisplayed() {
		mainContentComponent.shouldBe(Condition.visible);
		return this;
	}
}
