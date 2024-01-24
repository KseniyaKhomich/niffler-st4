package guru.qa.niffler.pages.mainPage;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.pages.FriendsPage;
import guru.qa.niffler.pages.PeoplePage;
import guru.qa.niffler.pages.components.HeaderComponent;
import guru.qa.niffler.pages.mainPage.components.SpendingsComponent;

public class MainPage {

	private static final String MAIN_URL = "http://127.0.0.1:3000/main";
	private final SpendingsComponent spendingsComponent = new SpendingsComponent();
	private final HeaderComponent headerComponent = new HeaderComponent();

	public MainPage open() {
		Selenide.open(MAIN_URL);
		return this;
	}

	public MainPage deleteFirstSelectedSpending(String spendDescription) {
		spendingsComponent.selectFirstSpendingByDescription(spendDescription).deleteSelectedSpendings();
		return this;
	}

	public MainPage checkEmptyListOfSpendings() {
		spendingsComponent.checkEmptyListOfSpendings();
		return this;
	}

	public FriendsPage clickOnFriendsHeaderTab() {
		return headerComponent.clickOnFriendsTab();
	}

	public PeoplePage clickOnPeopleHeaderTab() {
		return headerComponent.clickOnPeopleTab();
	}
}
