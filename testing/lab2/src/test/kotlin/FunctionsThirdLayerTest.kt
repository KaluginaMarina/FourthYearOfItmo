import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.CALLS_REAL_METHODS
import kotlin.math.E
import kotlin.math.PI

class FunctionsThirdLayerTest {
    companion object {
        const val EPSILON = 1E-10
        const val PERIOD = 2 * Math.PI
        const val PRECISION = 1E-4 // точность, с которой выдает ответ вольфрам для сложных функций
        var functions: Functions? = null

        @BeforeAll
        @JvmStatic
        fun createMock() {
            val sf = Mockito.mock(SimpleFunctions::class.java)
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
            Mockito.`when`(sf.cos(0.0)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(0.0)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(0.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(0.0 - PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(0.0 - PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0 - PERIOD)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(0.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(0.0 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(0.0 - 100 * PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(0.0 - 100 * PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0 - 100 * PERIOD)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(0.0 - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cos(0.0 - EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0 - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0 - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.sec(0.0 - EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0 - EPSILON)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(0.0 - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(0.0 - EPSILON - PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0 - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0 - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(0.0 - EPSILON - PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0 - EPSILON - PERIOD)).thenReturn(0.0)

            // Mockito.`when`(functions!!.f2(0.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(0.0 - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(0.0 - EPSILON - 100 * PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.tan(0.0 - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(0.0 - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(0.0 - EPSILON - 100 * PERIOD)).thenReturn(1.0)
            Mockito.`when`(sf.csc(0.0 - EPSILON - 100 * PERIOD)).thenReturn(0.0)

            // Значение на первой убывающей части отрезка + период (до и после экстремума)
            // Mockito.`when`(functions!!.f2(-0.5)).thenReturn(-9.86725)
            Mockito.`when`(sf.sin(-0.5)).thenReturn(-0.4794255386042)
            Mockito.`when`(sf.cos(-0.5)).thenReturn(0.87758256189037)
            Mockito.`when`(sf.tan(-0.5)).thenReturn(-0.54630248984379)
            Mockito.`when`(sf.cot(-0.5)).thenReturn(-1.83048772171245)
            Mockito.`when`(sf.sec(-0.5)).thenReturn(1.1394939273245)
            Mockito.`when`(sf.csc(-0.5)).thenReturn(-2.08582964293)

            // Mockito.`when`(functions!!.f2(-0.5 - PERIOD)).thenReturn(-9.86725)
            Mockito.`when`(sf.sin(-0.5 - PERIOD)).thenReturn(-0.4794255386042)
            Mockito.`when`(sf.cos(-0.5 - PERIOD)).thenReturn(0.87758256189037)
            Mockito.`when`(sf.tan(-0.5 - PERIOD)).thenReturn(-0.54630248984379)
            Mockito.`when`(sf.cot(-0.5 - PERIOD)).thenReturn(-1.83048772171245)
            Mockito.`when`(sf.sec(-0.5 - PERIOD)).thenReturn(1.1394939273245)
            Mockito.`when`(sf.csc(-0.5 - PERIOD)).thenReturn(-2.08582964293)

            // Mockito.`when`(functions!!.f2(-0.5 - 100 * PERIOD)).thenReturn(-9.86725)

            Mockito.`when`(sf.sin(-0.5 - 100 * PERIOD)).thenReturn(-0.4794255386042)
            Mockito.`when`(sf.cos(-0.5 - 100 * PERIOD)).thenReturn(0.87758256189037)
            Mockito.`when`(sf.tan(-0.5 - 100 * PERIOD)).thenReturn(-0.54630248984379)
            Mockito.`when`(sf.cot(-0.5 - 100 * PERIOD)).thenReturn(-1.83048772171245)
            Mockito.`when`(sf.sec(-0.5 - 100 * PERIOD)).thenReturn(1.1394939273245)
            Mockito.`when`(sf.csc(-0.5 - 100 * PERIOD)).thenReturn(-2.08582964293)

            // Mockito.`when`(functions!!.f2(-1.3)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3)).thenReturn(-0.963558185417)
            Mockito.`when`(sf.cos(-1.3)).thenReturn(0.267498828624587)
            Mockito.`when`(sf.tan(-1.3)).thenReturn(-3.602102447967978)
            Mockito.`when`(sf.cot(-1.3)).thenReturn(-0.2776156465411)
            Mockito.`when`(sf.sec(-1.3)).thenReturn(3.73833412707544)
            Mockito.`when`(sf.csc(-1.3)).thenReturn(-1.03782004567)

            // Mockito.`when`(functions!!.f2(-1.3 - PERIOD)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3 - PERIOD)).thenReturn(-0.963558185417)
            Mockito.`when`(sf.cos(-1.3 - PERIOD)).thenReturn(0.267498828624587)
            Mockito.`when`(sf.tan(-1.3 - PERIOD)).thenReturn(-3.602102447967978)
            Mockito.`when`(sf.cot(-1.3 - PERIOD)).thenReturn(-0.2776156465411)
            Mockito.`when`(sf.sec(-1.3 - PERIOD)).thenReturn(3.73833412707544)
            Mockito.`when`(sf.csc(-1.3 - PERIOD)).thenReturn(-1.03782004567)

            // Mockito.`when`(functions!!.f2(-1.3 - 100 * PERIOD)).thenReturn(-6.80003)
            Mockito.`when`(sf.sin(-1.3 - 100 * PERIOD)).thenReturn(-0.963558185417)
            Mockito.`when`(sf.cos(-1.3 - 100 * PERIOD)).thenReturn(0.267498828624587)
            Mockito.`when`(sf.tan(-1.3 - 100 * PERIOD)).thenReturn(-3.602102447967978)
            Mockito.`when`(sf.cot(-1.3 - 100 * PERIOD)).thenReturn(-0.2776156465411)
            Mockito.`when`(sf.sec(-1.3 - 100 * PERIOD)).thenReturn(3.73833412707544)
            Mockito.`when`(sf.csc(-1.3 - 100 * PERIOD)).thenReturn(-1.03782004567)

            // Значения около единицы и в эпсилон-окрестности + период
            // Mockito.`when`(functions!!.f2(-1.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0)).thenReturn(-0.8414709848078965)
            Mockito.`when`(sf.cos(-1.0)).thenReturn(0.540302305868)
            Mockito.`when`(sf.tan(-1.0)).thenReturn(-1.55740772465)
            Mockito.`when`(sf.cot(-1.0)).thenReturn(-0.6420926159)
            Mockito.`when`(sf.sec(-1.0)).thenReturn(1.85081571768)
            Mockito.`when`(sf.csc(-1.0)).thenReturn(-1.188395105778)

            //Mockito.`when`(functions!!.f2(-1.0 - EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON)).thenReturn(-0.841470985348199)
            Mockito.`when`(sf.cos(-1.0 - EPSILON)).thenReturn(0.540302305026669)
            Mockito.`when`(sf.tan(-1.0 - EPSILON)).thenReturn(-1.55740772808042)
            Mockito.`when`(sf.cot(-1.0 - EPSILON)).thenReturn(-0.642092614522048)
            Mockito.`when`(sf.sec(-1.0 - EPSILON)).thenReturn(1.85081572056340)
            Mockito.`when`(sf.csc(-1.0 - EPSILON)).thenReturn(-1.18839510501506)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON)).thenReturn(-0.841470984267594)
            Mockito.`when`(sf.cos(-1.0 + EPSILON)).thenReturn(0.540302306709611)
            Mockito.`when`(sf.tan(-1.0 + EPSILON)).thenReturn(-1.55740772122938)
            Mockito.`when`(sf.cot(-1.0 + EPSILON)).thenReturn(-0.642092617346614)
            Mockito.`when`(sf.sec(-1.0 + EPSILON)).thenReturn(1.85081571479845)
            Mockito.`when`(sf.csc(-1.0 + EPSILON)).thenReturn(-1.18839510654118)

            // Mockito.`when`(functions!!.f2(-1.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0 - PERIOD)).thenReturn(-0.8414709848078965)
            Mockito.`when`(sf.cos(-1.0 - PERIOD)).thenReturn(0.540302305868)
            Mockito.`when`(sf.tan(-1.0 - PERIOD)).thenReturn(-1.55740772465)
            Mockito.`when`(sf.cot(-1.0 - PERIOD)).thenReturn(-0.6420926159)
            Mockito.`when`(sf.sec(-1.0 - PERIOD)).thenReturn(1.85081571768)
            Mockito.`when`(sf.csc(-1.0 - PERIOD)).thenReturn(-1.188395105778)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON - PERIOD)).thenReturn(-0.841470984267594)
            Mockito.`when`(sf.cos(-1.0 + EPSILON - PERIOD)).thenReturn(0.540302306709611)
            Mockito.`when`(sf.tan(-1.0 + EPSILON - PERIOD)).thenReturn(-1.55740772122938)
            Mockito.`when`(sf.cot(-1.0 + EPSILON - PERIOD)).thenReturn(-0.642092617346614)
            Mockito.`when`(sf.sec(-1.0 + EPSILON - PERIOD)).thenReturn(1.85081571479845)
            Mockito.`when`(sf.csc(-1.0 + EPSILON - PERIOD)).thenReturn(-1.18839510654118)

            // Mockito.`when`(functions!!.f2(-1.0 - EPSILON - PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON - PERIOD)).thenReturn(-0.841470985348199)
            Mockito.`when`(sf.cos(-1.0 - EPSILON - PERIOD)).thenReturn(0.540302305026669)
            Mockito.`when`(sf.tan(-1.0 - EPSILON - PERIOD)).thenReturn(-1.55740772808042)
            Mockito.`when`(sf.cot(-1.0 - EPSILON - PERIOD)).thenReturn(-0.642092614522048)
            Mockito.`when`(sf.sec(-1.0 - EPSILON - PERIOD)).thenReturn(1.85081572056340)
            Mockito.`when`(sf.csc(-1.0 - EPSILON - PERIOD)).thenReturn(-1.18839510501506)

            // Mockito.`when`(functions!!.f2(-1.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.0 - 100 * PERIOD)).thenReturn(-0.8414709848078965)
            Mockito.`when`(sf.cos(-1.0 - 100 * PERIOD)).thenReturn(0.540302305868)
            Mockito.`when`(sf.tan(-1.0 - 100 * PERIOD)).thenReturn(-1.55740772465)
            Mockito.`when`(sf.cot(-1.0 - 100 * PERIOD)).thenReturn(-0.6420926159)
            Mockito.`when`(sf.sec(-1.0 - 100 * PERIOD)).thenReturn(1.85081571768)
            Mockito.`when`(sf.csc(-1.0 - 100 * PERIOD)).thenReturn(-1.188395105778)

            // Mockito.`when`(functions!!.f2(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.841470984267594)
            Mockito.`when`(sf.cos(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(0.540302306709611)
            Mockito.`when`(sf.tan(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-1.55740772122938)
            Mockito.`when`(sf.cot(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.642092617346614)
            Mockito.`when`(sf.sec(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(1.85081571479845)
            Mockito.`when`(sf.csc(-1.0 + EPSILON - 100 * PERIOD)).thenReturn(-1.18839510654118)

            // Mockito.`when`(functions!!.f2(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-5.7044795233)
            Mockito.`when`(sf.sin(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.841470985348199)
            Mockito.`when`(sf.cos(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(0.540302305026669)
            Mockito.`when`(sf.tan(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-1.55740772808042)
            Mockito.`when`(sf.cot(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.642092614522048)
            Mockito.`when`(sf.sec(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(1.85081572056340)
            Mockito.`when`(sf.csc(-1.0 - EPSILON - 100 * PERIOD)).thenReturn(-1.18839510501506)

            // Значения около pi/2 и в эпсилон-окрестности + период (граница двух частей функии + неопределенное значение)
            // Mockito.`when`(functions!!.f2(-PI / 2.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2)).thenReturn(Double.NaN)
            Mockito.`when`(sf.cot(-PI / 2)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2)).thenReturn(Double.NaN)
            Mockito.`when`(sf.csc(-PI / 2)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.cot(-PI / 2 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.csc(-PI / 2 - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI / 2 - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.cot(-PI / 2 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.csc(-PI / 2 - 100 * PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 + EPSILON)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 + EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 + EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 + EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 + EPSILON - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 + EPSILON - 100 * PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 - EPSILON)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 - EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 - EPSILON - PERIOD)).thenReturn(-1.0)

            // Mockito.`when`(functions!!.f2(-PI / 2.0 - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.cos(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.cot(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.csc(-PI / 2 - EPSILON - 100 * PERIOD)).thenReturn(-1.0)

            // Значения для значений второй негативой части + период (слева и справа от точки перегиба)
            // Mockito.`when`(functions!!.f2(-1.7)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7)).thenReturn(-0.991664810452)
            Mockito.`when`(sf.cos(-1.7)).thenReturn(-0.1288444942955)
            Mockito.`when`(sf.tan(-1.7)).thenReturn(7.696602139)
            Mockito.`when`(sf.cot(-1.7)).thenReturn(0.1299274643382)
            Mockito.`when`(sf.sec(-1.7)).thenReturn(-7.76129399605)
            Mockito.`when`(sf.csc(-1.7)).thenReturn(-1.00840524889)

            // Mockito.`when`(functions!!.f2(-1.7 - PERIOD)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7 - PERIOD)).thenReturn(-0.991664810452)
            Mockito.`when`(sf.cos(-1.7 - PERIOD)).thenReturn(-0.1288444942955)
            Mockito.`when`(sf.tan(-1.7 - PERIOD)).thenReturn(7.696602139)
            Mockito.`when`(sf.cot(-1.7 - PERIOD)).thenReturn(0.1299274643382)
            Mockito.`when`(sf.sec(-1.7 - PERIOD)).thenReturn(-7.76129399605)
            Mockito.`when`(sf.csc(-1.7 - PERIOD)).thenReturn(-1.00840524889)

            // Mockito.`when`(functions!!.f2(-1.7 - 100 * PERIOD)).thenReturn(4.5428)
            Mockito.`when`(sf.sin(-1.7 - 100 * PERIOD)).thenReturn(-0.991664810452)
            Mockito.`when`(sf.cos(-1.7 - 100 * PERIOD)).thenReturn(-0.1288444942955)
            Mockito.`when`(sf.tan(-1.7 - 100 * PERIOD)).thenReturn(7.696602139)
            Mockito.`when`(sf.cot(-1.7 - 100 * PERIOD)).thenReturn(0.1299274643382)
            Mockito.`when`(sf.sec(-1.7 - 100 * PERIOD)).thenReturn(-7.76129399605)
            Mockito.`when`(sf.csc(-1.7 - 100 * PERIOD)).thenReturn(-1.00840524889)

            // Mockito.`when`(functions!!.f2(-2.1)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1)).thenReturn(-0.8632093666)
            Mockito.`when`(sf.cos(-2.1)).thenReturn(-0.504846104599857)
            Mockito.`when`(sf.tan(-2.1)).thenReturn(1.7098465429045)
            Mockito.`when`(sf.cot(-2.1)).thenReturn(0.584847806459)
            Mockito.`when`(sf.sec(-2.1)).thenReturn(-1.980801655967)
            Mockito.`when`(sf.csc(-2.1)).thenReturn(-1.15846750352)

            // Mockito.`when`(functions!!.f2(-2.1 - PERIOD)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1 - PERIOD)).thenReturn(-0.8632093666)
            Mockito.`when`(sf.cos(-2.1 - PERIOD)).thenReturn(-0.504846104599857)
            Mockito.`when`(sf.tan(-2.1 - PERIOD)).thenReturn(1.7098465429045)
            Mockito.`when`(sf.cot(-2.1 - PERIOD)).thenReturn(0.584847806459)
            Mockito.`when`(sf.sec(-2.1 - PERIOD)).thenReturn(-1.980801655967)
            Mockito.`when`(sf.csc(-2.1 - PERIOD)).thenReturn(-1.15846750352)

            //  Mockito.`when`(functions!!.f2(-2.1 - 100 * PERIOD)).thenReturn(-3.62892)
            Mockito.`when`(sf.sin(-2.1 - 100 * PERIOD)).thenReturn(-0.8632093666)
            Mockito.`when`(sf.cos(-2.1 - 100 * PERIOD)).thenReturn(-0.504846104599857)
            Mockito.`when`(sf.tan(-2.1 - 100 * PERIOD)).thenReturn(1.7098465429045)
            Mockito.`when`(sf.cot(-2.1 - 100 * PERIOD)).thenReturn(0.584847806459)
            Mockito.`when`(sf.sec(-2.1 - 100 * PERIOD)).thenReturn(-1.980801655967)
            Mockito.`when`(sf.csc(-2.1 - 100 * PERIOD)).thenReturn(-1.15846750352)

            // Значения в точке пересечения с осью ординат второй негативой части + период
            // Mockito.`when`(functions!!.f2(-1.843684653)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653)).thenReturn(-0.962996469946765)
            Mockito.`when`(sf.cos(-1.843684653)).thenReturn(-0.26951400496)
            Mockito.`when`(sf.tan(-1.843684653)).thenReturn(3.573085079888)
            Mockito.`when`(sf.cot(-1.843684653)).thenReturn(0.279870189945564)
            Mockito.`when`(sf.sec(-1.843684653)).thenReturn(-3.71038232371)
            Mockito.`when`(sf.csc(-1.843684653)).thenReturn(-1.03842540570816)

            // Mockito.`when`(functions!!.f2(-1.843684653 - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653 - PERIOD)).thenReturn(-0.962996469946765)
            Mockito.`when`(sf.cos(-1.843684653 - PERIOD)).thenReturn(-0.26951400496)
            Mockito.`when`(sf.tan(-1.843684653 - PERIOD)).thenReturn(3.573085079888)
            Mockito.`when`(sf.cot(-1.843684653 - PERIOD)).thenReturn(0.279870189945564)
            Mockito.`when`(sf.sec(-1.843684653 - PERIOD)).thenReturn(-3.71038232371)
            Mockito.`when`(sf.csc(-1.843684653 - PERIOD)).thenReturn(-1.03842540570816)

            // Mockito.`when`(functions!!.f2(-1.843684653 - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.sin(-1.843684653 - 100 * PERIOD)).thenReturn(-0.962996469946765)
            Mockito.`when`(sf.cos(-1.843684653 - 100 * PERIOD)).thenReturn(-0.26951400496)
            Mockito.`when`(sf.tan(-1.843684653 - 100 * PERIOD)).thenReturn(3.573085079888)
            Mockito.`when`(sf.cot(-1.843684653 - 100 * PERIOD)).thenReturn(0.279870189945564)
            Mockito.`when`(sf.sec(-1.843684653 - 100 * PERIOD)).thenReturn(-3.71038232371)
            Mockito.`when`(sf.csc(-1.843684653 - 100 * PERIOD)).thenReturn(-1.03842540570816)

            // Значения в точке перегиба и в эпсилон-окрестности этой точки
            // Mockito.`when`(functions!!.f2(-2.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0)).thenReturn(-0.90929742682568)
            Mockito.`when`(sf.cos(-2.0)).thenReturn(-0.41614683654714)
            Mockito.`when`(sf.tan(-2.0)).thenReturn(2.1850398632615)
            Mockito.`when`(sf.cot(-2.0)).thenReturn(0.45765755436028576)
            Mockito.`when`(sf.sec(-2.0)).thenReturn(-2.40299796172238)
            Mockito.`when`(sf.csc(-2.0)).thenReturn(-1.0997501702946)

            // Mockito.`when`(functions!!.f2(-2.0 - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0 - PERIOD)).thenReturn(-0.90929742682568)
            Mockito.`when`(sf.cos(-2.0 - PERIOD)).thenReturn(-0.41614683654714)
            Mockito.`when`(sf.tan(-2.0 - PERIOD)).thenReturn(2.1850398632615)
            Mockito.`when`(sf.cot(-2.0 - PERIOD)).thenReturn(0.45765755436028576)
            Mockito.`when`(sf.sec(-2.0 - PERIOD)).thenReturn(-2.40299796172238)
            Mockito.`when`(sf.csc(-2.0 - PERIOD)).thenReturn(-1.0997501702946)

            // Mockito.`when`(functions!!.f2(-2.0 - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-2.0 - 100 * PERIOD)).thenReturn(-0.90929742682568)
            Mockito.`when`(sf.cos(-2.0 - 100 * PERIOD)).thenReturn(-0.41614683654714)
            Mockito.`when`(sf.tan(-2.0 - 100 * PERIOD)).thenReturn(2.1850398632615)
            Mockito.`when`(sf.cot(-2.0 - 100 * PERIOD)).thenReturn(0.45765755436028576)
            Mockito.`when`(sf.sec(-2.0 - 100 * PERIOD)).thenReturn(-2.40299796172238)
            Mockito.`when`(sf.csc(-2.0 - 100 * PERIOD)).thenReturn(-1.0997501702946)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON)).thenReturn(-0.909297426867296)
            Mockito.`when`(sf.cos(-2.0 + EPSILON)).thenReturn(-0.416146836456)
            Mockito.`when`(sf.tan(-2.0 + EPSILON)).thenReturn(2.1850398638)
            Mockito.`when`(sf.cot(-2.0 + EPSILON)).thenReturn(0.45765755423934)
            Mockito.`when`(sf.sec(-2.0 + EPSILON)).thenReturn(-2.4029979622474456)
            Mockito.`when`(sf.csc(-2.0 + EPSILON)).thenReturn(-1.099750170244285569)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON - PERIOD)).thenReturn(-0.909297426867296)
            Mockito.`when`(sf.cos(-2.0 + EPSILON - PERIOD)).thenReturn(-0.416146836456)
            Mockito.`when`(sf.tan(-2.0 + EPSILON - PERIOD)).thenReturn(2.1850398638)
            Mockito.`when`(sf.cot(-2.0 + EPSILON - PERIOD)).thenReturn(0.45765755423934)
            Mockito.`when`(sf.sec(-2.0 + EPSILON - PERIOD)).thenReturn(-2.4029979622474456)
            Mockito.`when`(sf.csc(-2.0 + EPSILON - PERIOD)).thenReturn(-1.099750170244285569)

            // Mockito.`when`(functions!!.f2(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.909297426867296)
            Mockito.`when`(sf.cos(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-0.416146836456)
            Mockito.`when`(sf.tan(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(2.1850398638)
            Mockito.`when`(sf.cot(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(0.45765755423934)
            Mockito.`when`(sf.sec(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-2.4029979622474456)
            Mockito.`when`(sf.csc(-2.0 + EPSILON - 100 * PERIOD)).thenReturn(-1.099750170244285569)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON)).thenReturn(-0.9092974267840670)
            Mockito.`when`(sf.cos(-2.0 - EPSILON)).thenReturn(-0.4161468366380721)
            Mockito.`when`(sf.tan(-2.0 - EPSILON)).thenReturn(2.185039862684079)
            Mockito.`when`(sf.cot(-2.0 - EPSILON)).thenReturn(0.4576575544812308)
            Mockito.`when`(sf.sec(-2.0 - EPSILON)).thenReturn(-2.402997961197316)
            Mockito.`when`(sf.csc(-2.0 - EPSILON)).thenReturn(-1.09975017034494736)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON - PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON - PERIOD)).thenReturn(-0.9092974267840670)
            Mockito.`when`(sf.cos(-2.0 - EPSILON - PERIOD)).thenReturn(-0.4161468366380721)
            Mockito.`when`(sf.tan(-2.0 - EPSILON - PERIOD)).thenReturn(2.185039862684079)
            Mockito.`when`(sf.cot(-2.0 - EPSILON - PERIOD)).thenReturn(0.4576575544812308)
            Mockito.`when`(sf.sec(-2.0 - EPSILON - PERIOD)).thenReturn(-2.402997961197316)
            Mockito.`when`(sf.csc(-2.0 - EPSILON - PERIOD)).thenReturn(-1.09975017034494736)

            // Mockito.`when`(functions!!.f2(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-2.259600862)
            Mockito.`when`(sf.sin(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.9092974267840670)
            Mockito.`when`(sf.cos(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-0.4161468366380721)
            Mockito.`when`(sf.tan(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(2.185039862684079)
            Mockito.`when`(sf.cot(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(0.4576575544812308)
            Mockito.`when`(sf.sec(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-2.402997961197316)
            Mockito.`when`(sf.csc(-2.0 - EPSILON - 100 * PERIOD)).thenReturn(-1.09975017034494736)

            // Граничное значение между второй и третьей частями и окрестности точки PI
            // Mockito.`when`(functions!!.f2(-PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(-PI)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(-PI - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI - PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(-PI - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI - PERIOD)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(-PI - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-PI - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI - 100 * PERIOD)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sec(-PI - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI - 100 * PERIOD)).thenReturn(Double.NaN)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI + EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI + EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI + EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI + EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI + EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI + EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI + EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI + EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)

            // Mockito.`when`(functions!!.f2(-PI + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI + EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI + EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI + EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI + EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI + EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI + EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI - EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI - EPSILON)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI - EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI - EPSILON - PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI - EPSILON - PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI - EPSILON - PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI - EPSILON - PERIOD)).thenReturn(Double.POSITIVE_INFINITY)

            // Mockito.`when`(functions!!.f2(-PI - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-PI - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-PI - EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.tan(-PI - EPSILON - 100 * PERIOD)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-PI - EPSILON - 100 * PERIOD)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sec(-PI - EPSILON - 100 * PERIOD)).thenReturn(-1.0)
            Mockito.`when`(sf.csc(-PI - EPSILON - 100 * PERIOD)).thenReturn(Double.POSITIVE_INFINITY)

            // Третья часть справа и слева от экстремума + париод
            // Mockito.`when`(functions!!.f2(-3.5)).thenReturn(25.97728)
            Mockito.`when`(sf.sin(-3.5)).thenReturn(0.3507832276896)
            Mockito.`when`(sf.cos(-3.5)).thenReturn(-0.93645668729)
            Mockito.`when`(sf.tan(-3.5)).thenReturn(-0.37458564015859)
            Mockito.`when`(sf.cot(-3.5)).thenReturn(-2.669616484968866)
            Mockito.`when`(sf.sec(-3.5)).thenReturn(-1.0678550471918)
            Mockito.`when`(sf.csc(-3.5)).thenReturn(2.850763437540464)

            // Mockito.`when`(functions!!.f2(-3.5 - 100 * PERIOD)).thenReturn(25.97728)
            Mockito.`when`(sf.sin(-3.5 - 100 * PERIOD)).thenReturn(0.3507832276896)
            Mockito.`when`(sf.cos(-3.5 - 100 * PERIOD)).thenReturn(-0.93645668729)
            Mockito.`when`(sf.tan(-3.5 - 100 * PERIOD)).thenReturn(-0.37458564015859)
            Mockito.`when`(sf.cot(-3.5 - 100 * PERIOD)).thenReturn(-2.669616484968866)
            Mockito.`when`(sf.sec(-3.5 - 100 * PERIOD)).thenReturn(-1.0678550471918)
            Mockito.`when`(sf.csc(-3.5 - 100 * PERIOD)).thenReturn(2.850763437540464)

            // Mockito.`when`(functions!!.f2(-4.3)).thenReturn(3.78801)
            Mockito.`when`(sf.sin(-4.3)).thenReturn(0.91616593674945498)
            Mockito.`when`(sf.cos(-4.3)).thenReturn(-0.400799172079975)
            Mockito.`when`(sf.tan(-4.3)).thenReturn(-2.28584787736698)
            Mockito.`when`(sf.cot(-4.3)).thenReturn(-0.4374744312171)
            Mockito.`when`(sf.sec(-4.3)).thenReturn(-2.4950151339146)
            Mockito.`when`(sf.csc(-4.3)).thenReturn(1.091505326587438)

            // Mockito.`when`(functions!!.f2(-4.3 - 100 * PERIOD)).thenReturn(3.78801)
            Mockito.`when`(sf.sin(-4.3 - 100 * PERIOD)).thenReturn(0.91616593674945498)
            Mockito.`when`(sf.cos(-4.3 - 100 * PERIOD)).thenReturn(-0.400799172079975)
            Mockito.`when`(sf.tan(-4.3 - 100 * PERIOD)).thenReturn(-2.28584787736698)
            Mockito.`when`(sf.cot(-4.3 - 100 * PERIOD)).thenReturn(-0.4374744312171)
            Mockito.`when`(sf.sec(-4.3 - 100 * PERIOD)).thenReturn(-2.4950151339146)
            Mockito.`when`(sf.csc(-4.3 - 100 * PERIOD)).thenReturn(1.091505326587438)

            // Экстремум в третьей части и эпсилон-окрестности точки перегиба
            // Mockito.`when`(functions!!.f2(-4.0)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-4.0)).thenReturn(0.7568024953079)
            Mockito.`when`(sf.cos(-4.0)).thenReturn(-0.65364362086361191)
            Mockito.`when`(sf.tan(-4.0)).thenReturn(-1.15782128234957758)
            Mockito.`when`(sf.cot(-4.0)).thenReturn(-0.8636911544506)
            Mockito.`when`(sf.sec(-4.0)).thenReturn(-1.52988565646639757)
            Mockito.`when`(sf.csc(-4.0)).thenReturn(1.321348708810902)

            // Mockito.`when`(functions!!.f2(-4.0 + EPSILON)).thenReturn(3.259463457)
            Mockito.`when`(sf.sin(-4.0 + EPSILON)).thenReturn(0.75680249465428463)
            Mockito.`when`(sf.cos(-4.0 + EPSILON)).thenReturn(-0.65364362162041)
            Mockito.`when`(sf.tan(-4.0 + EPSILON)).thenReturn(-1.157821280009027)
            Mockito.`when`(sf.cot(-4.0 + EPSILON)).thenReturn(-0.863691156196579)
            Mockito.`when`(sf.sec(-4.0 + EPSILON)).thenReturn(-1.52988565469506)
            Mockito.`when`(sf.csc(-4.0 + EPSILON)).thenReturn(1.32134870995213957)

            // Mockito.`when`(functions!!.f2(-4.0 - EPSILON)).thenReturn(3.259463457)
            Mockito.`when`(sf.sin(-4.0 - EPSILON)).thenReturn(0.75680249596157187)
            Mockito.`when`(sf.cos(-4.0 - EPSILON)).thenReturn(-0.6536436201068094)
            Mockito.`when`(sf.tan(-4.0 - EPSILON)).thenReturn(-1.1578212846901277)
            Mockito.`when`(sf.cot(-4.0 - EPSILON)).thenReturn(-0.863691152704654205)
            Mockito.`when`(sf.sec(-4.0 - EPSILON)).thenReturn(-1.52988565823773)
            Mockito.`when`(sf.csc(-4.0 - EPSILON)).thenReturn(1.321348707669665)

            // Граничные точки между третим и четвертым отрезком
            // Mockito.`when`(functions!!.f2(-1.5 * PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.sin(-1.5 * PI)).thenReturn(1.0)
            Mockito.`when`(sf.cos(-1.5 * PI)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-1.5 * PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.cot(-1.5 * PI)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-1.5 * PI)).thenReturn(Double.NaN)
            Mockito.`when`(sf.csc(-1.5 * PI)).thenReturn(1.0)

            // Mockito.`when`(functions!!.f2(-1.5 * PI + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sin(-1.5 * PI + EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.cos(-1.5 * PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-1.5 * PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.cot(-1.5 * PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-1.5 * PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.csc(-1.5 * PI + EPSILON)).thenReturn(1.0)

            // Mockito.`when`(functions!!.f2(-1.5 * PI - EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-1.5 * PI - EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.cos(-1.5 * PI - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.tan(-1.5 * PI - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.cot(-1.5 * PI - EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.sec(-1.5 * PI - EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.csc(-1.5 * PI - EPSILON)).thenReturn(1.0)

            // Четвертая часть: справа и слева от экстремума + период
            // Mockito.`when`(functions!!.f2(-5.1)).thenReturn(-2.1949)
            Mockito.`when`(sf.sin(-5.1)).thenReturn(0.925814682327732)
            Mockito.`when`(sf.cos(-5.1)).thenReturn(0.37797774271298)
            Mockito.`when`(sf.tan(-5.1)).thenReturn(2.44938941558459612)
            Mockito.`when`(sf.cot(-5.1)).thenReturn(0.40826501234852844)
            Mockito.`when`(sf.sec(-5.1)).thenReturn(2.645658426399343)
            Mockito.`when`(sf.csc(-5.1)).thenReturn(1.080129770123916)

            //  Mockito.`when`(functions!!.f2(-5.1 - 100 * PERIOD)).thenReturn(-2.1949)
            Mockito.`when`(sf.sin(-5.1 - 100 * PERIOD)).thenReturn(0.925814682327732)
            Mockito.`when`(sf.cos(-5.1 - 100 * PERIOD)).thenReturn(0.37797774271298)
            Mockito.`when`(sf.tan(-5.1 - 100 * PERIOD)).thenReturn(2.44938941558459612)
            Mockito.`when`(sf.cot(-5.1 - 100 * PERIOD)).thenReturn(0.40826501234852844)
            Mockito.`when`(sf.sec(-5.1 - 100 * PERIOD)).thenReturn(2.645658426399343)
            Mockito.`when`(sf.csc(-5.1 - 100 * PERIOD)).thenReturn(1.080129770123916)

            // Mockito.`when`(functions!!.f2(-5.9)).thenReturn(-5.47627)
            Mockito.`when`(sf.sin(-5.9)).thenReturn(0.373876664830236)
            Mockito.`when`(sf.cos(-5.9)).thenReturn(0.92747843074403574)
            Mockito.`when`(sf.tan(-5.9)).thenReturn(0.4031108998732266396323)
            Mockito.`when`(sf.cot(-5.9)).thenReturn(2.48070692287032564165)
            Mockito.`when`(sf.sec(-5.9)).thenReturn(1.07819218954535306596)
            Mockito.`when`(sf.csc(-5.9)).thenReturn(2.67467882878987)

            //  Mockito.`when`(functions!!.f2(-5.9 - 100 * PERIOD)).thenReturn(-5.47627)
            Mockito.`when`(sf.sin(-5.9 - 100 * PERIOD)).thenReturn(0.373876664830236)
            Mockito.`when`(sf.cos(-5.9 - 100 * PERIOD)).thenReturn(0.92747843074403574)
            Mockito.`when`(sf.tan(-5.9 - 100 * PERIOD)).thenReturn(0.4031108998732266396323)
            Mockito.`when`(sf.cot(-5.9 - 100 * PERIOD)).thenReturn(2.48070692287032564165)
            Mockito.`when`(sf.sec(-5.9 - 100 * PERIOD)).thenReturn(1.07819218954535306596)
            Mockito.`when`(sf.csc(-5.9 - 100 * PERIOD)).thenReturn(2.67467882878987)

            // Граничная точка
            // Mockito.`when`(functions!!.f2(-2.0 * PI + EPSILON)).thenReturn(Double.NEGATIVE_INFINITY)
            Mockito.`when`(sf.sin(-2.0 * PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cos(-2.0 * PI + EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.tan(-2.0 * PI + EPSILON)).thenReturn(0.0)
            Mockito.`when`(sf.cot(-2.0 * PI + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)
            Mockito.`when`(sf.sec(-2.0 * PI + EPSILON)).thenReturn(1.0)
            Mockito.`when`(sf.csc(-2.0 * PI + EPSILON)).thenReturn(Double.POSITIVE_INFINITY)


            /**
             * Положительная часть - функция, которая пересекает прямую ох 3 раза
             * В точке x = 0 стремиться к бесконечности
             */

            // Отрезок между 0 и пересечением о осью ох
            // Mockito.`when`(functions!!.f1(0.01)).thenReturn(275.519)
            Mockito.`when`(sf.log_2(0.01)).thenReturn(-6.64385618977472)
            Mockito.`when`(sf.log_3(0.01)).thenReturn(-4.191806548578769)
            Mockito.`when`(sf.log_5(0.01)).thenReturn(-2.8613531161467861)
            Mockito.`when`(sf.log_10(0.01)).thenReturn(-2.0)

            // Первое пересечение с осью ох
            // Mockito.`when`(functions!!.f1(0.0159975)).thenReturn(0.0)
            Mockito.`when`(sf.log_2(0.0159975)).thenReturn(-5.966009723375084)
            Mockito.`when`(sf.log_3(0.0159975)).thenReturn(-3.7641330445739607569)
            Mockito.`when`(sf.log_5(0.0159975)).thenReturn(-2.56942053309557701)
            Mockito.`when`(sf.log_10(0.0159975)).thenReturn(-1.79594788115887)

            // Значение функции на убывающем отрезке между первым и вторым пересечением ох
            // Mockito.`when`(functions!!.f1(0.02)).thenReturn(-61.70484)
            Mockito.`when`(sf.log_2(0.02)).thenReturn(-5.64385618977472)
            Mockito.`when`(sf.log_3(0.02)).thenReturn(-3.56087679500731177)
            Mockito.`when`(sf.log_5(0.02)).thenReturn(-2.43067655807339305)
            Mockito.`when`(sf.log_10(0.02)).thenReturn(-1.6989700043360188)

            // Значение функции на возрастающем отрезке между первым и вторым пересечением ох
            // Mockito.`when`(functions!!.f1(0.05)).thenReturn(-91.44142)
            Mockito.`when`(sf.log_2(0.05)).thenReturn(-4.3219280948873623)
            Mockito.`when`(sf.log_3(0.05)).thenReturn(-2.72683302786084204)
            Mockito.`when`(sf.log_5(0.05)).thenReturn(-1.861353116146786)
            Mockito.`when`(sf.log_10(0.05)).thenReturn(-1.301029995663981)

            // Второе пересечение с ox
            // Mockito.`when`(functions!!.f1(0.321061)).thenReturn(0.0)
            Mockito.`when`(sf.log_2(0.321061)).thenReturn(-1.63908066658413)
            Mockito.`when`(sf.log_3(0.321061)).thenReturn(-1.0341447610516654)
            Mockito.`when`(sf.log_5(0.321061)).thenReturn(-0.7059136198890959)
            Mockito.`when`(sf.log_10(0.321061)).thenReturn(-0.493412445954736)

            // Третье пересечение с ох
            // Mockito.`when`(functions!!.f1(65.5183)).thenReturn(0.0)
            Mockito.`when`(sf.log_2(65.5183)).thenReturn(6.0338260188170204932)
            Mockito.`when`(sf.log_3(65.5183)).thenReturn(3.80692036314527)
            Mockito.`when`(sf.log_5(65.5183)).thenReturn(2.5986274217977985)
            Mockito.`when`(sf.log_10(65.5183)).thenReturn(1.8163626202817045966692)

        }
    }

    @Test
    fun testLeftEndpointF1() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(0.0 + EPSILON),
            PRECISION,
            "Layer 2: [F1] (х = 0+). Левая граничная точка для положительного интервала."
        )
    }

    @Test
    fun testTheSegmentBetweenZeroAndTheIntersectionOfTheOX() {
        assertEquals(
            275.519,
            functions!!.systemOfFunctions(0.01),
            PRECISION,
            "Layer 2: [F1] (х = 0.01). Отрезок между 0 и пересечением о осью ох."
        )
    }

    @Test
    fun testFirstIntersectionWithTheOX() {
        assertEquals(
            0.0007,
            functions!!.systemOfFunctions(0.0159975),
            PRECISION,
            "Layer 2: [F1] (х = 0.0159975).Первое пересечение с осью ох"
        )
    }

    @Test
    fun testTheValueOfTheFunctionOnTheDecreasingSegmentBetweenTheFirstAndSecondIntersectionOX() {
        assertEquals(
            -61.70484,
            functions!!.systemOfFunctions(0.02),
            PRECISION,
            "Layer 2: [F1] (х = 0.02). Значение функции на убывающем отрезке между первым и вторым пересечением ох"
        )
    }

    @Test
    fun testTheValueOfTheFunctionOnTheIncreasingSegmentBetweenTheFirstAndSecondIntersectionOX() {
        assertEquals(
            -91.44142,
            functions!!.systemOfFunctions(0.05),
            PRECISION,
            "Layer 2: [F1] (х = 0.02).Значение функции на возрастающем отрезке между первым и вторым пересечением ох"
        )
    }

    @Test
    fun testSecondIntersectionWithTheOX() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(0.321061),
            PRECISION,
            "Layer 2: [F1] (х = 0.321061). Второе пересечение с осью ох"
        )
    }

    @Test
    fun testThirdIntersectionWithTheOX() {
        assertEquals(
            -0.00013,
            functions!!.systemOfFunctions(65.5183),
            PRECISION,
            "Layer 2: [F1] (х = 65.5183). Третье пересечение с осью ох"
        )
    }

    @Test
    fun testZeroValue() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0),
            PRECISION,
            "Layer 2: [х = 0]. Тестирование нулевого значения."
        )
    }


    @Test
    fun testZeroValueFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 - PERIOD),
            PRECISION,
            "Layer 2: [х = 0 - PERIOD]. Тестирование нулевого значения со сдвигом в период"
        )
    }

    @Test
    fun testZeroValueHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(0.0 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [х = 0 - 100 * PERIOD]. Тестирование нулевого значения со сдвигом в 100 периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZero() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON),
            PRECISION,
            "Layer 2: [х = 0-]. Тестирование граничного значения слева от нуля."
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [х = 0 - EPSILON - PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в период влево"
        )
    }

    @Test
    fun testBoundaryValueToTheLeftOfZeroHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [х = 0 - EPSILON - 100 * PERIOD]. Тестирование граничного значения слева от нуля со сдвигом в сто периодов влево"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtreme() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5),
            PRECISION,
            "Layer 2: [х = -0.5]. Первый кусок функции справа от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeFirstPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - PERIOD),
            PRECISION,
            "Layer 2: [x = -0.5 - PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheRightOfTheExtremeHundredthPeriod() {
        assertEquals(
            -9.86725,
            functions!!.systemOfFunctions(-0.5 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -0.5 - 100 * PERIOD]. Первый кусок функции справа от экстремума. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtreme() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3),
            PRECISION,
            "Layer 2: [х = -1.3]. Первый кусок функции слева от экстремума."
        )
    }

    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLeftOfTheExtremeFirstPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - PERIOD),
            PRECISION,
            "Layer 2: [x = -1.3 - PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в один период"
        )
    }


    @Test
    fun testSystemOfFunctionFirstPartOfNegativeValueToTheLefttOfTheExtremeHundredthPeriod() {
        assertEquals(
            -6.80003,
            functions!!.systemOfFunctions(-1.3 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1.3 - 100 * PERIOD]. Первый кусок функции слева от экстремума. Проверка переодичности: сдвиг в сто периодов"
        )
    }

    @Test
    fun testValueOne() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0),
            PRECISION,
            "Layer 2: [x = -1]. Первый кусок функции. При неопределенном значении."
        )
    }

    @Test
    fun testValueOneFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0 - PERIOD),
            PRECISION,
            "Layer 2: [x = -1 - PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на один период."
        )
    }

    @Test
    fun testValueOneHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.0 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1 - 100 * PERIOD]. Первый кусок функции. При неопределенном значении сдвиг на сто периодов."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON),
            PRECISION,
            "Layer 2: [x = -1 + EPS]. Первый кусок функции. При неопределенном значении. x -> 1+."
        )
    }

    @Test
    fun testValueToTheRightIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -1 + EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheRightIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1 + EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1+. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testValueToTheLeftIsAboutOne() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON),
            PRECISION,
            "Layer 2: [x = -1 - EPS]. Первый кусок функции. При неопределенном значении. x -> 1-."
        )
    }

    @Test
    fun testValueToTheLefttIsAboutOneFirstPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -1 - EPS - PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на один период"
        )
    }


    @Test
    fun testValueToTheLeftIsAboutOneHundredthPeriod() {
        assertEquals(
            -5.7044795233,
            functions!!.systemOfFunctions(-1.0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1 - EPS - 100 * PERIOD]. Первый кусок функции. При неопределенном значении. x -> 1-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testIndefiniteValueInHalfPi() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2),
            PRECISION,
            "Layer 2: [x = -PI / 2]. При неопределенном значении."
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 - PERIOD]. При неопределенном значении. Сдвиг на один период"
        )
    }

    @Test
    fun testIndefiniteValueInHalfPiHundredthPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI / 2 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 - 100 * PERIOD]. При неопределенном значении. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPi() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON),
            PRECISION,
            "Layer 2: [x = -PI / 2 + EPS]. Граничное значение справа от -PI/2. x->-pi/2+"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 + EPS - PERIOD]. Граничное значение справа от PI/2. x->pi/2+. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheRightOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 + EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2+. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPi() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON),
            PRECISION,
            "Layer 2: [x = -PI / 2 - EPS]. Граничное значение справа от -PI/2. x->-pi/2-"
        )
    }

    @Test
    fun testBoundaryValueToTheRightOfHalfPiLeftPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 - EPS - PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueToTheLeftOfHalfPiHundredthPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI / 2 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI / 2 - EPS - 100 * PERIOD]. Граничное значение справа от -PI/2. x->-pi/2-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPoint() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7),
            PRECISION,
            "Layer 2: [x = -1.7]. Вторая негативная часть справа от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointFirstPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - PERIOD),
            PRECISION,
            "Layer 2: [x = -1.7 - PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheRightOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            4.5428,
            functions!!.systemOfFunctions(-1.7 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1.7 - 100 * PERIOD]. Вторая негативная часть справа от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPoint() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1),
            PRECISION,
            "Layer 2: [x = -2.1]. Вторая негативная часть слева от точки перегиба"
        )
    }

    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointFirstPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - PERIOD),
            PRECISION,
            "Layer 2: [x = -2.1 - PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на один период"
        )
    }


    @Test
    fun testSecondNegativePartToTheLeftOfTheInflectionPointHundredthPeriod() {
        assertEquals(
            -3.62892,
            functions!!.systemOfFunctions(-2.1 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -2.1 - 100 * PERIOD]. Вторая негативная часть слева от точки перегиба сдвиг на сто периодов"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePart() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653),
            PRECISION,
            "Layer 2: [x = -1.843684653 (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartFirstPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - PERIOD),
            PRECISION,
            "Layer 2: [x = -1.843684653 - PERIOD (y = 0) ]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtThePointOfIntersectionWithTheYAxisOfTheSecondNegativePartHundredthPeriod() {
        assertEquals(
            0.0,
            functions!!.systemOfFunctions(-1.843684653 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -1.843684653 - 100 * PERIOD (y = 0)]. Значения в точке пересечения с осью ординат второй негативой части со сдвигом в один период"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPart() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0),
            PRECISION,
            "Layer 2: [x = -2]. Значения в точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - PERIOD),
            PRECISION,
            "Layer 2: [x = -2 - PERIOD]. Значения в точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesAtTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-2.0 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -2 - 100 * PERIOD]. Значения в точке перегиба второй чати сдвиг в сто периодов"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON),
            PRECISION,
            "Layer 2: [x = -2 + EPS ]. Значения в окрестности справа к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -2 + EPS - PERIOD]. Значения в окрестности справа к точке перегиба второй чати сдвиг в один период"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheRightOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -2 + EPS - 100 * PERIOD]. Значения в окрестности справа к точке перегиба второй чати в сто периодов"
        )
    }


    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPart() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON),
            PRECISION,
            "Layer 2: [x = -2 - EPS ]. Значения в окрестности слева к точке перегиба второй чати"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartFirstPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -2 - EPS - PERIOD]. Значения в окрестности слева к точке перегиба второй чати сдвиг в один период"
        )
    }

    @Test
    fun testValuesInTheNeighborhoodToTheLeftOfTheInflectionPointOfTheSecondPartHundredPeriod() {
        assertEquals(
            -2.259600862,
            functions!!.systemOfFunctions(-2.0 - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -2 - EPS - 100 * PERIOD]. Значения в окрестности слева к точке перегиба второй чати в сто периодов"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPoint() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI),
            PRECISION,
            "Layer 2: [x = -PI].  Граничное значение между второй и третьей частями и окрестности точки PI."
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI- PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на один период"
        )
    }

    @Test
    fun testTheBoundaryValueBetweenTheSecondAndThirdPartsAndTheVicinityOfThePIPointPeriod() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-PI - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI - 100 * PERIOD].  Граничное значение между второй и третьей частями и окрестности точки PI. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePI() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON),
            PRECISION,
            "Layer 2: [x = -PI + EPS].  Граничное значение между второй и третьей частями и справа от  точки PI . x->-pi+"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIPointFirstPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI + EPS - PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI . Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheRightOfThePIHundredthPeriod() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-PI + EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI + EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и справа от  точки PI. Сдвиг на сто периодов"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePI() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON),
            PRECISION,
            "Layer 2: [x = -PI - EPS].  Граничное значение между второй и третьей частями и слева от  точки PI  x->-pi-"
        )
    }

    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIFirstPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON - PERIOD),
            PRECISION,
            "Layer 2: [x = -PI  - EPS - PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на один период"
        )
    }


    @Test
    fun testBoundaryValueBetweenTheSecondAndThirdPartsAndToTheLeftOfThePIHundredthPeriod() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-PI - EPSILON - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -PI - EPS - 100 * PERIOD].  Граничное значение между второй и третьей частями и слева от  точки PI. x->-pi-. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremum() {
        assertEquals(
            25.9773,
            functions!!.systemOfFunctions(-3.5),
            PRECISION,
            "Layer 2: [x = -3.5].  Третья часть справа от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheRightOfTheExtremumWithPeriod() {
        assertEquals(
            25.97728,
            functions!!.systemOfFunctions(-3.5 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -3.5 - 100 * PERIOD].  Третья часть справа от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremum() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3),
            PRECISION,
            "Layer 2: [x = -4.3].  Третья часть слева от экстремума"
        )
    }

    @Test
    fun testTheThirdPartToTheLeftOfTheExtremumWithPeriod() {
        assertEquals(
            3.78801,
            functions!!.systemOfFunctions(-4.3 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -4.3 - 100 * PERIOD].  Третья часть слева от экстремума. Сдвиг на сто периодов"
        )
    }

    @Test
    fun testExtremumInTheThirdPart() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-4.0),
            PRECISION,
            "Layer 2: [x = -4.0].  Экстремум в третьей части "
        )
    }

    @Test
    fun testNeighborhoodToTheRightOfExtremumInTheThirdPart() {
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 + EPSILON),
            PRECISION,
            "Layer 2: [x = -4.0 + EPS].  Окрестность справа от экстремума в третьей части"
        )
    }

    @Test
    fun testNeighborhoodToTheLeftOfExtremumInTheThirdPart() {
        assertEquals(
            3.259463457,
            functions!!.systemOfFunctions(-4.0 - EPSILON),
            PRECISION,
            "Layer 2: [x = -4.0 - EPS].  Окрестность слева от экстремума в третьей части"
        )
    }

    @Test
    fun testBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NaN,
            functions!!.systemOfFunctions(-1.5 * PI),
            PRECISION,
            "Layer 2: [x = -1/5 * PI].  Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testRightNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.POSITIVE_INFINITY,
            functions!!.systemOfFunctions(-1.5 * PI + EPSILON),
            PRECISION,
            "Layer 2: [x = -1/5 * PI + EPS]. Окрестность справа Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testLeftNeighborhoodBoundaryPointsBetweenTheThirdAndFourthLineSegments() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-1.5 * PI - EPSILON),
            PRECISION,
            "Layer 2: [x = -1/5 * PI - EPS]. Окрестность слева Граничные точки между третим и четвертым отрезком"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremum() {
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1),
            PRECISION,
            "Layer 2: [x = -5.1]. Четвертая часть справа от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheRightOfTheExtremumWithPeriod() {
        assertEquals(
            -2.1949,
            functions!!.systemOfFunctions(-5.1 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -5.1 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremum() {
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9),
            PRECISION,
            "Layer 2: [x = -5.9]. Четвертая часть слева от экстремума"
        )
    }

    @Test
    fun testTheFourthPartToTheLeftOfTheExtremumWithPeriod() {
        assertEquals(
            -5.47627,
            functions!!.systemOfFunctions(-5.9 - 100 * PERIOD),
            PRECISION,
            "Layer 2: [x = -5.9 + PERIOD]. Четвертая часть справа от экстремума с периодом"
        )
    }

    @Test
    fun testTheBorderPointOfTheFourthPartIsAboutTwoPi() {
        assertEquals(
            Double.NEGATIVE_INFINITY,
            functions!!.systemOfFunctions(-2.0 * PI + EPSILON),
            PRECISION,
            "Layer 2: [x = -2.0 * PI + EPSILON]. Граничная точка четвертой части около 2PI"
        )
    }
}