import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class FunctionsFirstLayerTest {
    companion object {
        const val EPSILON = 1E-10
    }


//    @BeforeEach
//    fun createMock(){
//        functions = Mockito.mock(Functions::class.java)
//        Mockito.`when`(functions!!.f2(0.0 + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
//        Mockito.`when`(functions!!.f2(0.0 - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
//        Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
//    }

    @Test
    fun testSystemOfFunctionNegativeValue() {
        val functions = Mockito.mock(Functions::class.java)
        Mockito.`when`(functions.f2(-0.5)).thenReturn(-9.86725)
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5),
            Functions.PRECISION,
            "Layer 1: ошибка при тестировании "
        )
    }


}