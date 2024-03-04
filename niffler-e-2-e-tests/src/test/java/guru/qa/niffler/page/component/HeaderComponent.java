package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.PeoplePage;
import guru.qa.niffler.page.ProfilePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.condition.PhotoCondition.photoFromClasspath;

public class HeaderComponent {
	private final SelenideElement friendsTab = $("a[href='/friends']");
	private final SelenideElement peopleTab = $("a[href='/people']");
	private final SelenideElement profileTab = $("a[href='/profile']");
	private final SelenideElement avatar = $(".header__avatar");


	public FriendsPage clickOnFriendsTab() {
		friendsTab.click();
		return new FriendsPage();
	}

	public PeoplePage clickOnPeopleTab() {
		peopleTab.click();
		return new PeoplePage();
	}

	public ProfilePage clickOnProfileTab() {
		profileTab.click();
		return new ProfilePage();
	}

	@Step("check avatar")
	public HeaderComponent checkAvatar(String imageName) {
		avatar.shouldHave(photoFromClasspath(imageName));
		return this;
	}
}
