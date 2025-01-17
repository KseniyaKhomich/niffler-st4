package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.component.HeaderComponent;
import guru.qa.niffler.page.mainPage.MainPage;
import guru.qa.niffler.page.message.Msg;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage<T extends BasePage> {

  protected static final Config CFG = Config.getInstance();

  protected final SelenideElement toaster = $(".Toastify__toast-body");

  private final HeaderComponent headerComponent = new HeaderComponent();

  @SuppressWarnings("unchecked")
  @Step("Check message")
  public T checkMessage(Msg msg) {
    toaster.shouldHave(text(msg.getMessage()));
    return (T) this;
  }

  @Step("Navigate to Friends Page")
  public ProfilePage clickOnProfileHeaderTab() {
    return headerComponent.clickOnProfileTab();
  }

  @Step("Navigate to Friends Page")
  public FriendsPage clickOnFriendsHeaderTab() {
    return headerComponent.clickOnFriendsTab();
  }

  @Step("Navigate to People page")
  public PeoplePage clickOnPeopleHeaderTab() {
    return headerComponent.clickOnPeopleTab();
  }

  @Step("check avatar")
  public T checkAvatar(String imageName) {
    headerComponent.checkAvatar(imageName);
    return (T) this;
  }
}
