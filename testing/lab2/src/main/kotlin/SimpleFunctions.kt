import java.lang.Math.PI
import java.lang.Math.abs
import kotlin.math.sqrt

open class SimpleFunctions {

    companion object {
        const val PRECISION = 1E-8
    }

    private fun sin(x: Double, cur: Double, n: Int = 1, res: Double = 0.0): Double {
        if (abs(cur) < PRECISION) return res
        return sin(x, cur * (-x * x / (2.0 * n * (2.0 * n + 1.0))), n + 1, res + cur)
    }

    open fun sin(x: Double): Double {
        val x1 = x % (2 * PI)
        if (abs(abs(x1) - PI) < PRECISION || abs(abs(x1) - 2 * PI) < PRECISION || abs(abs(x1) - 0.0) < PRECISION) {
            return 0.0
        }
        return sin(x1, x1)
    }

    open fun cos(x: Double): Double {
        val sign = if (((x % (2.0 * PI)) < PI / 2) && ((x % (2.0 * PI)) > -PI / 2) ||
            ((x % (2.0 * PI)) < -1.5 * PI) || ((x % (2.0 * PI)) > 1.5 * PI)
        ) 1 else -1
        return sqrt(1.0 - sin(x) * sin(x)) * sign
    }

    open fun tan(x: Double): Double {
        return sin(x) / cos(x)
    }

    open fun cot(x: Double): Double {
        return cos(x) / sin(x)
    }

    open fun sec(x: Double): Double {
        return 1.0 / cos(x)
    }

    open fun csc(x: Double): Double {
        return 1.0 / sin(x)
    }

    open fun log_2(x: Double): Double {
        return ln(x) / ln(2.0)
    }

    open fun log_3(x: Double): Double {
        return ln(x) / ln(3.0)
    }

    open fun log_5(x: Double): Double {
        return ln(x) / ln(5.0)
    }

    open fun log_10(x: Double): Double {
        return ln(x) / ln(10.0)
    }

    open fun ln(x: Double): Double {
        return 1.0
    }
}