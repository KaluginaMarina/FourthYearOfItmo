open class Functions (var sf: SimpleFunctions) {

    open fun systemOfFunctions(x: Double): Double {
        return if (x > 0) f1(x) else f2(x)
    }

    open fun f1(x: Double): Double {
        return (((((sf.log_2(x) * sf.log_2(x)) * sf.log_3(x)) - sf.log_10(x)) * ((sf.log_10(x) * sf.log_10(x) * sf.log_10(x)) -
                sf.log_2(x))) - (sf.log_3(x) * ((sf.log_2(x) / sf.log_5(x)) - sf.log_3(x))))
    }

    open fun f2(x: Double): Double {
        return (((((sf.csc(x) + sf.sin(x)) - sf.cot(x)) / sf.tan(x)) * ((sf.cot(x) - sf.cos(x)) + sf.tan(x))) -
                ((sf.csc(x) * sf.csc(x)) + sf.sec(x)))
    }

}