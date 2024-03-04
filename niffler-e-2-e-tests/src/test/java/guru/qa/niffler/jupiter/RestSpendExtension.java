package guru.qa.niffler.jupiter;

import guru.qa.niffler.api.CategoryApi;
import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.ExtensionContext;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class RestSpendExtension extends SpendExtension {
	private static final OkHttpClient httpClient = new OkHttpClient.Builder().build();
	private static final Retrofit retrofit = new Retrofit.Builder()
			.client(httpClient)
			.baseUrl("http://127.0.0.1:8093")
			.addConverterFactory(JacksonConverterFactory.create())
			.build();

	private final SpendApi spendApi = retrofit.create(SpendApi.class);
	private final CategoryApi categoryApi = retrofit.create(CategoryApi.class);

	@Override
	SpendJson create(SpendJson spend) throws IOException {
		CategoryJson categoryJson = new CategoryJson(
				null,
				spend.category(),
				spend.username());

		categoryApi.addCategory(categoryJson).execute();
		return spendApi.addSpend(spend).execute().body();
	}
}
