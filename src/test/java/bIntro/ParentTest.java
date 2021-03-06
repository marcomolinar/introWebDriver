package bIntro;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ParentTest {
	static WebDriver driver;
	static By searchBoxLocator = By.name("q");
	static By searchButtonLocator = By.id("navbar-submit-button");

	public static void setUp() {
		//System.setProperty("webdriver.chrome.driver", "C:\\automation\\drivers\\chromedriver.exe");  //propiedad explorador chrome		
		driver = new ChromeDriver();  //driver de chrome
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("http:\\imdb.com");//navegar a la pagina de IMDB.com
	}

	public static void clickElement(By elementLocator) {
		driver.findElement(elementLocator).click();
	}

	public static void inputText(By elementLocator, String text) {
		driver.findElement(elementLocator).sendKeys(text);
	}

	public static void verifyLinkExists(String linkText) {
		try { // para que se ponga el try cathc en automatico se selecciona el if else y se da clic derecho se selcciona sorroun with > try catch block
			if(driver.findElement(By.partialLinkText(linkText)).isDisplayed())
				System.out.println("The movie star " + linkText + " is displayed");
		} catch (Exception e) {  //lo del catch es lo que corresponde al else
			System.out.println("The movie star " + linkText + " is not displayed");
			System.exit(-1);
		}
	}

	public static void testMovieSearch(String movieName, String movieStar, String movieYear) {

		inputText(searchBoxLocator, movieName);						
		clickElement(searchButtonLocator);

		//Verificar que exista un link de esa pelicula	
		verifyLinkExists(movieName, movieYear);

		clickMovieLink(movieName, movieYear);

		//verificar que el director sea correcto
		verifyLinkExists(movieStar);
	}


	private static void clickMovieLink(String movieName, String movieYear) {
		String movieLinkXpath = "//td[contains(.,'"+ movieYear +"')]//a[contains(text(), '"+ movieName +"')]";
		driver.findElement(By.xpath(movieLinkXpath)).click();		
	}

	private static void verifyLinkExists(String movieName, String movieYear) {
		WebElement correctColumn = null;
		String movieXpath = "//h3[text() = 'Titles']/following-sibling::table//td";
		List<WebElement> movieLinks = driver.findElements(By.xpath(movieXpath));
		for(WebElement we: movieLinks) {
			if(we.getText().contains(movieYear)) {
				correctColumn = we;
				break;
			}	
		}
		
		if(correctColumn != null)
			System.out.println("Movie " + movieName + " exists.");
		else
			System.out.println("Movie " + movieName + " does not exist.");
		
		
	}

	public static void tearDown() {
		driver.quit();
	}
	
	//Facebook methods
	
	protected static void testSearchFriend(String friendName) {
		logIntoFacebook("clase.selenium.marisol","Test123456");
		searchFriend(friendName);
		
	}

	private static void searchFriend(String friendName) {
		inputText(By.name("q"), friendName);
		clickElement(By.xpath("//*[@data-testid='facebar_search_button']"));
		verifyLinkExists(friendName);
		
	}

	private static void logIntoFacebook(String email, String password) {
		inputText(By.id("email"),email);
		inputText(By.id("pass"), password);
		clickElement(By.name("u_0_t"));
		verifyLinkExists(email);
	}

}
