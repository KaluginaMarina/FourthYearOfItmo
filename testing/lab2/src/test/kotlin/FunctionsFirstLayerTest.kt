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
            Mockito.`when`(functions!!.f2(0.0 - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - 100 * PERIOD)).thenReturn(-9.86725)

        }

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
            "Layer 1: [x = -0.5 - PERIOD]. Первый кусок функции. Проверка переодичности: первый период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueHundredthPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -0.5 - PERIOD]. Первый кусок функции. Проверка переодичности: сотый период"
        )
    }

}