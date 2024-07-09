package Class;

import Pages.*;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import jdk.jfr.Description;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class PlayWright {
    private Playwright playwright;
    private APIRequestContext request;

    void createPlaywright(){
        playwright=Playwright.create();
    }

    void createApiRequestContext(String baseUrl){
        HashMap<String,String> map=new HashMap<>();
        map.put("accept","application/json");
        request=playwright.request()
                .newContext(new APIRequest.NewContextOptions().setBaseURL(baseUrl).setExtraHTTPHeaders(map));
    }

    @Test(description = "Automate blibli retail store")
    @Description("To automate the task of user login then choosing a product after applying filters and then oredering the product and finally cancel the product")
    public void Test1() throws IOException, InterruptedException {
        Properties properties=new Properties();
        properties.load(new FileInputStream("src/test/java/input.properties"));
        Playwright playwright=Playwright.create();
        Browser browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page=browser.newPage();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        page.setViewportSize(width, height);

        createPlaywright();
        createApiRequestContext("https://wwwpreprod.gdn-app.com/");
        page.navigate(properties.getProperty("baseUrl"));
        APIResponse response=request.get("backend/search/getSeedKeyword", RequestOptions.create());
        System.out.println(response.text());

        String responseBody = response.text();
        JSONObject jsonResponse = new JSONObject(responseBody);


        JSONArray keywordsArray = jsonResponse.getJSONArray("data");
        List<String> keywords=new ArrayList<>();
        for (int i = 0; i < keywordsArray.length(); i++) {
            JSONObject keywordObject = keywordsArray.getJSONObject(i);
            String keyword = keywordObject.getString("keyword");
            keywords.add(keyword);
        }

        System.out.println(keywords);

        HomePage homePage=new HomePage(page,page.context());
        homePage.searchProduct(keywords.get(0));
        Assert.assertTrue(homePage.validateProduct(keywords.get(0)),"Product Mismatch");
        homePage.applyLocationFilter("Jabodetabek");
        Assert.assertTrue(homePage.isFilterApplied("Jabodetabek"),"Filter not applied");
        homePage.applyColorFilter("multicolor");
        Assert.assertTrue(homePage.isFilterApplied("Multicolor"),"Filter not applied");
        homePage.applyProductStockFilter("Ready Stock");
        Assert.assertTrue(homePage.isFilterApplied("Ready Stock"),"Filter not applied");

        homePage.applySorting("Terpopuler");
        Assert.assertTrue(homePage.isSortingApplied("Terpopuler"));


        Page newPage = homePage.getContext().waitForPage(() -> {
            homePage.openMaximumPriceProduct();
        });
        newPage.waitForLoadState();
        Page newpage = homePage.getContext().pages().get(1);

        ProductDetailsPage productDetailsPage=new ProductDetailsPage(newpage,newpage.context());
        Assert.assertTrue(productDetailsPage.isAddToCartVisible(),"The add to cart button is not visible");
        productDetailsPage.findRatings();
        productDetailsPage.addToCart();
        Assert.assertTrue(productDetailsPage.isAddToCartVisible(),"The Product not added to cart");
        productDetailsPage.moveToCart();


        LoginPage loginPage=new LoginPage(productDetailsPage.getPage(),productDetailsPage.getContext());

        loginPage.enterEmail("subash28122k3@gmail.com");
        Assert.assertTrue(loginPage.isEmailEntered("subash28122k3@gmail.com"),"Email not found in input");
        loginPage.enterPassword("Password@123");
        Assert.assertTrue(loginPage.isPasswordEntered("Password@123"),"Password not found in input");
        loginPage.clickLogin();
        loginPage.clickOtpButton();
        loginPage.enterOtp();

        CartPage cartPage=new CartPage(loginPage.getPage(),loginPage.getContext());
        Assert.assertTrue(cartPage.isCheckOutButtonVisible(),"The current page is not in the cart page");
        cartPage.addQuantity();
        Assert.assertTrue(cartPage.isQuantityAdded(),"Quantity not added");
        cartPage.addToWishList();
        cartPage.checkOut();

        CheckOutPage checkOutPage=new CheckOutPage(cartPage.getPage(), cartPage.getContext());
        Assert.assertTrue(checkOutPage.isNavigatedToCheckOutPage(),"Not navigated to checkout page");
        checkOutPage.chooseBank("Bank BRI / Bank Lainnya");
        Assert.assertTrue(checkOutPage.validateSelectecdBank("Bank BRI / Bank Lainnya"),"Bank is incorrect");
        checkOutPage.makePayment();

        ThankYouPage thankYouPage=new ThankYouPage(cartPage.getPage(), cartPage.getContext());
        thankYouPage.navigateToHomePage();


        HomePage homePage2 =new HomePage(thankYouPage.getPage(), thankYouPage.getContext());
        homePage2.hoverProfile();
        homePage2.clickOrderDetails();

        OrderDetailsPage orderDetailsPage=new OrderDetailsPage(homePage2.getPage(), homePage2.getContext());
        orderDetailsPage.clickOrderedProduct();
        orderDetailsPage.cancelOrder();
        orderDetailsPage.confirmCancelOrder();



    }


}
