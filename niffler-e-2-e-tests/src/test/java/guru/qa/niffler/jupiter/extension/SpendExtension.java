package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.spend.SpendClient;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;
import java.util.Optional;

public class SpendExtension implements BeforeEachCallback {

  public static final ExtensionContext.Namespace NAMESPACE
      = ExtensionContext.Namespace.create(SpendExtension.class);

  private final SpendClient spendClient = new SpendClient();

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    Optional<GenerateSpend> spend = AnnotationSupport.findAnnotation(
        extensionContext.getRequiredTestMethod(),
        GenerateSpend.class
    );

    Optional<GenerateCategory> category = AnnotationSupport.findAnnotation(
            extensionContext.getRequiredTestMethod(),
            GenerateCategory.class
    );

    if (spend.isPresent() && category.isPresent()) {
      CategoryJson categoryData = extensionContext.getStore(CategoryExtension.NAMESPACE)
              .get(extensionContext.getUniqueId(), CategoryJson.class);

      GenerateSpend spendData = spend.get();

      SpendJson spendJson = new SpendJson(
          null,
          new Date(),
          categoryData.category(),
          spendData.currency(),
          spendData.amount(),
          spendData.description(),
          spendData.username()
      );

      SpendJson created = spendClient.addSpend(spendJson);
      extensionContext.getStore(NAMESPACE)
          .put(extensionContext.getUniqueId(), created);
    }
  }
}
