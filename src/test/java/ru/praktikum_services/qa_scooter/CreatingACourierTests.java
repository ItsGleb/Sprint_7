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


public class CreatingACourierTests {
    private Courier courier;
    private Steps steps;


    @Before
    public void setUp() {
        // Базовая часть URL
        RestAssured.baseURI = BASE_URL;
        steps = new Steps();
    }

    @Test
    @DisplayName("Успешное создание нового курьера")
    @Description("Проверяем что тело ответа содержит параметр 'ok' со значением 'true' и статус-кодом = 201")
    public void courierCreationSuccessTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "1234", "Gleb");

        // Отправляем запрос на создание курьера
        Response response = steps.createCourier(courier, CREATE_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_CREATED, response);

        // Проверяем наличие параметра ок
        assertResponseHasKey("ok", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("ok", true, response);
    }

    @Test
    @DisplayName("Невозможность создать двух курьеров с одинаковым логином")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Этот логин уже используется'" +
            " и статус-кодом = 409")
    public void creatingTwoAccountsWithTheSameLoginTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "1234", "Gleb");

        // Отправляем запрос на создание курьера
        Response response = steps.createCourier(courier, CREATE_COURIER_PATH);

        // Отправляем запрос на создание курьера с тем же логином
        Courier courierWithTheSameLogin = new Courier("Koshe4kin", "12345", "Ivan");
        Response theSameLoginResponse = steps.createCourier(courierWithTheSameLogin, CREATE_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_CONFLICT, theSameLoginResponse);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", theSameLoginResponse);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message", CREATE_COURIER_WITH_THE_SAME_LOGIN_MESSAGE,
                response);

    }


    @Test
    @DisplayName("Создание курьера без login")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением 'Недостаточно данных " +
            "для создания учетной записи' и статус-кодом = 400")
    public void creatingCourierWithoutRequiredFieldLoginTest() {
        // Формируем тело запроса
        courier = new Courier("", "1234", "Gleb");

        // Отправляем запрос на создание курьера
        Response response = steps.createCourier(courier, CREATE_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_BAD_REQUEST, response);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message", CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE,
                response);

    }

    @Test
    @DisplayName("Создание курьера без password")
    @Description("Проверяем что тело ответа содержит параметр 'message' со значением " +
            "'Недостаточно данных для создания учетной записи' и статус-кодом = 400")
    public void creatingCourierWithoutRequiredFieldPasswordTest() {
        // Формируем тело запроса
        courier = new Courier("Koshe4kin", "", "Gleb");

        // Отправляем запрос на создание курьера
        Response response = steps.createCourier(courier, CREATE_COURIER_PATH);

        // Проверяем статус код
        assertStatusCode(HttpURLConnection.HTTP_BAD_REQUEST, response);

        // Проверяем наличие параметра message
        assertResponseHasKey("message", response);

        // Проверяем тело ответа
        assertResponseKeyValueIsCorrect("message", CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE,
                response);

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
