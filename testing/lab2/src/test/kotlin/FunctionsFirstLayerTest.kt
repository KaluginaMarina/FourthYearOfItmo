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
            /**
             * Негативная часть функции имеет вид переодической функции, которая
             * состоит из 4-ех частей.
             * В каждой части есть граничные точки, экстремумы или точки перегиба
             *
             * Граничные точки представляют собой точки разрыва второго рода. В таких
             * точках невозможно посчитать значение функции и предел функции, в
             * окрестностях этих точек стремиться к +- бесконечности
             * (Примером может служить точка х = 0, х = pi/2)
             *
             * Помимо этих точек есть точки разрыва первого рода.
             * Функция в данных точках не определена, но в эа односоронних предела
             * в этой точке существуют и конечны
             * (Примером такой точки модет послужить точка х = 1)
             *
             *
             * Для тестирования были исследованы все точки разрыва первого и второго рода,
             * была проконтролированна соблюдаемость периодичности, и выбраны и протестированы
             * точки в каждом классе эквивалентности.
             */
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
            Mockito.`when`(functions!!.f2(-PI / 2.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
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
            // Граничное значение между второй и третьей частями и окрестности точки PI
            Mockito.`when`(functions!!.f2(-PI)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-PI - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            // Третья часть справа и слева от экстремума + париод
            Mockito.`when`(functions!!.f2(-3.5)).thenReturn(25.9773)
            Mockito.`when`(functions!!.f2(-3.5 - 100 * PERIOD)).thenReturn(25.9773)
            Mockito.`when`(functions!!.f2(-4.3)).thenReturn(3.78801)
            Mockito.`when`(functions!!.f2(-4.3 - 100 * PERIOD)).thenReturn(3.78801)
            // Экстремум в третьей части и эпсилон-окрестности точки перегиба
            Mockito.`when`(functions!!.f2(-4.0)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-4.0 + EPSILON)).thenReturn(3.259463457)
            Mockito.`when`(functions!!.f2(-4.0 - EPSILON)).thenReturn(3.259463457)
            // Граничные точки между третим и четвертым отрезком
            Mockito.`when`(functions!!.f2(-1.5 * PI)).thenReturn(Double.NaN)
            Mockito.`when`(functions!!.f2(-1.5 * PI + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(functions!!.f2(-1.5 * PI - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            // Четвертая часть: справа и слева от экстремума + период
            Mockito.`when`(functions!!.f2(-5.1)).thenReturn(-2.1949)
            Mockito.`when`(functions!!.f2(-5.1 - 100 * PERIOD)).thenReturn(-2.1949)
            Mockito.`when`(functions!!.f2(-5.9)).thenReturn(-5.47627)
            Mockito.`when`(functions!!.f2(-5.9 - 100 * PERIOD)).thenReturn(-5.47627)
            // Граничная точка
            Mockito.`when`(functions!!.f2(-2.0 * PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
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
    fun testValuesAtTheInflectionPointOfTheSecondPart() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0),
            Functions.PRECISION,
            "Layer 1: [x = -2]. Значения в точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - PERIOD]. Значения в точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - 100 * PERIOD]. Значения в точке перегиба второй чати сдвиг в сто периодов"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS ]. Значения в окрестности справа к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS - PERIOD]. Значения в окрестности справа к точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 + EPS - 100 * PERIOD]. Значения в окрестности справа к точке перегиба второй чати в сто периодов"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS ]. Значения в окрестности слева к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS - PERIOD]. Значения в окрестности слева к точке перегиба второй чати сдвиг в один период"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -2 - EPS - 100 * PERIOD]. Значения в окрестности слева к точке перегиба второй чати в сто периодов"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPoint() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI),
            Functions.PRECISION,
            "Layer 1: [x = -PI].  Граничное значение между второй и третьей частями и окрестности точки PI."
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI- PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на один период"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI - 100 * PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePI() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -PI + EPS].  Граничное значение между второй и третьей частями и справа от  точки PI . x->-pi+"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI + EPS - PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI . Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI + EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePI() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -PI - EPS].  Граничное значение между второй и третьей частями и слева от  точки PI  x->-pi-"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIFirstPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON - PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI  - EPS - PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIHundredthPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -PI - EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremum() {
        assertEquals(
            25.9773,
            functions!!.systemOfFunctions(-3.5),
            Functions.PRECISION,
            "Layer 1: [x = -3.5].  Третья часть справа от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremumWithPeriod() {
        assertEquals(
            25.9773,
            functions!!.systemOfFunctions(-3.5 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -3.5 - 100 * PERIOD].  Третья часть справа от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremum() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3),
            Functions.PRECISION,
            "Layer 1: [x = -4.3].  Третья часть слева от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremumWithPeriod() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -4.3 - 100 * PERIOD].  Третья часть слева от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testExtremumInTheThirdPart(){
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-4.0),
            Functions.PRECISION,
            "Layer 1: [x = -4.0].  Экстремум в третьей части "
        )
    }

    @Test
    fun testNeighborhoodToTheRightOfExtremumInTheThirdPart(){
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -4.0 + EPS].  Окрестность справа от экстремума в третьей части"
        )
    }

    @Test
    fun testNeighborhoodToTheLeftOfExtremumInTheThirdPart(){
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -4.0 - EPS].  Окрестность слева от экстремума в третьей части"
        )
    }

    @Test
    fun testBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.5 * PI),
            Functions.PRECISION,
            "Layer 1: [x = -1/5 * PI].  Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testRightNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-1.5 * PI + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -1/5 * PI + EPS]. Окрестность справа Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testLeftNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-1.5 * PI - EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -1/5 * PI - EPS]. Окрестность слева Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremum(){
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1),
            Functions.PRECISION,
            "Layer 1: [x = -5.1]. Четвертая часть справа от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremumWithPeriod(){
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -5.1 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremum(){
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9),
            Functions.PRECISION,
            "Layer 1: [x = -5.9]. Четвертая часть слева от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremumWithPeriod(){
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9 - 100 * PERIOD),
            Functions.PRECISION,
            "Layer 1: [x = -5.9 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheBorderPointOfTheFourthPartIsAboutTwoPi(){
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-2.0 * PI + EPSILON),
            Functions.PRECISION,
            "Layer 1: [x = -2.0 * PI + EPSILON]. Граничная точка четвертой части около 2PI"
        )
    }
}