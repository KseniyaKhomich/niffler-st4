package guru.qa.niffler.api.category;

import guru.qa.niffler.api.RestClient;
import guru.qa.niffler.model.CategoryJson;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class CategoryClient extends RestClient {

	private final CategoryApi categoryApi;

	public CategoryClient() {
		super("http://127.0.0.1:8093");
		this.categoryApi = retrofit.create(CategoryApi.class);
	}

	@Step("Add category: {categoryJson.category}")
	public CategoryJson addCategory(CategoryJson categoryJson) throws IOException {
		return categoryApi.addCategory(categoryJson).execute().body();
	}

	@Step("Get all categories by username - {username}")
	public List<CategoryJson> getCategories(String username) throws IOException {
		return categoryApi.getCategories(username).execute().body();
	}
}
