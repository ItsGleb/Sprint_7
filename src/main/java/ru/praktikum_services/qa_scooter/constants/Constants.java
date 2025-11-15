package ru.praktikum_services.qa_scooter.constants;

public class Constants {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/";
    public static final String CREATE_OR_GET_ORDER_PATH = "/api/v1/orders";
    public static final String CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE = "Недостаточно данных для создания" +
            " учетной записи";
    public static final String CREATE_COURIER_WITH_THE_SAME_LOGIN_MESSAGE = "Этот логин уже используется";
    public static final String LOGIN_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE = "Недостаточно данных для входа";
    public static final String LOGIN_NONEXISTENT_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE = "Учетная запись не найдена";




}
