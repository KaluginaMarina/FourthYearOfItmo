import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.CALLS_REAL_METHODS

class FunctionsFirstLayerTest {
    companion object {
        const val EPSILON = 1E-10
        const val PERIOD = 2 * Math.PI
        var functions: Functions? = null

        @BeforeAll
        @JvmStatic
        fun createMock() {
            functions = Mockito.mock(Functions::class.java, CALLS_REAL_METHODS)
            // Значения около 0 и в эпсилон-окрестности от 0 + период
            Mockito.`when`(functions!!.f2(0.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(0.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(0.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(0.0 - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(0.0 - EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(0.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            // Значение на первой убывающей части отрезка + период
            Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - 100 * PERIOD)).thenReturn(-9.86725)
            // Значения около единицы и в эпмилон-окрестности + период
            Mockito.`when`(functions!!.f2(-1.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
        }

    }

    @Test
    fun testZeroValue() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0),
            Functions.PRECISION,
            "Layer 1: [х = 0]. Тестирование нулевого значения."
        )
    }


    @Test
    fun testZeroValueFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 + PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 + PERIOD]. Тестирование нулевого значения со сдвигом в период"
        )
    }

    @Test
    fun testZeroValueHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 + 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 + 100 * PERIOD]. Тестирование нулевого значения со сдвигом в 100 периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZero() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [х = 0-]. Тестирование граничного значения слева от нуля."
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 - EPSILON - PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в период влево"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 - EPSILON - 100 * PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в сто периодов влево"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValue() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5),
            Functions.PRECISION,
            "Layer 1: [х = -0.5]. Первый кусок функции."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueFirstPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -0.5 - PERIOD]. Первый кусок функции. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueHundredthPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -0.5 - PERIOD]. Первый кусок функции. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testValueOne() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0),
            Functions.PRECISION,
            "Layer 1: [x = -1]. Первый кусок функции. При неопределенном значении."
        )
    }

    @Test
    fun testValueOneFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0 + PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 + PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на один период."
        )
    }

    @Test
    fun testValueOneHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0 + 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 + 100 * PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на сто периодов."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -1 + EPS]. Первый кусок функции. При неопределенном значении. x -> 1+."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 + EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheRightIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 + EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testValueToTheLeftIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -1 - EPS]. Первый кусок функции. При неопределенном значении. x -> 1-."
        )
    }

    @Test
    fun testValueToTheLefttIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 - EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheLeftIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 - EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на сто периодов"
        )
    }
}