package domain

class Actor(val firstName: String, val lastName: String, var countFilms: Int, var isExist: Boolean = true) {
    fun getFullName(): String{
        return firstName + lastName
    }

    private fun removeAllFilmsWithActor() {
        countFilms = 0
    }

    fun getSize(): Double {
        return countFilms + 0.17 // средний размер человека в километров + количетво фильмов в которых он снялся
    }

    fun destruction() {
        removeAllFilmsWithActor()
        isExist = false
    }
}
