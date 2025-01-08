import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SwipeGesture extends LoginBasics {

    private static final By Email_Button = AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText[1]");
    private static final By Password_Button = AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText[2]");
    private static final By Login1_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Login\"]");
    private static final By Check_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"You have no orders yet\"]");
    private static final By SideBar_Button = AppiumBy.xpath("//android.view.ViewGroup[@resource-id=\"cross-fade-icon-current\"]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView");
    private static final By Report_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Reports\"]");
    private static final By Visits_Button = AppiumBy.xpath("(//android.widget.TextView[@text=\"No data\"])[2]");
    private static final By Order_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Order source\"]");

    @DataProvider(name = "loginScenario")
    public Object[][] loginScenarios() {
        return new Object[][]{
                {"meyaj38923@pofmagic.com", "12345Aa@", true},
        };
    }

    @Test(dataProvider = "loginScenario")
    public void SwipeGesture1(String email, String password, boolean expected) {
        try {
            // Remplir le champ email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(Email_Button));
            emailField.click();
            emailField.clear();
            emailField.sendKeys(email);
            driver.hideKeyboard();

            // Remplir le champ mot de passe
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(Password_Button));
            passwordField.click();
            passwordField.clear();
            passwordField.sendKeys(password);
            driver.hideKeyboard();

            // Cliquer sur le bouton de connexion
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(Login1_Button));
            loginButton.click();

            // Vérifier la présence du nom du profil
            wait.until(ExpectedConditions.elementToBeClickable(Check_Button));
            System.out.println("Connexion réussie avec l'email et mot de passe : " + email + password);

            // Ouvrir SideBar
            wait.until(ExpectedConditions.elementToBeClickable(SideBar_Button)).click();

            // Cliquer sur Report_Button
            wait.until(ExpectedConditions.elementToBeClickable(Report_Button)).click();

            // Scroll down to the Visibility_BUTTON
            scrollToElement(Visits_Button);

            //click on Visits|_Button
            wait.until(ExpectedConditions.elementToBeClickable(Visits_Button)).click();

            // Wait for the Visits_BUTTON to be visible
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(Visits_Button)).isDisplayed();
            Assert.assertTrue(isDisplayed, "'Visits' n'est pas affiché sur l'écran.");
            System.out.println("'Visits' est bien affiché sur l'écran.");
        } catch (Exception e) {
            Assert.fail("Le test a échoué en raison d'une exception : " + e.getMessage());
        }

        // Ensure the Order_Button is visible before performing the swipe
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Order_Button));

    // Ensure the element is interactable
        if (element.isDisplayed() && element.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
                    ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
                            "direction", "left",
                            "percent", 1));
            System.out.println("Swipe Gesture performed on element: " + element);
        } else {
            System.out.println("Element not interactable, swipe gesture not performed.");
        }


    }


    // Méthode améliorée pour scroller vers un élément
    public void scrollToElement(By elementLocator) {
        try {
            AndroidDriver driver = (AndroidDriver) this.driver;

            // Utiliser UiScrollable pour scroller dans la vue
            String uiScrollableCommand = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(";

            // Ajouter le critère correct selon le localisateur
            if (elementLocator.toString().contains("text=")) {
                String text = elementLocator.toString().split("text=\"")[1].split("\"")[0];
                uiScrollableCommand += "new UiSelector().text(\"" + text + "\"));";
            } else if (elementLocator.toString().contains("resource-id=")) {
                String resourceId = elementLocator.toString().split("resource-id=\"")[1].split("\"")[0];
                uiScrollableCommand += "new UiSelector().resourceId(\"" + resourceId + "\"));";
            } else {
                throw new IllegalArgumentException("Localisateur non pris en charge pour le scroll.");
            }

            // Exécuter la commande
            driver.findElement(AppiumBy.androidUIAutomator(uiScrollableCommand));
            System.out.println("Défilement effectué jusqu'à l'élément : " + elementLocator);
        } catch (Exception e) {
            System.err.println("Erreur lors du défilement : " + e.getMessage());
            throw e;
        }
    }


}









