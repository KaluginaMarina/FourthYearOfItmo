import SimpleFunctions.Companion.cos
import SimpleFunctions.Companion.cot
import SimpleFunctions.Companion.csc
import SimpleFunctions.Companion.log_10
import SimpleFunctions.Companion.log_2
import SimpleFunctions.Companion.log_3
import SimpleFunctions.Companion.log_5
import SimpleFunctions.Companion.sec
import SimpleFunctions.Companion.sin
import SimpleFunctions.Companion.tan

open class Functions {

    companion object {
        const val PRECISION = 1E-8
    }

    open fun systemOfFunctions(x: Double): Double {
        return if (x > 0) f1(x) else f2(x)
    }

    open fun f1(x: Double): Double {
        return (((((log_2(x) * log_2(x)) * log_3(x)) - log_10(x)) * ((log_10(x) * log_10(x) * log_10(x)) - log_2(x))) -
                (log_3(x) * ((log_2(x) / log_5(x)) - log_3(x))))
    }

    open fun f2(x: Double): Double {
        return (((((csc(x) + sin(x)) - cot(x)) / tan(x)) * ((cot(x) - cos(x)) + tan(x))) - ((csc(x) * csc(x)) + sec(x)))
    }

}