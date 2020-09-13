package domain

class BrainControlSystem {
    val MAX_SIZE_FOR_REALIZE = 150000.0

    fun realizeDestruction(size: Double, isExist: Boolean): Boolean{
        return !isExist && size < MAX_SIZE_FOR_REALIZE
    }

    fun checkUnit(unit: GeographicUnit): String {
        return if (unit.isExists) "${unit.name} больше не существует." else "${unit.name} все еще сущесвует"
    }

    fun realizeDestructionGeoUnit(unit: GeographicUnit): String {
        return if (realizeDestruction(unit.size, unit.isExists)) "${checkUnit(unit)} Он смог это осознать."
        else "Это не охватывается. Мне кажется, ${checkUnit(unit)}."
    }

    fun checkDollar(dollar: Dollar): String{
        return if (dollar.value > 0) "Доллар упал. Совсем упал. " +
                (if (realizeDestruction(dollar.getSize(), dollar.isExist)) "Что-то ощущается." else "Все хорошо.")
        else "С долларом все ок."
    }
    

}