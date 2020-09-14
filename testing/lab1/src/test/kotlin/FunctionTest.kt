import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import kotlin.math.asin

class FunctionTest {
    @Test
    fun testRightBorder(){
        assertEquals(asin(1.0), Function.arcsin(1.0)!!, Function.PRECISION, "Правая граница не верна. Тест: asin(1.0)")
    }

    @Test
    fun testLeftBourder(){
        assertEquals(asin(-1.0), Function.arcsin(-1.0)!!, Function.PRECISION, "Левая граница не верна. Тест: asin(-1.0)")
    }

    @Test
    fun testYIntercept(){
        assertEquals(asin(0.0), Function.arcsin(0.0)!!, Function.PRECISION, "Пересечение с осью ордина не верно. Тест: asin(0.0)")
    }

    @Test
    fun testLeftPartOfThePositiveSegment(){
        assertEquals(asin(0.33), Function.arcsin(0.33)!!,  Function.PRECISION,"Левая часть положительного отрезка не верна. Тест: asin(0.33)")
    }

    @Test
    fun testRightPartOfThePositiveSegment() {
        assertEquals(asin(0.67), Function.arcsin(0.67)!!, Function.PRECISION, "Правая часть положительного отрезка не верна. Тест: asin(0.67)")
    }

    @Test
    fun testLeftPartOfTheNegativeSegment() {
        assertEquals(asin(-0.67), Function.arcsin(-0.67)!!, Function.PRECISION, "Последняя часть положительного отрезка не верна. Тест: asin(-0.67)")
    }

    @Test
    fun testRightPartOfTheNegativeSegment() {
        assertEquals(asin(-0.33), Function.arcsin(-0.33)!!, Function.PRECISION, "Последняя часть положительного отрезка не верна. Тест: asin(-0.33)")
    }

    @Test
    fun testOutOfBoundsOnTheRight(){
        assertNull(Function.arcsin(1.5), "Тест на выход за границы справа.")
    }

    @Test
    fun testOutOfBoundsOnTheLeft(){
        assertNull(Function.arcsin(-1.5), "Тест на выход за границы слева.")
    }
}