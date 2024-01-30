package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage {

	private final SelenideElement peopleTable = $(".table tbody");

	public PeoplePage pendingInvitationActionShouldBeDisplayed() {
		peopleTable
				.$$("tr")
				.find(text("Pending invitation"))
				.shouldBe(visible);

		return this;
	}

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

	public PeoplePage friendShouldBeInTable(UserJson friendUser) {
		peopleTable.$$("tr")
				.find(text(friendUser.username()))
				.$$("td")
				.find(text("You are friends"))
				.shouldBe(visible);
		return this;
	}
}
