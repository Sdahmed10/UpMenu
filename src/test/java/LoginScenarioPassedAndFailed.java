import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginScenarioPassedAndFailed extends LoginBasics {
    private static final By Email_Button = AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText[1]");
    private static final By Password_Button = AppiumBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText[2]");
    private static final By Login1_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Login\"]");
    private static final By Check_Button = AppiumBy.xpath("(//android.view.ViewGroup[@resource-id=\"card\"])[1]/android.view.ViewGroup");
    private static final By SideBar_Button = AppiumBy.xpath("//android.view.ViewGroup[@resource-id=\"cross-fade-icon-current\"]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView");
    private static final By logout_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Logout\"]");
    private static final By Errormessage_Button = AppiumBy.xpath("//android.widget.TextView[@text=\"Wrong credential\"]");

    //annotation utilisée dans TestNG pour fournir des données de test à une méthode de test.
    //Elle permet d'exécuter une même méthode de test plusieurs fois avec des ensembles de données différents
    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][]{
                {"meyaj38923@pofmagic.com", "12345Aa@", true}, // Scénario valide
                {"fif", "2345678@Aa", false},  // Email et mot de passe incorrectes
                {"", "12345Aa@", false},    // Champ email vide
                {"meyaj38923@pofmagic.com", "", false},           // Champ mot de passe vide
                {"", "", false}                 // champ email et mot de passes vides
        };
    }

    @Test(dataProvider = "loginScenarios")
    public void loginScenario(String email, String password, boolean expected) {
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


            if (expected) {
                // Vérifier la présence du nom du profil
                wait.until(ExpectedConditions.elementToBeClickable(Check_Button));
                takeScreenshot("login_success_" + email );
                System.out.println("Connexion réussie avec l'email et mot de passe : " + email );

                // Ouvrir SideBar
                wait.until(ExpectedConditions.elementToBeClickable(SideBar_Button)).click();

                // Effectuer la déconnexion
                wait.until(ExpectedConditions.elementToBeClickable(logout_Button)).click();
                wait.until(ExpectedConditions.elementToBeClickable(Email_Button));
                takeScreenshot("logout_success_" + email);
                System.out.println("Déconnexion réussie après connexion.");
            } else {
                // Si connexion échouée, vérifier qu'un message d'erreur apparaît
                wait.until(ExpectedConditions.elementToBeClickable(Errormessage_Button));
                takeScreenshot("login_failure_" + email);
                System.out.println("Connexion échouée avec l'email : " + email);
                Assert.fail("Connexion échouée comme attendu pour : " + email);
            }

        } catch (Exception e) {
            takeScreenshot("error_" + email);
            System.err.println("Erreur lors de la tentative de connexion : " + e.getMessage());
        }
    }

    private void takeScreenshot(String screenshotName) {
        String screenshotDirectory = "screenshots";
        String screenshotPath = screenshotDirectory + "/" + screenshotName + ".png";

        try {
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            Path directoryPath = Paths.get(screenshotDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Files.copy(srcFile.toPath(), Paths.get(screenshotPath));
            System.out.println("Capture d'écran enregistrée à : " + screenshotPath);
        } catch (IOException e) {
            System.err.println("Échec de l'enregistrement de la capture d'écran : " + e.getMessage());
        }
    }

}



