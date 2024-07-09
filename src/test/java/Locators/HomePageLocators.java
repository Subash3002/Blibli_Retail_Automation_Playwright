package Locators;

public class HomePageLocators {
    public final String searchProductInput="//input[@placeholder='PRODUCTNAME']";
    public final String searchButton="//button[@title='search']";
    public final String locationFilter="//div[@class='multi-select-filter location-filter']//div[@class='facet-text' and text()=' LOCATION ']";
    public final String displayedFilters="//div[@class='selected-list']//span/div[text()=' FILTER ']";
    public final String colorFilter="//div[@class='color-filter']//span[text()=' FILTER ']";
    public final String productStockFilter="//div[@class='multi-select-filter oos-filter']//div[text()=' FILTER ']";
    public final String productStockLabel="//label[@class='multi-select-filter__header multi-select-filter__name' and text()=' Product Stock ']";
    public final String sortFilter="//div[@class='blu-list list b-overflow-y hidden adddress-prompt-ul']//label[text()=' FILTER ']";
    public final String sortTitle="//div[@class='b-overflow-x has-value']/span[@slot='trigger']";
    public final String productPrices="//div[@class='product columns product__container__*']//div[@class='blu-product__price-after']";
//    public final String secondRow="//div[@class=\"product columns product__container__end\"]//div[@class=\"blu-product__price-after\"]"
    public final String profileButton="//div[@class='account tooltip__trigger hover-gray']";
    public final String daftarPesananButton="//span[text()='Daftar pesanan']";
}
