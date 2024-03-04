package guru.qa.niffler.page.mainPage.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AddSpendingComponent {

	private final SelenideElement categoryInput = $(".add-spending__form div[class*='IndicatorsContainer']");
	private final ElementsCollection categoryListboxOptions = $$("div[id*='option']");
	private final SelenideElement amountInput = $("input[name='amount']");
	private final SelenideElement addButton = $(".add-spending__form .button");

	public AddSpendingComponent addSpending(String categoryName, Double amount) {
		categoryInput.click();
		categoryListboxOptions.filter(Condition.text(categoryName))
				.first()
				.click();

		amountInput.setValue(String.valueOf(amount));
		addButton.click();

		return this;
	}
}
