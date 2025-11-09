package ru.praktikum_services.qa_scooter.POJO;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.qa_scooter.constants.Constants.*;

public class Courier {

    private String login;
    private String password;
    private String firstName;
    private int id;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setIdFromResponse(Response response) {
        if (response.getStatusCode() == LOGIN_COURIER_SUCCESS_STATUS_CODE) {
            try {
                this.id = response.jsonPath().getInt("id");
                System.out.println("ID курьера успешно получен = "+this.id);
            } catch (Exception e){
                System.out.println("Не удалось получить ID курьера : "+e.getMessage());
            }
        }

    }

    @Step("Создание курьера")
    public Response createCourier(String url_path) {
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(this) //Чтобы использовать данные экземпляра класса который вызывает метод
                        .when()
                        .post(url_path);
        return response;
    }

    @Step("Логин курьера")
    public Response loginCourier(String url_path) {
        Map<String, String> loginUserJson = new HashMap<>(); // Нам нужно для создания тела запроса
        loginUserJson.put("login", this.login); // Создаем параметр login со значением экземпляра класса который вызывает метод
        loginUserJson.put("password", this.password); // Создаем параметр password со значением экземпляра класса который вызывает метод
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(loginUserJson)
                        .when()
                        .post(url_path);
        setIdFromResponse(response);
        return response;
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String url_path,int id){
        Response response =
                given()
                        .delete(url_path+"/"+id);
        if(response.getStatusCode() == DELETE_COURIER_SUCCESS_STATUS_CODE){
            System.out.println("Курьер с ID = "+id+" был успешно удален");
        }
        return response;
    }
}
