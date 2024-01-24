package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {

	private final SelenideElement noFriendsMessageElement = $(byText("There are no friends yet!"));
	private final SelenideElement friendsTable = $(".table");

	public FriendsPage noFriendsMessageShouldBeDisplayed() {
		noFriendsMessageElement.shouldBe(visible);
		return this;
	}

	public FriendsPage requestFromUserShouldBeDisplayed(UserJson sentInvitationUser) {
		SelenideElement rowElement = friendsTable
				.$$("tr")
				.find(text(sentInvitationUser.username()));

		rowElement
				.find(".button-icon_type_submit")
				.shouldBe(visible);

		rowElement
				.find(".button-icon_type_close")
				.shouldBe(visible);

		return this;
	}

	public FriendsPage friendShouldBeInTable(UserJson friendUser) {
		friendsTable
				.$$("tr")
				.find(text(friendUser.username()))
				.$$("td")
				.find(text("You are friends"))
				.shouldBe(visible);
		return this;
	}

}
