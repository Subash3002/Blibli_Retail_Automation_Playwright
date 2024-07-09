package Pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class OrderDetailsPage {
    private Page page;
    private BrowserContext context;
    public final String orderProduct="//div[@class='order-item']";
    public final String cancelOrderButton="//div[text()='  Batalkan pesanan  ']";
    public final String confirmCancelButton="//div[contains(text(),'Ya')]";

    public OrderDetailsPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public void clickOrderedProduct(){
        page.click(orderProduct);
    }

    public void cancelOrder(){
        page.click(cancelOrderButton);
    }

    public void confirmCancelOrder(){
        page.click(confirmCancelButton);
    }


}
