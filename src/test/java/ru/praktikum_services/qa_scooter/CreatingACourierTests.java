package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.praktikum_services.qa_scooter.POJO.Courier;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import static ru.praktikum_services.qa_scooter.constants.Constants.*;
import static org.junit.Assert.assertEquals;


public class CreatingACourierTests {
    private Courier courier; // Нужно, чтобы передавать объект между Before и After



    @Before
    public void setUp() {
        // Базовая часть URL
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Успешное создание нового курьера")
    @Description("Проверяем что тело ответа содержит параметр 'ok' со значением 'true' и статус-кодом = 201")
    public void courierCreationSuccessTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "1234", "Gleb");
        // Отправляем запрос на создание курьера
        Response response = courier.createCourier(CREATE_COURIER_PATH);
        // Проверяем статус код
        int actualStatusCode = response.getStatusCode();
        assertEquals("Статус-код ответа не совпадает", CREATE_COURIER_SUCCESS_STATUS_CODE,
                actualStatusCode);
        // Проверяем наличие параметра ок
        assertThat("Параметр 'ok' должен присутствовать в ответе", response.jsonPath().getMap("$"),
                hasKey("ok"));
        // Проверяем тело ответа
        boolean actualBodyValue = response.jsonPath().getBoolean("ok");
        assertEquals("Неправильное значение параметра 'ok' в теле ответа.",
                CREATE_COURIER_SUCCESS_BODY_VALUE, actualBodyValue);
    }

    @Test
    @DisplayName("Невозможность создать двух курьеров с одинаковым логином")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Этот логин уже используется' и статус-кодом = 409")
    public void creatingTwoAccountsWithTheSameLoginTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "1234", "Gleb");
        // Отправляем запрос на создание курьера
        Response response = courier.createCourier(CREATE_COURIER_PATH);
        // Отправляем запрос на создание курьера с тем же логином
        Courier courierWithTheSameLogin = new Courier("Koshe4kin", "12345", "Ivan");
        Response theSameLoginResponse = courierWithTheSameLogin.createCourier(CREATE_COURIER_PATH);
        // Проверяем статус код
        int actualStatusCode = theSameLoginResponse.getStatusCode();
        assertEquals("Статус-код ответа не совпадает", CREATE_COURIER_WITH_THE_SAME_LOGIN_STATUS_CODE,
                actualStatusCode);
        // Проверяем наличие параметра message
        assertThat("Параметр 'message' должен присутствовать в ответе", theSameLoginResponse.jsonPath()
                .getMap("$"), hasKey("message"));
        // Проверяем тело ответа
        String actualMessage = theSameLoginResponse.jsonPath().getString("message");
        assertEquals("Неправильное значение параметра 'message' в теле ответа.",
                CREATE_COURIER_WITH_THE_SAME_LOGIN_MESSAGE, actualMessage);
    }



    @Test
    @DisplayName("Создание курьера без login")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных для создания учетной записи' и статус-кодом = 400")
    public void creatingCourierWithoutRequiredFieldLoginTest() {
        // Формируем тело запроса
        courier = new Courier("", "1234", "Gleb");
        // Отправляем запрос на создание курьера
        Response response = courier.createCourier(CREATE_COURIER_PATH);
        // Проверяем статус код
        int actualStatusCode = response.getStatusCode();
        assertEquals("Статус-код ответа не совпадает", CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_STATUS_CODE,
                actualStatusCode);
        // Проверяем наличие параметра message
        assertThat("Параметр 'message' должен присутствовать в ответе", response.jsonPath()
                .getMap("$"), hasKey("message"));
        // Проверяем тело ответа
        String actualMessage = response.jsonPath().getString("message");
        assertEquals("Неправильное значение параметра 'message' в теле ответа.",
                CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE, actualMessage);
    }
    @Test
    @DisplayName("Создание курьера без password")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных для создания учетной записи' и статус-кодом = 400")
    public void creatingCourierWithoutRequiredFieldPasswordTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "", "Gleb");
        // Отправляем запрос на создание курьера
        Response response = courier.createCourier(CREATE_COURIER_PATH);
        // Проверяем статус код
        int actualStatusCode = response.getStatusCode();
        assertEquals("Статус-код ответа не совпадает", CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_STATUS_CODE,
                actualStatusCode);
        // Проверяем наличие параметра message
        assertThat("Параметр 'message' должен присутствовать в ответе", response.jsonPath()
                .getMap("$"), hasKey("message"));
        // Проверяем тело ответа
        String actualMessage = response.jsonPath().getString("message");
        assertEquals("Неправильное значение параметра 'message' в теле ответа.",
                CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE, actualMessage);
    }


    @After
    public void deletingTheCreatedData() {
        Response response = courier.loginCourier(LOGIN_COURIER_PATH);
        if (response.getStatusCode() == LOGIN_COURIER_SUCCESS_STATUS_CODE) {
            int id = courier.getId();
            Response deleteResponse = courier.deleteCourier(DELETE_COURIER_PATH, id);
        }
    }
}
