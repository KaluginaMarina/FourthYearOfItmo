import java.lang.Math.PI
import java.lang.Math.abs

class Function {
    companion object {
        const val PRECISION = 1E-8

        fun arcsin(x: Double): Double? {
            var current = x
            var result = 0.0
            var n = 1
            if (x == 1.0) {
                return PI / 2
            } else if (x == -1.0) {
                return -PI / 2
            } else if (abs(x) < 1) {
                while (abs(current) >= PRECISION / 10) {
                    result += current
                    current = current * x * x * (2 * n - 1) * (2 * n - 1) / ((2 * n) * (2 * n + 1))
                    n++
                }
            } else {
                return null
            }
            return result
        }
    }
}