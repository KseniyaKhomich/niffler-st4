package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.pages.FriendsPage;
import guru.qa.niffler.pages.PeoplePage;
import guru.qa.niffler.pages.WelcomePage;
import guru.qa.niffler.pages.mainPage.MainPage;
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
