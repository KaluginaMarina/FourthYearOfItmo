import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.CALLS_REAL_METHODS
import kotlin.math.E
import kotlin.math.PI

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
            // Значение на первой убывающей части отрезка + период (до и после экстремума)
            Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-0.5 - 100 * PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(functions!!.f2(-1.3)).thenReturn(-6.80003)
            Mockito.`when`(functions!!.f2(-1.3 - PERIOD)).thenReturn(-6.80003)
            Mockito.`when`(functions!!.f2(-1.3 - 100 * PERIOD)).thenReturn(-6.80003)
            // Значения около единицы и в эпсилон-окрестности + период
            Mockito.`when`(functions!!.f2(-1.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(functions!!.f2(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            // Значения около pi/2 и в эпсилон-окрестности + период (граница двух частей функии + неопределенное значение)
            Mockito.`when`(functions!!.f2(-PI / 2)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2 + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2 + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2 - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2 - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            // Значения для значений второй негативой части + период (слева и справа от точки перегиба)
            Mockito.`when`(functions!!.f2(-1.7)).thenReturn(4.5428)
            Mockito.`when`(functions!!.f2(-1.7 - PERIOD)).thenReturn(4.5428)
            Mockito.`when`(functions!!.f2(-1.7 - 100 * PERIOD)).thenReturn(4.5428)
            Mockito.`when`(functions!!.f2(-2.1)).thenReturn(-3.62892)
            Mockito.`when`(functions!!.f2(-2.1 - PERIOD)).thenReturn(-3.62892)
            Mockito.`when`(functions!!.f2(-2.1 - 100 * PERIOD)).thenReturn(-3.62892)
            // Значения в точке пересечения с осью ординат второй негативой части + период
            Mockito.`when`(functions!!.f2(-1.843684653)).thenReturn(0.0)
            Mockito.`when`(functions!!.f2(-1.843684653 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(functions!!.f2(-1.843684653 - 100 * PERIOD)).thenReturn(0.0)
            // Значения в точке перегиба и в эпсилон-окрестности этой точки
            Mockito.`when`(functions!!.f2(-2.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-2.0 + EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(functions!!.f2(-2.0 + EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(functions!!.f2(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(functions!!.f2(-2.0 - EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(functions!!.f2(-2.0 - EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(functions!!.f2(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
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
            functions!!.systemOfFunctions(0.0 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 - PERIOD]. Тестирование нулевого значения со сдвигом в период"
        )
    }

    @Test
    fun testZeroValueHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [х = 0 - 100 * PERIOD]. Тестирование нулевого значения со сдвигом в 100 периодов"
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
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtreme() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5),
            Functions.PRECISION,
            "Layer 1: [х = -0.5]. Первый кусок функции справа от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeFirstPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -0.5 - PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeHundredthPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -0.5 - 100 * PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtreme() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3),
            Functions.PRECISION,
            "Layer 1: [х = -1.3]. Первый кусок функции слева от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtremeFirstPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.3 - PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLefttOfTheExtremeHundredthPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.3 - 100 * PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в сто периодов"
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
            functions!!.systemOfFunctions(-1.0 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 - PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на один период."
        )
    }

    @Test
    fun testValueOneHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1 - 100 * PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на сто периодов."
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

    @Test
    fun testIndefiniteValueInHalfPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2]. При неопределенном значении."
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 - PERIOD]. При неопределенном значении. Сдвиг на один период"
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 - 100 * PERIOD]. При неопределенном значении. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPi() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 + EPS]. Граничное значение справа от -PI/2. x->-pi/2+"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 + EPS - PERIOD]. Граничное значение справа от PI/2. x->pi/2+. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheRightOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 + EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2+. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPi() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 - EPS]. Граничное значение справа от -PI/2. x->-pi/2-"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiLeftPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 - EPS - PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI / 2 - EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPoint() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7),
            Functions.PRECISION,
            "Layer 1: [x = -1.7]. Вторая негативная часть справа от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointFirstPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.7 - PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.7 - 100 * PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPoint() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1),
            Functions.PRECISION,
            "Layer 1: [x = -2.1]. Вторая негативная часть слева от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointFirstPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2.1 - PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2.1 - 100 * PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePart() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653),
            Functions.PRECISION,
            "Layer 1: [x = -1.843684653 (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartFirstPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.843684653 - PERIOD (y = 0) ]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartHundredthPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -1.843684653 - 100 * PERIOD (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPart(){
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0),
            Functions.PRECISION,
            "Layer 1: [x = -2]. Значения в точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartFirstPeriod(){
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - PERIOD]. Значения в точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartHundredPeriod(){
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - 100 * PERIOD]. Значения в точке перегиба второй чати сдвиг в сто периодов"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPart(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS ]. Значения в окрестности справа к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartFirstPeriod(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON- PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS - PERIOD]. Значения в окрестности справа к точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartHundredPeriod(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS - 100 * PERIOD]. Значения в окрестности справа к точке перегиба второй чати в сто периодов"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPart(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS ]. Значения в окрестности слева к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartFirstPeriod(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON- PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS - PERIOD]. Значения в окрестности слева к точке перегиба второй чати сдвиг в один период"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartHundredPeriod(){
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS - 100 * PERIOD]. Значения в окрестности слева к точке перегиба второй чати в сто периодов"
        )
    }
}