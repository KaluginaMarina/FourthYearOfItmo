import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.lang.Math.PI

open class Functions(var sf: SimpleFunctions) {

    private val CSV_HEADER = "x,f(x),sin(x),cos(x),tan(x),cot(x),sec(x),csc(x)\n"
    private val FILENAME = "out/out.csv"

    open fun systemOfFunctions(x: Double): Double {
        return if (x > 0) f1(x) else f2(x)
    }

    open fun f1(x: Double): Double {
        return (((((sf.log_2(x) * sf.log_2(x)) * sf.log_3(x)) - sf.log_10(x)) * ((sf.log_10(x) * sf.log_10(x) * sf.log_10(
            x
        )) -
                sf.log_2(x))) - (sf.log_3(x) * ((sf.log_2(x) / sf.log_5(x)) - sf.log_3(x))))
    }

    open fun f2(x: Double): Double {
        return (((((sf.csc(x) + sf.sin(x)) - sf.cot(x)) / sf.tan(x)) * ((sf.cot(x) - sf.cos(x)) + sf.tan(x))) -
                ((sf.csc(x) * sf.csc(x)) + sf.sec(x)))
    }

    fun createSCV(from: Double, to: Double, step: Double) {
        var fileWriter: FileWriter?
        try {
            fileWriter = FileWriter(FILENAME)
        } catch (e: FileNotFoundException) {
            File(FILENAME).createNewFile()
            fileWriter = FileWriter(FILENAME)
        }
        fileWriter!!.append(CSV_HEADER)
        var cur = from
        while (cur < to) {
            fileWriter.append(
                "$cur,${systemOfFunctions(cur)},${sf.sin(cur)},${sf.cos(cur)}," +
                        "${sf.tan(cur)},${sf.cot(cur)},${sf.sec(cur)},${sf.csc(cur)}\n"
            )
            cur += step
        }
        fileWriter.flush()
        fileWriter.close()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val functions = Functions(SimpleFunctions())
            functions.createSCV(- 2 * PI, 1.0, 0.05)
            println("The data was written")
        }
    }

}