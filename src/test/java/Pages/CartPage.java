package Pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class CartPage {
    private Page page;
    private BrowserContext context;

    public final String addQuantityButton="//button[@data-testid='addQuantityButton']";
    public final String wishListButton="//div[@class='wishlist__icon']";
    public final String checkoutButton="//button[@data-testid='createCheckoutButton']";
    public final String quantityText="//input[@class='form__input quantity-input__input']";


    public CartPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public void addQuantity(){
        page.click(addQuantityButton);
    }

    public void addToWishList(){
        page.click(wishListButton);
    }

    public void checkOut(){
        page.click(checkoutButton);
    }


    public boolean isCheckOutButtonVisible() {
        page.waitForSelector(checkoutButton,new Page.WaitForSelectorOptions().setTimeout(10000));
        return page.locator(checkoutButton).isVisible();
    }

    public boolean isQuantityAdded() {
        page.waitForSelector(quantityText,new Page.WaitForSelectorOptions().setTimeout(10000));
        return page.locator(quantityText).inputValue().equals("2");
    }
}
