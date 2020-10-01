open class Functions {

    companion object {
        const val PRECISION = 1E-8
    }

    open fun systemOfFunctions(x: Double): Double {
        return if (x > 0) {
            f1(x)
        } else {
            f2(x)
        }
    }

    open fun f1(x: Double): Double {
        return (((((log_2(x) * log_2(x)) * log_3(x)) - log_10(x)) * ((log_10(x) * log_10(x) * log_10(x)) - log_2(x))) -
                (log_3(x) * ((log_2(x) / log_5(x)) - log_3(x))))
    }

   open fun f2(x: Double): Double {
        return (((((csc(x) + sin(x)) - cot(x)) / tan(x)) * ((cot(x) - cos(x)) + tan(x))) - ((csc(x) * csc(x)) + sec(x)))
    }

    private fun sec(x: Double): Double {
        return 1.0
    }

    private fun cos(x: Double): Double {
        return 1.0
    }

    private fun tan(x: Double): Double {
        return 1.0
    }

    private fun cot(x: Double): Double {
        return 1.0
    }

    private fun sin(x: Double): Double {
        return 1.0
    }

    private fun csc(x: Double): Double {
        return 1.0
    }

    private fun log_5(x: Double): Double {
        return 1.0
    }

    private fun log_10(x: Double): Double {
        return 1.0
    }

    private fun log_3(x: Double): Double {
        return 1.0
    }

    private fun log_2(x: Double): Double {
        return 1.0
    }

}