package Pages;

import Locators.LoginPageLocators;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;


public class LoginPage {
    LoginPageLocators loginPageLocators=new LoginPageLocators();
    private Page page;
    private BrowserContext context;

    public LoginPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }


    public void enterEmail(String mail) {
        page.fill(loginPageLocators.emailField,mail);
    }

    public void enterPassword(String password) {
        page.fill(loginPageLocators.passwordField,password);
    }


    public void clickLogin() {
        page.click(loginPageLocators.loginButton);
    }

    public void clickOtpButton() {
        page.click(loginPageLocators.otpButton);
    }

    public void enterOtp(){
        page.fill("//input[@class='otp__textField space item active']","3");
        page.fill("//input[@class='otp__textField space item active']","3");
        page.fill("//input[@class='otp__textField space item active']","3");
        page.fill("//input[@class='otp__textField space item active']","3");
//        List<ElementHandle> otpInputs=page.querySelectorAll(loginPageLocators.otpInputFields);
//        for(ElementHandle otpInput:otpInputs){
//            otpInput.fill("3");
//        }
    }

    public boolean isEmailEntered(String email) {
        page.waitForSelector(loginPageLocators.emailField,new Page.WaitForSelectorOptions().setTimeout(10000));
        System.out.println(page.locator(loginPageLocators.emailField).innerText());
        return email.equals(page.locator(loginPageLocators.emailField).inputValue());
    }

    public boolean isPasswordEntered(String password) {
        page.waitForSelector(loginPageLocators.passwordField,new Page.WaitForSelectorOptions().setTimeout(10000));
        System.out.println(page.locator(loginPageLocators.passwordField).innerText());
        return password.equals(page.locator(loginPageLocators.passwordField).inputValue());
    }

}
