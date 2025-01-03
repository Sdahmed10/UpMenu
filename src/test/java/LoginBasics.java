import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class LoginBasics {
    // Déclaration d'une variable statique pour le driver Android, utilisée pour interagir avec l'application.
    static AndroidDriver driver;
    // Déclaration d'une variable pour gérer les attentes explicites.
    WebDriverWait wait;
    //Méthode annotée avec @BeforeTest exécutée avant le lancement des tests.
    //Elle configure l'environnement nécessaire pour l'exécution des tests.
    @BeforeTest
    public void setUp() throws MalformedURLException {
        // Création d'un objet DesiredCapabilities pour définir les capacités souhaitées de l'appareil ou de l'émulateur.
        DesiredCapabilities caps = new DesiredCapabilities();
        // Spécifie la plateforme cible pour le test (Android).
        caps.setCapability("appium:platformName", "Android");
        // Définit le framework d'automatisation à utiliser (UiAutomator2 pour Android).
        caps.setCapability("appium:automationName", "UiAutomator2");
        // Indique la version du système d'exploitation Android de l'appareil ou de l'émulateur.
        caps.setCapability("appium:platformVersion", "14");
        // Spécifie le nom de l'appareil ou de l'émulateur utilisé pour le test.
        caps.setCapability("appium:deviceName", "emulator-5554");
        // Indique le chemin absolu vers l'application (.apk) à tester.
        caps.setCapability("appium:app", "/Users/takiacademy/Desktop/OrangeHrmExemple/src/main/resources/UpMenu_0.77.0_APKPure.apk");
//        caps.setCapability("appium:autoGrantPermissions", true);
//        caps.setCapability("appium:ignoreHiddenApiPolicyError", true);
        // Affiche un message pour indiquer que le serveur Appium a démarré.
        System.out.println("Appium server started..");
        // Initialisation du AndroidDriver avec l'URL du serveur Appium et les capacités définies.
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        // Initialisation d'un objet WebDriverWait avec un timeout de 10 secondes.
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Méthode annotée avec @AfterTest exécutée après la fin des tests.
    //Elle permet de fermer et de nettoyer les ressources utilisées.
    @AfterTest
    public void tearDown() {
        // Vérifie si le driver est initialisé, puis ferme la session Appium.
        if (null != driver) {
            driver.quit();
        }
    }
}

