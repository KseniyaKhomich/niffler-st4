package guru.qa.niffler.api.currency;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.model.CurrencyCalculateJson;
import guru.qa.niffler.model.CurrencyJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class CurrencyClient extends RestClient {
	private final CurrencyApi currencyApi;

	public CurrencyClient() {
		super("http://127.0.0.1:8091");
		this.currencyApi = retrofit.create(CurrencyApi.class);
	}

	@Step("Gel all currencies")
	public List<CurrencyJson> getAllCurrencies() throws IOException {
		return currencyApi.getAllCurrencies().execute().body();
	}

	@Step("Calculate currency")
	public CurrencyCalculateJson calculate(CurrencyCalculateJson currencyCalculate) throws IOException {
		return currencyApi.calculate(currencyCalculate).execute().body();
	}
}
