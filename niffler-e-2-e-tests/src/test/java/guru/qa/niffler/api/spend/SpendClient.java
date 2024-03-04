package guru.qa.niffler.api.spend;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.StatisticJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.text.spi.DateFormatProvider;
import java.util.Date;
import java.util.List;

public class SpendClient extends RestClient {

	private final SpendApi spendApi;

	public SpendClient() {
		super("http://127.0.0.1:8093");
		this.spendApi = retrofit.create(SpendApi.class);
	}

	@Step("Add spend : {spendJson.description}")
	public SpendJson addSpend(SpendJson spendJson) throws IOException {
		return spendApi.addSpend(spendJson).execute().body();
	}

	@Step("Edit spend : {spendJson.description}")
	public SpendJson editSpend(SpendJson spendJson) throws IOException {
		return spendApi.editSpend(spendJson).execute().body();
	}

	@Step("Get all spends by username: {username}")
	public List<SpendJson> getSpends(String username,
									 CurrencyValues filterCurrency,
									 Date from,
									 Date to) throws IOException {
		return spendApi.getSpends(username, filterCurrency, from, to).execute().body();
	}
	
	@Step("Delete spends by username: {username}")
	public void deleteSpends(String username, String... ids) throws IOException {
		spendApi.deleteSpends(username, ids).execute();
	}

	@Step("Get statistic for {username} for {filterCurrency.name} currency")
	public List<StatisticJson> getStatistic(String username,
											CurrencyValues userCurrency,
											CurrencyValues filterCurrency,
											Date from,
											Date to) throws IOException {
		return spendApi.getStatistic(username, userCurrency, filterCurrency, from, to).execute().body();
	}
}
