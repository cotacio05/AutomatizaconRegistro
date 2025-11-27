package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import driver.DriverFactory;

public class basePage {
    
    /*
     * Declaración de una variable 'driver' de tipo WebDriver
     */
    protected  WebDriver driver;
    protected WebDriverWait wait;


    public basePage(){
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

     public WebDriver getDriver() {
        return this.driver;
    }

     //Método estático para navegar a una URL.
    public void navegacionA(String url){
        driver.manage().window().maximize();
        driver.get(url);
    }


     // Encuentra y devuelve un WebElement en la página utilizando un locator XPath, esperando a que esté presentente en el DOM
     private WebElement Encontrar(String locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    // dar clic a un elemento
    public void clickElemento( String locator){

       WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        element.click();
    }

        //permite escribir 
    public void escribir(String locator, String KeysToSend){
        Encontrar(locator).clear();
        Encontrar(locator).sendKeys(KeysToSend);
    }

    public void verificarMensaje(String palabraEncontrada, String messages){
            String palabra = driver.findElement(By.xpath(palabraEncontrada)).getText();
            Assert.assertEquals(palabra, messages);
    
    }

}
