package domain

class Actor(val firstName: String, val lastName: String, var countFilms: Int, var isExist: Boolean = true) {
    fun getFullName(): String{
        return firstName + lastName
    }

    fun getSize(): Double {
        return countFilms + 0.17 // средний размер человека в километров + количетво фильмов в которых он снялся
    }

    fun destruction() {
        isExist = false
    }
}
