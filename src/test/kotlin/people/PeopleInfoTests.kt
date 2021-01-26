package people

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import utils.Endpoints.GET_PEOPLE_INFO
import org.junit.Test
import io.restassured.RestAssured.get
import io.restassured.http.ContentType.JSON
import utils.StatusCodes.SUCCESS
import com.google.gson.Gson
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import pojo.People

class PeopleInfoTests {
    private val baseUrl = "https://swapi.dev/api/"

    @Test
    @DisplayName("Testing if /people/ response is correct")
    fun getPeopleInfoTest() {
        val quantityOfPeople = 82
        val nextPageLink = "http://swapi.dev/api/people/?page=2"
        val previousPageLink = null
        val firstPersonName = "Luke Skywalker"
        val lastPersonHairColor = "auburn, white"

        val response = get(baseUrl + GET_PEOPLE_INFO.endPoint)

        with(response) {
            assertEquals(
                "Checking that statusCode is ${SUCCESS.status}",
                SUCCESS.status, statusCode()
            )
            assertTrue(
                "Checking that contentType is JSON",
                contentType().contains(JSON.toString())
            )
        }
        val objectFromResponseJson = Gson().fromJson(response.asString(), People::class.java)

        with(objectFromResponseJson) {
            assertAll("Checking that response contains correct data",
                {
                    assertEquals(
                        "Checking that quantity of people is $quantityOfPeople",
                        quantityOfPeople, count
                    )
                },
                {
                    assertEquals(
                        "Checking that link to the next page is $nextPageLink",
                        nextPageLink, next
                    )
                },
                {
                    assertEquals(
                        "Checking that link to the previous page is $previousPageLink",
                        previousPageLink, previous
                    )
                },
                {
                    assertTrue(
                        "Checking that results is not empty",
                        results.isNotEmpty()
                    )
                }
            )
            assertAll("Checking that results contains correct data",
                {
                    assertEquals(
                        "Checking that first person name is $firstPersonName",
                        firstPersonName, results.first().name
                    )
                },
                {
                    assertEquals(
                        "Checking that last person hair color is $lastPersonHairColor",
                        lastPersonHairColor, results.last().hairColor
                    )
                }
            )
        }
    }
}