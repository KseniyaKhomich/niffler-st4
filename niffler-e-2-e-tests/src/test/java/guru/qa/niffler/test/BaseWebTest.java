package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.extension.BrowserExtension;
import com.codeborne.selenide.Configuration;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.PeoplePage;
import guru.qa.niffler.page.WelcomePage;
import guru.qa.niffler.page.mainPage.MainPage;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {
	MainPage mainPage = new MainPage();
	WelcomePage welcomePage = new WelcomePage();
	FriendsPage friendsPage = new FriendsPage();
	PeoplePage peoplePage = new PeoplePage();

	static {
		Configuration.browserSize = "1980x1024";
	}
}
