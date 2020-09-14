package domain

open class GeographicUnit(val name: String, val size: Double, val type: TypeOfGeographicUnit, var isExists: Boolean = true) {
    fun destruction() {
        isExists = false
    }
}