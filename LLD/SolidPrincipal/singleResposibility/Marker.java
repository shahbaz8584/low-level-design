package singleResposibility;

public class Marker {

    String color;
    int price;
    String name;
    String brand;

    public Marker(String color, int price, String name, String brand) {
        this.color = color;
        this.price = price;
        this.name = name;
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

}
