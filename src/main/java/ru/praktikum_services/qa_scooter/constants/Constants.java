package ru.praktikum_services.qa_scooter.constants;

public class Constants {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier";
    public static final int LOGIN_COURIER_SUCCESS_STATUS_CODE = 200;
    public static final int CREATE_COURIER_SUCCESS_STATUS_CODE = 201;
    public static final int CREATE_COURIER_WITH_THE_SAME_LOGIN_STATUS_CODE = 409;
    public static final int CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_STATUS_CODE = 400;
    public static final String CREATE_COURIER_WITHOUT_PASSWORD_OR_LOGIN_MESSAGE = "Недостаточно данных для создания учетной записи";
    public static final String CREATE_COURIER_WITH_THE_SAME_LOGIN_MESSAGE = "Этот логин уже используется";
    public static final int DELETE_COURIER_SUCCESS_STATUS_CODE = 200;
    public static final boolean CREATE_COURIER_SUCCESS_BODY_VALUE = true;


}
