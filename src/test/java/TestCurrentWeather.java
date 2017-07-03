import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TestCurrentWeather {
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec;
    @BeforeClass
    public static void setupRequestSpecBuilder()
    {
        builder = new RequestSpecBuilder();
        builder.setBaseUri("http://api.openweathermap.org")
                .setBasePath("/data/2.5/weather")
                .addQueryParam("q", "London,uk")
                .addQueryParam("appid", "c565d3d594a006f6b0f251ba9e43e167")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());
        requestSpec = builder.build();
    }
    @Test
    public void givenUrl_whenValidatesResponseWithStaticSettings_thenCorrect() {

        WeatherEntity weather = given()
                .spec(requestSpec).
                        when()
                .get().
                        then()
                .statusCode(200).extract().as(WeatherEntity.class);

        assertThat(weather.getName()).as("Verify city name").isEqualTo("London");

    }
}
