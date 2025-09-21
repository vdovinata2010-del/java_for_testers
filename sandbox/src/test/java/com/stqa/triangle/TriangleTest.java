package com.stqa.triangle;

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
}
