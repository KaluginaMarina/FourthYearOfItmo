package domain

class Dollar(var value: Double){

    fun changeValue(newValue: Double){
        this.value = newValue
    }

    fun getSize(): Double{
        return 0.000156 * 0.000066 * 0.0000001 * 1740000000000
                                  //Все банкноты долларов США имеют один стандартный размер - 155.956 на 66.294 мм
                                  // Согласно Федеральной резервной системе (ФРС), на данный момент в обращении находится $1.74 трлн бумажных долларов.
                                  // Толщина среднего листа бумаги составляет 1/10 миллиметра.
    }

    fun destruction(){
        changeValue(0.0)
    }
}