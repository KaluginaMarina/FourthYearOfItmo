import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.CALLS_REAL_METHODS
import kotlin.math.E
import kotlin.math.PI

class FunctionsFourthLayerTest {
    companion object {
        const val EPSILON = 1E-10
        const val PERIOD = 2 * Math.PI
        const val PRECISION = 1E-4 // точность, с которой выдает ответ вольфрам для сложных функций
        var functions: Functions? = null

        @BeforeAll
        @JvmStatic
        fun createMock() {
            val sf = Mockito.mock(SimpleFunctions::class.java, CALLS_REAL_METHODS)
            functions = Functions(sf)

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
            // Mockito.`when`(functions!!.f2(0.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - 100 * PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - EPSILON)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - EPSILON - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - EPSILON - 100 * PERIOD)).thenReturn(0.0)

            // Значение на первой убывающей части отрезка + период (до и после экстремума)
            // Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
            Mockito.`when`(sf.sin(-0.5)).thenReturn(-0.4794255386042)

            // Mockito.`when`(functions!!.f2(-0.5 - PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(sf.sin(-0.5 - PERIOD)).thenReturn(-0.4794255386042)

            // Mockito.`when`(functions!!.f2(-0.5 - 100 * PERIOD)).thenReturn(-9.86725)

            Mockito.`when`(sf.sin(-0.5 - 100 * PERIOD)).thenReturn(-0.4794255386042)

            // Mockito.`when`(functions!!.f2(-1.3)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3)).thenReturn(-0.963558185417)

            // Mockito.`when`(functions!!.f2(-1.3 - PERIOD)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3 - PERIOD)).thenReturn(-0.963558185417)

            // Mockito.`when`(functions!!.f2(-1.3 - 100 * PERIOD)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3 - 100 * PERIOD)).thenReturn(-0.963558185417)

            // Значения около единицы и в эпсилон-окрестности + период
            // Mockito.`when`(functions!!.f2(-1.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0)).thenReturn(-0.8414709848078965)

            //Mockito.`when`(functions!!.f2(-1.0 - EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON)).thenReturn(-0.841470985348199)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON)).thenReturn(-0.841470984267594)

            // Mockito.`when`(functions!!.f2(-1.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0 - PERIOD)).thenReturn(-0.8414709848078965)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON - PERIOD)).thenReturn(-0.841470984267594)

            // Mockito.`when`(functions!!.f2(-1.0 - EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON - PERIOD)).thenReturn(-0.841470985348199)

            // Mockito.`when`(functions!!.f2(-1.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0 - 100 * PERIOD)).thenReturn(-0.8414709848078965)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.841470984267594)

            // Mockito.`when`(functions!!.f2(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.841470985348199)

            // Значения около pi/2 и в эпсилон-окрестности + период (граница двух частей функии + неопределенное значение)
            // Mockito.`when`(functions!!.f2(-PI / 2.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - 100 * PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(-1.0)

            // Значения для значений второй негативой части + период (слева и справа от точки перегиба)
            // Mockito.`when`(functions!!.f2(-1.7)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7)).thenReturn(-0.991664810452)

            // Mockito.`when`(functions!!.f2(-1.7 - PERIOD)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7 - PERIOD)).thenReturn(-0.991664810452)

            // Mockito.`when`(functions!!.f2(-1.7 - 100 * PERIOD)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7 - 100 * PERIOD)).thenReturn(-0.991664810452)

            // Mockito.`when`(functions!!.f2(-2.1)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1)).thenReturn(-0.8632093666)

            // Mockito.`when`(functions!!.f2(-2.1 - PERIOD)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1 - PERIOD)).thenReturn(-0.8632093666)

            //  Mockito.`when`(functions!!.f2(-2.1 - 100 * PERIOD)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1 - 100 * PERIOD)).thenReturn(-0.8632093666)

            // Значения в точке пересечения с осью ординат второй негативой части + период
            // Mockito.`when`(functions!!.f2(-1.843684653)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653)).thenReturn(-0.962996469946765)

            // Mockito.`when`(functions!!.f2(-1.843684653 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653 - PERIOD)).thenReturn(-0.962996469946765)

            // Mockito.`when`(functions!!.f2(-1.843684653 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653 - 100 * PERIOD)).thenReturn(-0.962996469946765)

            // Значения в точке перегиба и в эпсилон-окрестности этой точки
            // Mockito.`when`(functions!!.f2(-2.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0)).thenReturn(-0.90929742682568)

            // Mockito.`when`(functions!!.f2(-2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0 - PERIOD)).thenReturn(-0.90929742682568)

            // Mockito.`when`(functions!!.f2(-2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0 - 100 * PERIOD)).thenReturn(-0.90929742682568)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON)).thenReturn(-0.909297426867296)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON - PERIOD)).thenReturn(-0.909297426867296)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.909297426867296)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON)).thenReturn(-0.9092974267840670)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON - PERIOD)).thenReturn(-0.9092974267840670)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.9092974267840670)

            // Граничное значение между второй и третьей частями и окрестности точки PI
            // Mockito.`when`(functions!!.f2(-PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - 100 * PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI + EPSILON)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI + EPSILON - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI + EPSILON - 100 * PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - EPSILON)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - EPSILON - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - EPSILON - 100 * PERIOD)).thenReturn(0.0)

            // Третья часть справа и слева от экстремума + париод
            // Mockito.`when`(functions!!.f2(-3.5)).thenReturn(25.97728)
            Mockito.`when`(sf.sin(-3.5)).thenReturn(0.3507832276896)

            // Mockito.`when`(functions!!.f2(-3.5 - 100 * PERIOD)).thenReturn(25.97728)
            Mockito.`when`(sf.sin(-3.5 - 100 * PERIOD)).thenReturn(0.3507832276896)

            // Mockito.`when`(functions!!.f2(-4.3)).thenReturn(3.78801)
            Mockito.`when`(sf.sin(-4.3)).thenReturn(0.91616593674945498)

            // Mockito.`when`(functions!!.f2(-4.3 - 100 * PERIOD)).thenReturn(3.78801)
            Mockito.`when`(sf.sin(-4.3 - 100 * PERIOD)).thenReturn(0.91616593674945498)

            // Экстремум в третьей части и эпсилон-окрестности точки перегиба
            // Mockito.`when`(functions!!.f2(-4.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-4.0)).thenReturn(0.7568024953079)

            // Mockito.`when`(functions!!.f2(-4.0 + EPSILON)).thenReturn(3.259463457)
            Mockito.`when`(sf.sin(-4.0 + EPSILON)).thenReturn(0.75680249465428463)

            // Mockito.`when`(functions!!.f2(-4.0 - EPSILON)).thenReturn(3.259463457)
            Mockito.`when`(sf.sin(-4.0 - EPSILON)).thenReturn(0.75680249596157187)

            // Граничные точки между третим и четвертым отрезком
            // Mockito.`when`(functions!!.f2(-1.5 * PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.5 * PI)).thenReturn(1.0)

            // Mockito.`when`(functions!!.f2(-1.5 * PI + EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.5 * PI + EPSILON)).thenReturn(1.0)

            // Mockito.`when`(functions!!.f2(-1.5 * PI - EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.5 * PI - EPSILON)).thenReturn(1.0)

            // Четвертая часть: справа и слева от экстремума + период
            // Mockito.`when`(functions!!.f2(-5.1)).thenReturn(-2.1949)
            Mockito.`when`(sf.sin(-5.1)).thenReturn(0.925814682327732)

            //  Mockito.`when`(functions!!.f2(-5.1 - 100 * PERIOD)).thenReturn(-2.1949)
            Mockito.`when`(sf.sin(-5.1 - 100 * PERIOD)).thenReturn(0.925814682327732)

            // Mockito.`when`(functions!!.f2(-5.9)).thenReturn(-5.47627)
            Mockito.`when`(sf.sin(-5.9)).thenReturn(0.373876664830236)

            //  Mockito.`when`(functions!!.f2(-5.9 - 100 * PERIOD)).thenReturn(-5.47627)
            Mockito.`when`(sf.sin(-5.9 - 100 * PERIOD)).thenReturn(0.373876664830236)

            // Граничная точка
            // Mockito.`when`(functions!!.f2(-2.0 * PI + EPSILON)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0 * PI + EPSILON)).thenReturn(0.0)


            /**
             * Положительная часть - функция, которая пересекает прямую ох 3 раза
             * В точке x = 0 стремиться к бесконечности
             */

            // Функции для перевода логарифмов
            Mockito.`when`(sf.ln(2.0)).thenReturn(0.6931471805599)
            Mockito.`when`(sf.ln(3.0)).thenReturn(1.098612288668)
            Mockito.`when`(sf.ln(5.0)).thenReturn(1.6094379124341)
            Mockito.`when`(sf.ln(10.0)).thenReturn(2.302585092994)


            // Отрезок между 0 и пересечением о осью ох
            // Mockito.`when`(functions!!.f1(0.01)).thenReturn(275.519)
            Mockito.`when`(sf.ln(0.01)).thenReturn(-4.605170185988)

            // Первое пересечение с осью ох
            // Mockito.`when`(functions!!.f1(0.0159975)).thenReturn(0.0)
            Mockito.`when`(sf.ln(0.0159975)).thenReturn(-4.135322818950658779)

            // Значение функции на убывающем отрезке между первым и вторым пересечением ох
            // Mockito.`when`(functions!!.f1(0.02)).thenReturn(-61.70484)
            Mockito.`when`(sf.ln(0.02)).thenReturn(-3.912023005428146)

            // Экстремум
            // Mockito.`when`(functions!!.f1(0.0333409)).thenReturn(-106.26636)
            Mockito.`when`(sf.ln(0.0333409)).thenReturn(-3.40097040742275701)

            // Mockito.`when`(functions!!.f1(0.0333409)).thenReturn(1.55602)
            Mockito.`when`(sf.ln(0.0333409)).thenReturn(-0.738967060394736)
            
            // Значение функции на возрастающем отрезке между первым и вторым пересечением ох
            // Mockito.`when`(functions!!.f1(0.05)).thenReturn(-91.44142)
            Mockito.`when`(sf.ln(0.05)).thenReturn(-2.99573227355399)

            // Второе пересечение с ox
            // Mockito.`when`(functions!!.f1(0.321061)).thenReturn(0.0)
            Mockito.`when`(sf.ln(0.321061)).thenReturn(-1.1361241427531)

            // Третье пересечение с ох
            // Mockito.`when`(functions!!.f1(65.5183)).thenReturn(0.0)
            Mockito.`when`(sf.ln(65.5183)).thenReturn(4.182329492932)

        }
    }

    @Test
    fun testTheSegmentBetweenZeroAndTheIntersectionOfTheOX() {
        assertEquals(
            275.519,
            functions!!.systemOfFunctions(0.01),
            PRECISION,
            "Layer 3: [F1] (х = 0.01). Отрезок между 0 и пересечением о осью ох."
        )
    }

    @Test
    fun testFirstIntersectionWithTheOX() {
        assertEquals(
            0.0007,
            functions!!.systemOfFunctions(0.0159975),
            PRECISION,
            "Layer 3: [F1] (х = 0.0159975).Первое пересечение с осью ох"
        )
    }

    @Test
    fun testTheValueOfTheFunctionOnTheDecreasingSegmentBetweenTheFirstAndSecondIntersectionOX() {
        assertEquals(
            -61.70484,
            functions!!.systemOfFunctions(0.02),
            PRECISION,
            "Layer 3: [F1] (х = 0.02). Значение функции на убывающем отрезке между первым и вторым пересечением ох"
        )
    }

    @Test
    fun testFirstExtreme() {
        assertEquals(
            -106.26636,
            functions!!.systemOfFunctions(0.0333409),
            PRECISION,
            "Layer 1: [F1] (х = 0.0333409). Проверка функции в экстремуме"
        )
    }

    @Test
    fun testSecondExtreme() {
        assertEquals(
            1.55602,
            functions!!.systemOfFunctions(0.477607),
            PRECISION,
            "Layer 1: [F1] (х = 0.0333409). Проверка функции во втором экстремуме"
        )
    }

    @Test
    fun testTheValueOfTheFunctionOnTheIncreasingSegmentBetweenTheFirstAndSecondIntersectionOX() {
        assertEquals(
            -91.44142,
            functions!!.systemOfFunctions(0.05),
            PRECISION,
            "Layer 3: [F1] (х = 0.02).Значение функции на возрастающем отрезке между первым и вторым пересечением ох"
        )
    }

    @Test
    fun testSecondIntersectionWithTheOX() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(0.321061),
            PRECISION,
            "Layer 3: [F1] (х = 0.321061). Второе пересечение с осью ох"
        )
    }

    @Test
    fun testThirdIntersectionWithTheOX() {
        assertEquals(
            -0.00013,
            functions!!.systemOfFunctions(65.5183),
            PRECISION,
            "Layer 3: [F1] (х = 65.5183). Третье пересечение с осью ох"
        )
    }

    @Test
    fun testZeroValue() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0),
            PRECISION,
            "Layer 3: F1 [x = 0]. Тестирование нулевого значения."
        )
    }


    @Test
    fun testZeroValueFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = 0 - PERIOD]. Тестирование нулевого значения со сдвигом в период"
        )
    }

    @Test
    fun testZeroValueHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = 0 - 100 * PERIOD]. Тестирование нулевого значения со сдвигом в 100 периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZero() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0 - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = 0-]. Тестирование граничного значения слева от нуля."
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = 0 - EPSILON - PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в период влево"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = 0 - EPSILON - 100 * PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в сто периодов влево"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtreme() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5),
            PRECISION,
            "Layer 3: F1 [x = -0.5]. Первый кусок функции справа от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeFirstPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -0.5 - PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeHundredthPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -0.5 - 100 * PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtreme() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3),
            PRECISION,
            "Layer 3: F1 [x = -1.3]. Первый кусок функции слева от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtremeFirstPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.3 - PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLefttOfTheExtremeHundredthPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.3 - 100 * PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testValueOne() {
        assertEquals(
            -5.704479,
            functions!!.systemOfFunctions(-1.0),
            PRECISION,
            "Layer 3: F1 [x = -1]. Первый кусок функции. При неопределенном значении."
        )
    }

    @Test
    fun testValueOneFirstPeriod() {
        assertEquals(
            -5.704479,
            functions!!.systemOfFunctions(-1.0 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 - PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на один период."
        )
    }

    @Test
    fun testValueOneHundredthPeriod() {
        assertEquals(
            -5.70448,
            functions!!.systemOfFunctions(-1.0 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 - 100 * PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на сто периодов."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -1 + EPS]. Первый кусок функции. При неопределенном значении. x -> 1+."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 + EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheRightIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 + EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testValueToTheLeftIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -1 - EPS]. Первый кусок функции. При неопределенном значении. x -> 1-."
        )
    }

    @Test
    fun testValueToTheLefttIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 - EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheLeftIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1 - EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testIndefiniteValueInHalfPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2]. При неопределенном значении."
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 - PERIOD]. При неопределенном значении. Сдвиг на один период"
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 - 100 * PERIOD]. При неопределенном значении. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 + EPS]. Граничное значение справа от -PI/2. x->-pi/2+"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 + EPS - PERIOD]. Граничное значение справа от PI/2. x->pi/2+. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheRightOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 + EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2+. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 - EPS]. Граничное значение справа от -PI/2. x->-pi/2-"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiLeftPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 - EPS - PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI / 2 - EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPoint() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7),
            PRECISION,
            "Layer 3: F1 [x = -1.7]. Вторая негативная часть справа от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointFirstPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.7 - PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.7 - 100 * PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPoint() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1),
            PRECISION,
            "Layer 3: F1 [x = -2.1]. Вторая негативная часть слева от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointFirstPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2.1 - PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2.1 - 100 * PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePart() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653),
            PRECISION,
            "Layer 3: F1 [x = -1.843684653 (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartFirstPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.843684653 - PERIOD (y = 0) ]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartHundredthPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -1.843684653 - 100 * PERIOD (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.2596,
            functions!!.systemOfFunctions(-2.0),
            PRECISION,
            "Layer 3: F1 [x = -2]. Значения в точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.2596,
            functions!!.systemOfFunctions(-2.0 - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 - PERIOD]. Значения в точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 - 100 * PERIOD]. Значения в точке перегиба второй чати сдвиг в сто периодов"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -2 + EPS ]. Значения в окрестности справа к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 + EPS - PERIOD]. Значения в окрестности справа к точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 + EPS - 100 * PERIOD]. Значения в окрестности справа к точке перегиба второй чати в сто периодов"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -2 - EPS ]. Значения в окрестности слева к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 - EPS - PERIOD]. Значения в окрестности слева к точке перегиба второй чати сдвиг в один период"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -2 - EPS - 100 * PERIOD]. Значения в окрестности слева к точке перегиба второй чати в сто периодов"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPoint() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI),
            PRECISION,
            "Layer 3: F1 [x = -PI].  Граничное значение между второй и третьей частями и окрестности точки PI."
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI- PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на один период"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI - 100 * PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePI() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -PI + EPS].  Граничное значение между второй и третьей частями и справа от  точки PI . x->-pi+"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI + EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI + EPS - PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI . Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI + EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePI() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -PI - EPS].  Граничное значение между второй и третьей частями и слева от  точки PI  x->-pi-"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - EPSILON - PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI  - EPS - PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -PI - EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremum() {
        assertEquals(
            25.9773,
            functions!!.systemOfFunctions(-3.5),
            PRECISION,
            "Layer 3: F1 [x = -3.5].  Третья часть справа от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremumWithPeriod() {
        assertEquals(
            25.97728,
            functions!!.systemOfFunctions(-3.5 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -3.5 - 100 * PERIOD].  Третья часть справа от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremum() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3),
            PRECISION,
            "Layer 3: F1 [x = -4.3].  Третья часть слева от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremumWithPeriod() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -4.3 - 100 * PERIOD].  Третья часть слева от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testExtremumInTheThirdPart() {
        assertEquals(
            3.259463,
            functions!!.systemOfFunctions(-4.0),
            PRECISION,
            "Layer 3: F1 [x = -4.0].  Экстремум в третьей части "
        )
    }

    @Test
    fun testNeighborhoodToTheRightOfExtremumInTheThirdPart() {
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -4.0 + EPS].  Окрестность справа от экстремума в третьей части"
        )
    }

    @Test
    fun testNeighborhoodToTheLeftOfExtremumInTheThirdPart() {
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -4.0 - EPS].  Окрестность слева от экстремума в третьей части"
        )
    }

    @Test
    fun testBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.5 * PI),
            PRECISION,
            "Layer 3: F1 [x = -1/5 * PI].  Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testRightNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.5 * PI + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -1/5 * PI + EPS]. Окрестность справа Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testLeftNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.5 * PI - EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -1/5 * PI - EPS]. Окрестность слева Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremum() {
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1),
            PRECISION,
            "Layer 3: F1 [x = -5.1]. Четвертая часть справа от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremumWithPeriod() {
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -5.1 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremum() {
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9),
            PRECISION,
            "Layer 3: F1 [x = -5.9]. Четвертая часть слева от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremumWithPeriod() {
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9 - 100 * PERIOD),
            PRECISION,
            "Layer 3: F1 [x = -5.9 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheBorderPointOfTheFourthPartIsAboutTwoPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 * PI + EPSILON),
            PRECISION,
            "Layer 3: F1 [x = -2.0 * PI + EPSILON]. Граничная точка четвертой части около 2PI"
        )
    }
}