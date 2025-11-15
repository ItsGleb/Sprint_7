package ru.praktikum_services.qa_scooter;


import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.POJO.OrderList;
import ru.praktikum_services.qa_scooter.POJO.OrderListResponse;
import ru.praktikum_services.qa_scooter.steps.Steps;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static ru.praktikum_services.qa_scooter.constants.Constants.BASE_URL;
import static ru.praktikum_services.qa_scooter.constants.Constants.CREATE_OR_GET_ORDER_PATH;
import static ru.praktikum_services.qa_scooter.general_assert.GeneralAssert.*;

public class GetOrderListTest {

    private Steps steps;

    private OrderListResponse orderListResponse;

    @Before
    public void setUp() {
        // Базовая часть URL
        RestAssured.baseURI = BASE_URL;
        steps = new Steps();
    }

    @Test
    @DisplayName("Все активные/завершенные заказы курьера проверка статус-кода")
    @Description("Отправляем GET запрос без параметров и проверяем статус-код")
    public void getOrderListWithoutParamsTest() {
        Response response = steps.getOrderList(CREATE_OR_GET_ORDER_PATH);
        // Проверка статус-кода
        assertStatusCode(HttpURLConnection.HTTP_OK, response);
        // Проверка, что тело ответа не пустое
        orderListResponse = response.as(OrderListResponse.class);
        List<OrderList> orders = orderListResponse.getOrders();

        assertFalse("Список заказов не должен быть пустым", orders.isEmpty());
        System.out.println("Количество заказов: " + orders.size());

    }

    @Test
    @DisplayName("Все активные/завершенные заказы курьера проверяем что список заказов не пустой")
    @Description("Отправляем GET запрос без параметров и проверяем что список заказов не пустой")
    public void getOrderListWithoutParamsOrdersIsNotEmptyTest() {
        Response response = steps.getOrderList(CREATE_OR_GET_ORDER_PATH);

        // Проверка, что тело ответа не пустое
        orderListResponse = response.as(OrderListResponse.class);
        List<OrderList> orders = orderListResponse.getOrders();
        
        assertFalse("Список заказов не должен быть пустым", orders.isEmpty());
        System.out.println("Количество заказов: " + orders.size());

    }
}
