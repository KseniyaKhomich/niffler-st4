package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import com.codeborne.selenide.Configuration;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.PeoplePage;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.WelcomePage;
import guru.qa.niffler.page.mainPage.MainPage;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {

  protected static final Config CFG = Config.getInstance();

	protected MainPage mainPage = new MainPage();
	protected WelcomePage welcomePage = new WelcomePage();
	protected FriendsPage friendsPage = new FriendsPage();
	protected PeoplePage peoplePage = new PeoplePage();

	protected ProfilePage profilePage = new ProfilePage();

	static {
		Configuration.browserSize = "1980x1024";
		Configuration.browserVersion = "120.0";
	}
}
