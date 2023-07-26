package www.rest.api.restapi;


	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.Test;
	import io.restassured.RestAssured;
	import io.restassured.path.json.JsonPath;
	import io.restassured.response.Response;
	import java.util.Scanner;

	public class WeatherAPI {

	    private Scanner sc;

	    @BeforeMethod
	    public void setup() {
	        sc = new Scanner(System.in);
	    }

	    @AfterMethod
	    public void cleanup() {
	        sc.close();
	    }

	    @Test
	    public void weatherTest() {
	        System.out.println("1. Get weather\n2. Get Wind Speed\n3. Get Pressure\n0. Exit");

	        int option;
	        while (true) {
	            option = sc.nextInt();

	            if (option == 0) {
	                System.out.println("Terminating the program...");
	                break; // Exit the loop if option is 0
	            }

	            if (option < 1 || option > 3) {
	                System.out.println("Invalid option. Please select a valid option.");
	                continue; // Restart the loop if the option is invalid
	            }

	            System.out.println("Enter date");
	            String date = sc.next();
	            String url = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	            Response response = RestAssured.get(url);
	            JsonPath jsonPathEvaluator = response.jsonPath();
	            int l = jsonPathEvaluator.getList("list").size();

	            boolean found = false;
	            for (int i = 0; i < l; i++) {
	                if (date.equals(jsonPathEvaluator.getString("list[" + i + "].dt"))) {
	                    found = true;
	                    if (option == 1) {
	                        System.out.println("Temperature: " + jsonPathEvaluator.getDouble("list[" + i + "].main.temp"));
	                        break;
	                    } else if (option == 2) {
	                        System.out.println("Speed: " + jsonPathEvaluator.getDouble("list[" + i + "].wind.speed"));
	                        break;
	                    } else if (option == 3) {
	                        System.out.println("Pressure: " + jsonPathEvaluator.getDouble("list[" + i + "].main.pressure"));
	                        break;
	                    } else {
	                        System.out.println("Invalid option");
	                    }
	                }
	            }

	            if (!found) {
	                System.out.println("No data found for the given date.");
	            }

	            System.out.println("\n1. Get weather\n2. Get Wind Speed\n3. Get Pressure\n0. Exit");
	        }
	    }
	}
//         2019-03-27 19:00:00
//          1553716800
//			"dt": 1553713200,
//			"temp": 278.4,
//			 "pressure": 1033.061,
//			 "speed": 1.49,

