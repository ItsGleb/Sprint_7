package ru.praktikum_services.qa_scooter.general_assert;


import io.restassured.response.Response;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;


public class GeneralAssert {

    // Проверка статус-кода
    public static void assertStatusCode(int expectedStatusCode, Response response) {
        int actualStatusCode = response.getStatusCode();
        assertEquals("Статус-код ответа не совпадает", expectedStatusCode,
                actualStatusCode);
    }

    //Проверка наличия параметра
    public static void assertResponseHasKey(String keyName, Response response) {
        assertThat("Параметр " + keyName + "должен присутствовать в ответе", response.jsonPath().getMap("$"),
                hasKey(keyName));
    }

    // Проверка значения параметра
    public static void assertResponseKeyValueIsCorrect(String keyName, Object expectedValue, Response response) {
        Object actualValue = response.jsonPath().get(keyName);
        assertEquals("Неправильное значение параметра '" + keyName + "' в теле ответа.",
                expectedValue, actualValue);
    }
    // Проверка значения параметра != null
    public static void assertResponseKeyValueIsNotNull(String keyName, Response response) {
        Integer actualValue = response.jsonPath().get(keyName);
        assertThat(keyName+" не должен быть null", actualValue, notNullValue());
    }
}
