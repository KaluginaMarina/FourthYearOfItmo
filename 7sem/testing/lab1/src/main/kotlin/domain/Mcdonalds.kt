package domain

class Mcdonalds(var isExist: Boolean = true) {
    var countMcdonalds = 0
    var hamburgerCount = 0

    fun create(countMd: Int = 37855){
        countMcdonalds = countMd
        hamburgerCount = countMcdonalds * 75 * 60 * 60 //Макдональдс продает более 75 гамбургеров каждую секунду.
        //Будем считать, что за час все приготовленные гамбургеры съедаются
    }

    fun destruction() {
        hamburgerCount = 0
        countMcdonalds = 0
        isExist = false
    }

    fun getSize(): Double {
        return 37855 * 0.05 //На конец 2018 года под торговой маркой McDonald's работало 37 855 ресторанов
    }

    fun getHamburgersSize(): Double {
        return hamburgerCount * 0.000015 * 0.000015 * 0.00001//размером 15 на 15 на 10
    }

}
