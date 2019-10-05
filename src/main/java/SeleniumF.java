import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class SeleniumF {

    //obj selenium
    private WebDriver wd;
    // consumables
    private int choosePosition;
    private int chooseCons;
    private String maxBuyPrice;
    //setup
    private String url;
    private int playerOrCons;
    //liczniki
    private static int licznik1=1;
    private static int licznik2=0;
    //player




    @Before
   public void setUp()
    {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        wd = new ChromeDriver(options);


        //setup
        url = "https://www.easports.com/pl/fifa/ultimate-team/fut-app";
        playerOrCons = 0;
        //consumbles
        maxBuyPrice = "1000";
        choosePosition = 20; // 19
        chooseCons = 4;      // 3
        //player


    }

    @Test
    public void sGoogle() throws InterruptedException
    {

        // logowanie
        wd.get(url);
        wd.findElement(By.partialLinkText("Logowanie w")).click();
        //Thread.sleep(10000);
        //wd.findElement(By.cssSelector("button.standard.call-to-action")).click();
        Thread.sleep(14000);
        try
        {
            wd.findElement(By.cssSelector("button.btnFooter.btnTransfers")).click();

        }
        catch (ElementNotVisibleException ex)
        {
            System.out.println("Próbuję poczekać...");
            Thread.sleep(7000);
            wd.findElement(By.cssSelector("button.btnFooter.btnTransfers")).click();

        }
        Thread.sleep(1500);
        wd.findElement(By.cssSelector("div.tile.transferMarketTile")).click();
        Thread.sleep(1500);


        //consumables(choosePosition,chooseCons);

        //http://wklej.org/id/3379661/

        //players(2,3);  //quality
        //players(3,4);  //position
        //players(4,4);  //style
        //players(5,35); //nationality
        players(6,37); //league championship(13)
        players(7,15); //club forrest(18)

//        Thread.sleep(10000);

        WebElement inputBuyMaxPrice =  wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.searchPrices > div:nth-child(6) > div.buttonInfo.bidSpinner > div > input"));
        sendChar(inputBuyMaxPrice, maxBuyPrice, 200);





       for (int i=0; i<10000; i++)
       {
           WebElement btnPlus =  wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.searchPrices > div:nth-child(2) > div.buttonInfo.bidSpinner > button.standard.incrementBtn"));
           WebElement btnMinus =  wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.searchPrices > div:nth-child(2) > div.buttonInfo.bidSpinner > button.standard.decrementBtn"));

           if(i%2==0)
           {
               btnPlus.click();
           }
           else if(i%2==1)
           {
               btnMinus.click();
           }

           isCard();
       }

    }

    @After
    public void close()
    {
        out.println("Zrobione");
    }



    public void sendChar(WebElement element, String value, int delay) throws InterruptedException {
        element.clear();

        for (int i = 0; i < value.length(); i++){
            Thread.sleep(delay);
            char c = value.charAt(i);
            String s = new StringBuilder().append(c).toString();
            element.sendKeys(s);
        }
    }

    public void isCard() throws InterruptedException
    {
        String divEmpty ="div.emptySearch";
        WebElement back = wd.findElement(By.cssSelector("a.btn-flat.back.headerButton"));

        WebElement searchButton = wd.findElement(By.cssSelector("button.standard.call-to-action"));
        searchButton.click();

        Thread.sleep(3000);
        if(existsElement(divEmpty))
        {

            System.out.println("Nie znaleziono nic na rynku - "+licznik1+" -> "+licznik2);
            licznik1++;

        }
        else {

            WebElement buy = wd.findElement(By.cssSelector("button.standard.buyButton.coins"));
            buy.click();
            Thread.sleep(500);

            licznik2++;
            System.out.println("ZNALEZIONO - " + licznik2);
            /*
            if (existsElement("div.popupClickShield.ui-shield-style-transparent > section > div > p"))
            {

                WebElement bidError = wd.findElement(By.cssSelector("div.popupClickShield.ui-shield-style-transparent > section > div > footer > a"));
                bidError.click();
            }
            else{
                WebElement confirmBuy = wd.findElement(By.cssSelector("section > div > footer > a:nth-child(2)"));
                confirmBuy.click();
            } */

            WebElement confirmBuy = wd.findElement(By.cssSelector("section > div > footer > a:nth-child(2)"));
            confirmBuy.click();



            if(licznik2==5)
            {
                System.out.println("Kupiono maks kart");
            }

            Thread.sleep(1000);



        }

        Thread.sleep(250);
        back.click();
        Thread.sleep(2000);

    }



    public boolean existsElement(String css) {
        try
        {
            wd.findElement(By.cssSelector(css));
        } catch (NoSuchElementException e)
        {
            return false;
        }
        return true;
    }

    public void consumables(int choosePosition, int chooseCons) throws InterruptedException {
        wd.findElement(By.partialLinkText("CONS")).click();
        Thread.sleep(1500);
        wd.findElement(By.cssSelector("div.inline-list-select.filter.has-selection.has-image")).click();
        Thread.sleep(1500);
        wd.findElement(By.cssSelector("li:nth-child("+chooseCons+").with-icon")).click();
        Thread.sleep(1500);
        wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.clearfix > div:nth-child(4) > div")).click();
        Thread.sleep(1500);
        wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.clearfix > div.inline-list-select.filter.has-default.is-open > div > ul > li:nth-child("+choosePosition+")")).click();


    }

    public void players(int category, int uncategory) throws InterruptedException {

        wd.findElement(By.cssSelector("div.search-container > div.filters-container-parent > div > div.filters-scroller > div.clearfix > div:nth-child("+category+")")).click();
        Thread.sleep(500);
        wd.findElement(By.cssSelector("div.filters-scroller > div.clearfix > div.inline-list-select.filter.has-default.has-image.is-open > div > ul > li:nth-child("+uncategory+")")).click();
        Thread.sleep(500);
    }




}



