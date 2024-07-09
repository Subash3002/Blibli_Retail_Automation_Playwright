package Pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ThankYouPage {
    private Page page;
    private BrowserContext context;
    public final String homePageButton="//div[@class='header__content__title']";

    public ThankYouPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public void navigateToHomePage(){
        page.click(homePageButton);
    }
}
