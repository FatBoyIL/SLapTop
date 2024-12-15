package com.example.laptopgiahuy2.util;

public enum OrderStatus {
    DANG_XULY(1,"ĐANG XỬ LÝ"),
    DAT_HANG(2,"ĐẶT HÀNG"),
    DONG_GOI(3,"ĐÃ CHUẨN BỊ XONG"),
    DANG_VANCHUYEN(4,"ĐANG VẬN CHUYỂN"),
    HOAN_TAT(5,"HOÀN TẤT"),
    HUY_DON(6,"HỦY ĐƠN"),
    MAIL_CHECK(7,"ĐẶT HÀNG THÀNH CÔNG");

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
