package ru.praktikum_services.qa_scooter.POJO;

import java.util.List;

//класс для обертки ответа
public class OrderListResponse {
    private List<OrderList> orders;

    // Конструктор по умолчанию
    public OrderListResponse() {
    }

    // Конструктор с параметром
    public OrderListResponse(List<OrderList> orders) {
        this.orders = orders;
    }

    // Getter и Setter
    public List<OrderList> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderList> orders) {
        this.orders = orders;
    }
}
