package guru.qa.niffler.page.components;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.PeoplePage;

import static com.codeborne.selenide.Selenide.$;

public class HeaderComponent {
	private final SelenideElement friendsTab = $("a[href='/friends']");
	private final SelenideElement peopleTab = $("a[href='/people']");

	public FriendsPage clickOnFriendsTab() {
		friendsTab.click();
		return new FriendsPage();
	}

	public PeoplePage clickOnPeopleTab() {
		peopleTab.click();
		return new PeoplePage();
	}
}
