package com.stqa.triangle;

public class Triangle {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Периметр
    public double perimeter() {
        return a + b + c;
    }

    //Площадь по Герону
    public double area() {
        double p = perimeter() / 2.0;
        return Math.sqrt(p * (p-a) * (p-b) * (p-c));
    }
}
