package ru.praktikum_services.qa_scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import ru.praktikum_services.qa_scooter.POJO.Courier;
import ru.praktikum_services.qa_scooter.POJO.Order;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Steps {

    @Step("Получение Id курьера после логина")
    public void setIdFromResponse(Response response, Courier courier) {
        if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
            try {
                int id = response.jsonPath().getInt("id");
                courier.setId(id);
                System.out.println("ID курьера успешно получен = " + courier.getId());
            } catch (Exception e) {
                System.out.println("Не удалось получить ID курьера : " + e.getMessage());
            }
        }
    }

    @Step("Создание курьера")
    public Response createCourier(Courier courier, String url_path) {
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(courier)
                        .when()
                        .post(url_path);
        return response;
    }

    @Step("Логин курьера")
    public Response loginCourier(Courier courier, String url_path) {
        Map<String, String> loginUserJson = new HashMap<>(); // Нам нужно для создания тела запроса
        loginUserJson.put("login", courier.getLogin()); // Создаем параметр login
        loginUserJson.put("password", courier.getPassword()); // Создаем параметр password
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(loginUserJson)
                        .when()
                        .post(url_path);
        setIdFromResponse(response, courier);
        return response;
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String url_path, int id) {
        Map<String, String> userDeleteBody = new HashMap<>();
        userDeleteBody.put("id", String.valueOf(id));
        Response response = given()
                .body(userDeleteBody)
                .when()
                .delete(url_path + id);
        if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Курьер с ID = " + id + " был успешно удален");
        }
        return response;
    }

    @Step("Удаление курьера без возможности обработать ответ")
    public void justDeleteCourier(String url_path, int id) {
        Map<String, String> userDeleteBody = new HashMap<>();
        userDeleteBody.put("id", String.valueOf(id));
        Response response =
                given()
                        .body(userDeleteBody)
                        .when()
                        .delete(url_path + id);
        if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Курьер с ID = " + id + " был успешно удален");
        }
    }

    @Step("Создание курьера без возможности обработать ответ")
    public void justCreateCourier(Courier courier, String url_path) {

        given()
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(url_path);
    }

    @Step("Создание заказа")
    public Response createOrder(Order order, String url_path) {
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(order)
                        .when()
                        .post(url_path);
        return response;
    }

    @Step("Получение списка заказов без параметров")
    public Response getOrderList(String url_path){
        Response response =
                given()
                        .get(url_path);
        return response;
    }
}
