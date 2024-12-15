package com.example.laptopgiahuy2.util;

public enum OrderStatus {
    DANG_XULY(1,"DANG XU LY"),
    DAT_HANG(2,"DAT HANG"),
    DONG_GOI(3,"DONG GOI SAN PHAM"),
    DANG_VANCHUYEN(4,"DANG VAN CHUYEN"),
    HOAN_TAT(5,"HOAN TAT DON HANG");

    private Integer id;
    private String name;

    OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
