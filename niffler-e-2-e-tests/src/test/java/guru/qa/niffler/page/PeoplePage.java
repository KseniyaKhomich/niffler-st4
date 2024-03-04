package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage extends BasePage<PeoplePage> {

	private final SelenideElement peopleTable = $(".table tbody");

	@Step("Check if row with Pending invitation action is displayed")
	public PeoplePage pendingInvitationActionShouldBeDisplayed() {
		peopleTable
				.$$("tr")
				.find(text("Pending invitation"))
				.shouldBe(visible);

		return this;
	}

	@Step("Check if Request from User with {sentInvitationUser.username} username is displayed in table")
	public PeoplePage requestFromUserShouldBeDisplayed(UserJson sentInvitationUser) {
		SelenideElement rowElement = peopleTable.$$("tr")
				.find(text(sentInvitationUser.username()));

		rowElement
				.find(".button-icon_type_submit")
				.shouldBe(visible);

		rowElement
				.find(".button-icon_type_close")
				.shouldBe(visible);

		return this;
	}

	@Step("Check if Friend with {friendUser.username} username is displayed in table")
	public PeoplePage friendShouldBeInTable(UserJson friendUser) {
		peopleTable.$$("tr")
				.find(text(friendUser.username()))
				.$$("td")
				.find(text("You are friends"))
				.shouldBe(visible);
		return this;
	}
}
