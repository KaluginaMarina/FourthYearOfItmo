import java.lang.Math.abs

class Function {
    companion object {
        const val PRECISION = 1E-16

        fun arcsin(x: Double): Double? {
            var current = x
            var result = 0.0
            var n = 1
            if (abs(x) <= 1) {
                while (current >= PRECISION) {
                    result += current
                    current = -current * x * x / (2 * n++ + 1)
                }
            } else {
                return null
            }
            return Math.asin(x)
        }
    }
}