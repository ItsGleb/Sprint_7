package ru.praktikum_services.qa_scooter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.POJO.OrderList;
import ru.praktikum_services.qa_scooter.steps.Steps;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import static ru.praktikum_services.qa_scooter.constants.Constants.BASE_URL;
import static ru.praktikum_services.qa_scooter.constants.Constants.CREATE_ORDER_PATH;
import static ru.praktikum_services.qa_scooter.general_assert.GeneralAssert.*;


@RunWith(Parameterized.class)
public class CreatingOrderTests {

    private OrderList orderList;
    private Steps steps;

    /* Параметры теста - объявляем как поля класса. Потому что в параметризованном тесте JUnit 4 параметры должны
    передаваться через конструктор класса */
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;
    private int expectedStatusCode;

    // Нужно для потому что в параметризованном тесте JUnit 4 параметры должны передаваться через конструктор класса
    public CreatingOrderTests(String firstName, String lastName, String address, String metroStation,
                              String phone, int rentTime, String deliveryDate, String comment,
                              List<String> color,int expectedStatusCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        // Базовая часть URL
        RestAssured.baseURI = BASE_URL;
        steps = new Steps();
    }


    @Parameterized.Parameters
    public static Object[][] getRequestData() {
        return new Object[][]{
                // Тест 1 цвет самоката черный
                {
                        "Иван",
                        "Иванов",
                        "Москва ул.Стартовая 1, кв 15",
                        "4",
                        "89102202095",
                        5,
                        "2025-11-20",
                        "Тест номер 1",
                        Arrays.asList("BLACK"),
                        HttpURLConnection.HTTP_CREATED
                },
                // Тест 2 цвет самоката серый
                {
                        "Иван",
                        "Иванов",
                        "Москва ул.Стартовая 1, кв 15",
                        "4",
                        "89102202095",
                        5,
                        "2025-11-20",
                        "Тест номер 2",
                        Arrays.asList("GREY"),
                        HttpURLConnection.HTTP_CREATED
                },
                // Тест 3 цвет оба цвета
                {
                        "Иван",
                        "Иванов",
                        "Москва ул.Стартовая 1, кв 15",
                        "4",
                        "89102202095",
                        5,
                        "2025-11-20",
                        "Тест номер 3",
                        Arrays.asList("GREY", "BLACK"),
                        HttpURLConnection.HTTP_BAD_REQUEST // Один самокат не может быть одновременно двух цветов
                },
                // Тест 4 цвет без цвета
                {
                        "Иван",
                        "Иванов",
                        "Москва ул.Стартовая 1, кв 15",
                        "4",
                        "89102202095",
                        5,
                        "2025-11-20",
                        "Тест номер 4",
                        Arrays.asList(),
                        HttpURLConnection.HTTP_CREATED
                }
        };
    }

    @Test
    public void createOrderTest() {
        // Создаем заказ
        orderList = new OrderList(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        // Отправляем запрос
        Response response = steps.createOrder(orderList, CREATE_ORDER_PATH);
        // Проверяем статус-код
        assertStatusCode(expectedStatusCode, response);
        // Проверяем что содержит поле 'track'
        assertResponseHasKey("track", response);
        // Проверяем что поле 'track' не пустое
        assertResponseKeyValueIsNotNull("track", response);
    }

}
