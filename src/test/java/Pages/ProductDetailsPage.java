package Pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ProductDetailsPage {
    private Page page;
    private BrowserContext context;

    public ProductDetailsPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }


    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public final String addToCartButton="//button[@data-testid='addToBagButton']";
    public final String cartIcon="//div[@class='cart tooltip__trigger menus__item']";
    public final String averageRatings="//div[@class='rating-score__average']/div";
    public final String totalRating="//span[@class='rating-score__detail-count']";
    public final String reviewSection="//div[@class='product-reviews']";
    public final String footer="//div[@class='footer__info']";

    public void addToCart(){
        page.click(addToCartButton);
    }


    public void moveToCart() {
        page.click(cartIcon);
    }

    public void findRatings(){

        while (!page.locator(reviewSection).isVisible() && !page.locator(footer).isVisible()){
            page.mouse().wheel(0,100);
        }


        try {
            System.out.println(page.locator(averageRatings).innerText());
            System.out.println(page.locator(totalRating).innerText());
        } catch (Exception e) {
            System.out.println("There are no reviews");
        }

    }

    public boolean isAddToCartVisible() {
        return page.isVisible(addToCartButton);
    }


}
