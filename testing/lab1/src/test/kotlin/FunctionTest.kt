import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import kotlin.math.asin

class FunctionTest {
    @Test
    fun testRightBorder(){
        assertEquals(asin(1.0), Function.arcsin(1.0), "Правая граница не верна")
    }

    @Test
    fun testLeftBourder(){
        assertEquals(asin(-1.0), Function.arcsin(-1.0), "Левая граница не верна")
    }

    @Test
    fun testYIntercept(){
        assertEquals(asin(0.0), Function.arcsin(0.0), "Пересечение с осью ордина не верно")
    }

    @Test
    fun testLeftPartOfThePositiveSegment(){
        assertEquals(asin(0.33), Function.arcsin(0.33), "Левая часть положительного отрезка не верна")
    }

    @Test
    fun testRightPartOfThePositiveSegment() {
        assertEquals(asin(0.67), Function.arcsin(0.67), "Правая часть положительного отрезка не верна")
    }

    @Test
    fun testLeftPartOfTheNegativeSegment() {
        assertEquals(asin(-0.67), Function.arcsin(-0.67), "Последняя часть положительного отрезка не верна")
    }

    @Test
    fun testRightPartOfTheNegativeSegment() {
        assertEquals(asin(-0.33), Function.arcsin(-0.33), "Последняя часть положительного отрезка не верна")
    }

    @Test
    fun testOutOfBoundsOnTheRight(){
        assertNull(Function.arcsin(1.5))
    }

    @Test
    fun testOutOfBoundsOnTheLeft(){
        assertNull(Function.arcsin(-1.5))
    }
}