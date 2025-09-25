package com.stqa.triangle;

public class Triangle {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Стороны треугольника должны быть положительными");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Нарушено неравенство треугольника");
        }

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
