package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.CurrencyValues;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage extends BasePage<ProfilePage>{

	private final SelenideElement nameInput = $("input[name='firstname']");
	private final SelenideElement surnameInput = $("input[name='surname']");
	private final SelenideElement currencyInput = $("input[type='text']");
	private final ElementsCollection currencyValueOptions = $$("div[id*='option']");
	private final SelenideElement submitButton = $("button[type='submit']");
	private final SelenideElement categoryNameInput = $("input[name='category']");
	private final SelenideElement createButton = $(byText("Create"));
	private final ElementsCollection categoryItems = $$(".categories__item");

	@Step("Add category with {categoryName} name")
	public ProfilePage addCategory(String categoryName) {
		categoryNameInput.setValue(categoryName);
		createButton.click();
		return this;
	}

	@Step("Update User info")
	public ProfilePage updateUserInfo(String name, String surname, CurrencyValues currencyValue) {
		nameInput.setValue(name);
		surnameInput.setValue(surname);

		currencyInput.click();
		currencyValueOptions.findBy(Condition.text(currencyValue.name())).click();
		submitButton.click();

		return this;
	}

	@Step("Get spending categories")
	public List<String> getCategories() {
		System.out.println(categoryItems.size());
		return categoryItems.stream()
				.map(SelenideElement::getText)
				.collect(Collectors.toList());
	}
}
