import kotlin.math.sqrt

open class SimpleFunctions {
    open fun sin(x: Double): Double {
        return 1.0
    }

    open fun cos(x: Double): Double {
        return sqrt(1.0 - sin(x) * sin(x))
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
        return ln(x)/ln(2.0)
    }

    open fun log_3(x: Double): Double {
        return ln(x)/ln(3.0)
    }

    open fun log_5(x: Double): Double {
        return ln(x)/ln(5.0)
    }

    open fun log_10(x: Double): Double {
        return ln(x)/ln(10.0)
    }

    open fun ln(x: Double): Double {
        return 1.0
    }
}