package Pages;

import Locators.HomePageLocators;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import java.util.List;

public class HomePage {
    HomePageLocators homePageLocators=new HomePageLocators();


    public Page getPage() {
        return page;
    }


    public BrowserContext getContext() {
        return context;
    }


    private Page page;
    private BrowserContext context;

    public HomePage(Page page, BrowserContext browser) {
        this.page = page;
        this.context = browser;
    }

    @Step("To enter a product name and click search buttom")
    public void searchProduct(String productName) {
        String productInputSelector=homePageLocators.searchProductInput.replace("PRODUCTNAME",productName);
        page.waitForSelector(productInputSelector,new Page.WaitForSelectorOptions().setTimeout(10000));
        page.fill(productInputSelector,productName);
        page.click(homePageLocators.searchButton);
    }

    @Step("Applying the location filter")
    public void applyLocationFilter(String locationFilterName){
        page.click(homePageLocators.locationFilter.replace("LOCATION",locationFilterName));
    }

    @Step("Checking whether the entered product is correctly displayed in the input box")
    public boolean validateProduct(String expectedProductName) {
        String productInputSelector=homePageLocators.searchProductInput.replace("PRODUCTNAME","Cari brand, produk, atau seller");
        return expectedProductName.contains(page.locator(productInputSelector).innerText());
    }

    @Step("Checking whether the filter is applied on the product chose")
    public boolean isFilterApplied(String filterName) {
        String filter=homePageLocators.displayedFilters.replace("FILTER",filterName);
        page.waitForSelector(filter,new Page.WaitForSelectorOptions().setTimeout(10000));
        return page.isVisible(filter);
    }

    @Step("Applying the necessary color filter ony the product")
    public void applyColorFilter(String filterName) {
        while (!page.locator(homePageLocators.colorFilter.replace("FILTER",filterName)).isVisible()){
            page.mouse().wheel(0,100);
        }
        page.locator(homePageLocators.colorFilter.replace("FILTER",filterName)).scrollIntoViewIfNeeded();
        page.click(homePageLocators.colorFilter.replace("FILTER",filterName));
    }

    @Step("Applying the product stock filter on the product")
    public void applyProductStockFilter(String filterName) {
        while (!page.locator(homePageLocators.productStockLabel).isVisible()){
            page.mouse().wheel(0,100);
        }

        page.click(homePageLocators.productStockLabel);
        page.click(homePageLocators.productStockFilter.replace("FILTER",filterName));
    }

    @Step("Applying necessary sorting on the product")
    public void applySorting(String sortingName) {
        page.click("//div[@class='blu-dropdown__trigger']/div");
        page.click(homePageLocators.sortFilter.replace("FILTER",sortingName));
    }

    @Step("Check whether the sorting filter is applied on the product")
    public boolean isSortingApplied(String appliedSort) {
        return appliedSort.equals(page.locator(homePageLocators.sortTitle).innerText());
    }

    @Step("To find the product which has the maximum price")
    public void openMaximumPriceProduct() {
        ElementHandle maximumProduct=null;
        int maxPrice=0;
        List<ElementHandle> productsPrices=page.querySelectorAll(homePageLocators.productPrices.replace("*","side"));
        productsPrices.addAll(page.querySelectorAll(homePageLocators.productPrices.replace("*","end")));

        for(ElementHandle price:productsPrices){
            int currentProductPrice=findPrice(price.innerText().substring(2));
            if(currentProductPrice>maxPrice){
                maxPrice=currentProductPrice;
                maximumProduct=price;
            }
        }

        maximumProduct.click();

    }




    @Step("To convert the product price to integer format")
    public int findPrice(String priceInString) {
        StringBuilder finalPrice= new StringBuilder();

        String[] priceAfterSplit=priceInString.split("\\.");
        for(String price:priceAfterSplit) finalPrice.append(price);

        return Integer.parseInt(finalPrice.toString());

    }


    public void hoverProfile(){
        page.hover(homePageLocators.profileButton);
    }

    public void clickOrderDetails(){
        page.click(homePageLocators.daftarPesananButton);
    }
}
