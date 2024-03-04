package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.category.CategoryClient;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Optional;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver {

	public static final ExtensionContext.Namespace NAMESPACE
			= ExtensionContext.Namespace.create(CategoryExtension.class);


	private final CategoryClient categoryClient = new CategoryClient();


	@Override
	public void beforeEach(ExtensionContext extensionContext) throws Exception {
		Optional<GenerateCategory> category = AnnotationSupport.findAnnotation(
				extensionContext.getRequiredTestMethod(),
				GenerateCategory.class
		);

		if (category.isPresent()) {
			GenerateCategory categoryData = category.get();

			CategoryJson categoryJson = new CategoryJson(
					null,
					categoryData.category(),
					categoryData.username());

			CategoryJson created = categoryClient.addCategory(categoryJson);
			extensionContext.getStore(NAMESPACE).put("category", created);
		}

	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return parameterContext.getParameter()
				.getType()
				.isAssignableFrom(CategoryJson.class);
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
		return extensionContext.getStore(NAMESPACE)
				.get("category", CategoryJson.class);
	}
}
