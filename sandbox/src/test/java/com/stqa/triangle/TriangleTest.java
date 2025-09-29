package com.stqa.triangle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {
    @Test
    void testPerimeter () {
        Triangle x = new Triangle(9,12,15);
        assertEquals(36.0, x.perimeter());
    }
    @Test
    void testArea () {
        Triangle x = new Triangle(9,12,15);
        assertEquals(54.0, x.area());
    }
    @Test
    void testNegativeSide() {
        try {
            new Triangle(-9,12,15);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
    @Test
    void testZeroSide() {
        try {
            new Triangle(0,12,15);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
    @Test
    void testTriangleInequalityViolation () {
        try {
            new Triangle(1,12,15);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }

        try {
            new Triangle(100,12,15);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            // OK
        }
    }
    @Test
    void testEqualTriangles1 () {
        Triangle t1 = new Triangle(9,12,15);
        Triangle t2 = new Triangle(9,12,15);
        Assertions.assertEquals(t1, t2);
    }
    @Test
    void testEqualTriangles2 () {
        Triangle t1 = new Triangle(9,12,15);
        Triangle t2 = new Triangle(15,9,12);
        Assertions.assertEquals(t1, t2);
    }
    @Test
    void testNotEqualTriangles () {
        Triangle t1 = new Triangle(9,12,15);
        Triangle t2 = new Triangle(3,4,5);
        Assertions.assertNotEquals(t1, t2);
    }
}
