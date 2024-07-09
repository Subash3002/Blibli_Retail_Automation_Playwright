package Pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class CheckOutPage {
    private Page page;
    private BrowserContext context;

    public final String bankField="//div[@data-testid='paymentGroup-2']//div[text()=' BANKNAME ']";
    public final String selectButton="//button[@data-testid='chooseMethodButton']";
    public final String changeBtn="//span[@data-testid='openPaymentModuleButton']";
    public final String skipButton="//button[@data-testid='onboardingSkipButton']";
    public final String paymentButton="//button[@data-testid='payButton']";
    public final String continuePaymentButton="//button[text()='Lanjutkan pembayaran']";
    public final String bankLabel="//span[@class='payment-item__detail__description__label']";
    public CheckOutPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public void chooseBank(String bankName){
        try {
            page.click(skipButton);
        } catch (Exception e) {
            System.out.println("Failed to click the skip button: " + e.getMessage());
        }
        page.click(changeBtn);
        page.click(bankField.replace("BANKNAME",bankName));
        page.click(selectButton);
    }

    public void makePayment(){
        page.click(paymentButton);
        try {
            page.click(continuePaymentButton);
        } catch (Exception e) {
            System.out.println("Failed to click the skip button: " + e.getMessage());
        }
    }


    public boolean isNavigatedToCheckOutPage() {
        page.waitForSelector(paymentButton,new Page.WaitForSelectorOptions().setTimeout(10000));
        return page.isVisible(paymentButton);
    }


    public boolean validateSelectecdBank(String bankName) {
        return page.locator(bankLabel).innerText().contains(bankName);
    }
}
