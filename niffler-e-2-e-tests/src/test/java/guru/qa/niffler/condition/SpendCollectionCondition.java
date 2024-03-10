package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.ex.TextsMismatch;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SpendCollectionCondition {

  public static CollectionCondition spends(SpendJson... expectedSPends) {

    List<String> actualResults = new ArrayList<>();
    List<String> expectedResults = new ArrayList<>();
    return new CollectionCondition() {

      @Override
      public void fail(CollectionSource collection, CheckResult lastCheckResult, @Nullable Exception cause, long timeoutMs) {
        throw new TextsMismatch("Incorrect spend values",
                collection,
                List.of(((List) lastCheckResult.getActualValue()).get(1).toString()),
                List.of(((List) lastCheckResult.getActualValue()).get(0).toString()),
                explanation,
                timeoutMs,
                cause
        );

      }

      @Nonnull
      @Override
      public CheckResult check(Driver driver, List<WebElement> elements) {
        List<SpendJson> expectedSpends = Arrays.asList(expectedSPends);

        if (elements.size() != expectedSpends.size()) {
          return CheckResult.rejected("Incorrect table size", elements);
        }

        boolean checkPassed = false;

        for (int i = 0; i < elements.size(); i++) {

          List<WebElement> tds = elements.get(i).findElements(By.cssSelector("td"));

          try {
            SpendJson actualSpendJson = new SpendJson(
                    null,
                    new SimpleDateFormat("dd MMM yy", Locale.ENGLISH).parse(tds.get(1).getText()),
                    tds.get(4).getText(),
                    CurrencyValues.valueOf(tds.get(3).getText()),
                    Double.valueOf(tds.get(2).getText()),
                    tds.get(5).getText(),
                    null
            );

            checkPassed = compareRowValues(expectedSpends.get(i), actualSpendJson);

            if (!checkPassed) {
              actualResults.add(actualSpendJson.toString());
              expectedResults.add(expectedSpends.get(i).toString());
              break;
            }

          } catch (ParseException e) {
				throw new RuntimeException(e);
          }

        }

        if (checkPassed) {
          return CheckResult.accepted();
        } else {
          return CheckResult.rejected("Incorrect spends content", List.of(actualResults, expectedResults));
        }
      }

      @Override
      public boolean missingElementSatisfiesCondition() {
        return false;
      }

    };
  }

  private static boolean compareRowValues(SpendJson expectedSpendJson, SpendJson actualSpendJson) throws ParseException {
    return actualSpendJson.spendDate().equals(expectedSpendJson.spendDate()) &&
            actualSpendJson.amount().equals(expectedSpendJson.amount()) &&
            actualSpendJson.currency().equals(expectedSpendJson.currency()) &&
            actualSpendJson.category().equals(expectedSpendJson.category()) &&
            actualSpendJson.description().equals(expectedSpendJson.description());
  }
}
