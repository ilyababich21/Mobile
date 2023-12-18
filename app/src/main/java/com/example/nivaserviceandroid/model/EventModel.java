package com.example.nivaserviceandroid.model;

import androidx.annotation.Nullable;

public class EventModel {
    public String[] EventMessages = new String[]{
            "Питание включено", "Тест защит пройден", "Тест защит не пройден", "Сигнал сирены",
            "Сброс защит", "Пылеотсос вкл(М1)", "Насосная станция вкл(М10)", "Конвейер вкл(М2)",
            "Конвейер реверс вкл(М2)", "Бермовый орган вкл(М4-М5)", "Режущие диски вкл(М6)",
            "Питание БП вкл(М12)", "АПШ включен", "Пылеотсос откл(М1)", "Насосная станция откл(М10)",
            "Конвейер откл(М2)", "Конвейер реверс откл(М2)", "Бермовый орган откл(М4-М5)",
            "Режущие диски откл(М6)", "Питание БП откл(М12)", "АПШ отключен", "Выбран режим работа",
            "Выбран режим наладка", "Выбран режим АПШ", "Выбран режим проверка", "Силовой автомат включен",
            "Силовой автомат отключен", "Изменены параметры защит", "Выбран режим БП авто",
            "Выбран режим БП полу-авто", "Выбран режим БП ручной", "Выбран типа БП", "Выбран типа БПС",
            "Маневр вкл", "Маневр откл", "Подача дистанционная вкл", "Подача дистанционная откл",
            "Ход разрешен", "Ход запрещен", "Низкий уровень масла", "УКУ местное", "УКУ дистанционное",
    };

    private int id;
    private String date;
    private String event;

    @Nullable
    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getEvent() {
        return event;
    }

    public void setEvent(@Nullable String event) {
        this.event = event;
    }


}
