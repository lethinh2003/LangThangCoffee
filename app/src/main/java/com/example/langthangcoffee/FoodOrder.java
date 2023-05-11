package com.example.langthangcoffee;

public class FoodOrder {
    public static final int TYPE_CAFE = 1;
    public static final int TYPE_SODA = 2;
    public static final int TYPE_TRASUA = 3;
    public static final int TYPE_SWEETCAKE = 4;
private String hinhAnh;

    private int image;
    private String name;
    private int id;
    private int price;
    private int type;
    private boolean favorite;
    private String desc;

    public FoodOrder() {
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public FoodOrder(int image, String name, int price, int type, boolean favorite, String desc) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
        this.favorite = favorite;
        this.desc = desc;
    }

    public int getID() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int cost) {
        this.price = cost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
