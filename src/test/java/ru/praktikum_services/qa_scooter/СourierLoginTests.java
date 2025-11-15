package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.POJO.Courier;
import ru.praktikum_services.qa_scooter.steps.Steps;

import java.net.HttpURLConnection;


import static ru.praktikum_services.qa_scooter.constants.Constants.*;
import static ru.praktikum_services.qa_scooter.general_assert.GeneralAssert.*;
import static ru.praktikum_services.qa_scooter.general_assert.GeneralAssert.assertStatusCode;

// Внимательно перечитать проверки которые хочет яндекс . Возможно что-то упускаю
public class СourierLoginTests {

    private Courier courier;
    private Steps steps;


    @Before
    public void setUp() {
        // Базовая часть URL
        RestAssured.baseURI = BASE_URL;
        steps = new Steps();

    }

    @Test
    @DisplayName("Успешный логин в системе")
    @Description("Проверяем что тело ответа содержит параметр 'id' со значением int и статус-кодом = 200")
    public void courierLoginSuccessTest() {
        // Предварительно создадим курьера
        courier = new Courier("Koshe4kin", "1234", "Gleb");

        // Отправляем запрос на создание курьера
        steps.justCreateCourier(courier, CREATE_COURIER_PATH);

        // Логиним курьера в системе
        Response response = steps.loginCourier(courier, LOGIN_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_OK, response);

        // Проверяем наличие параметра 'id'
        assertResponseHasKey("id", response);

        // Проверяем что id != null
        assertResponseKeyValueIsNotNull("id", response);

    }

    @Test
    @DisplayName("Запрос на логин в системе без обязательного параметра 'login'")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных для входа' " +
            "и статус-кодом = 400")
    public void courierLoginWithoutLoginTest() {
        // Предварительно создадим курьера
        courier = new Courier("", "1234", "Gleb");

        // Логиним курьера в системе
        Response response = steps.loginCourier(courier, LOGIN_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_BAD_REQUEST, response);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message", LOGIN_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE,
                response);

    }

    @Test
    @DisplayName("Запрос на логин в системе без обязательного параметра 'password'")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных для входа' " +
            "и статус-кодом = 400")
    public void courierLoginWithoutPasswordTest() {
        // Предварительно создадим курьера
        courier = new Courier("Koshe4kin", "", "Gleb");

        // Логиним курьера в системе
        Response response = steps.loginCourier(courier, LOGIN_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_BAD_REQUEST, response);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message", LOGIN_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE,
                response);
    }

    @Test
    @DisplayName("Запрос на логин в системе c несуществующей парой login password")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных для входа' " +
            "и статус-кодом = 400")
    public void nonexistentCourierLoginTest() {
        // Предварительно создадим курьера
        courier = new Courier("Koshe4kin", "1234", "Gleb");

        // Логиним курьера в системе
        Response response = steps.loginCourier(courier, LOGIN_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_NOT_FOUND, response);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message",
                LOGIN_NONEXISTENT_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE, response);
    }


    @After
    public void deletingTheCreatedData() {
        Response response = steps.loginCourier(courier, LOGIN_COURIER_PATH);

        if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
            // Получаем значение Id из ответа
            int id = response.jsonPath().getInt("id");
            // Удаляем клиента
            steps.justDeleteCourier(DELETE_COURIER_PATH, id);
        }
    }
}
