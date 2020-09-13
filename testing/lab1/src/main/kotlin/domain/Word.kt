package domain

import kotlin.collections.ArrayList

class Word(val name: String, val units: ArrayList<GeographicUnit>, val actors: ArrayList<Actor>) {
    val mcducks = Mcdonalds()
    val dollar = Dollar()

    fun create() {
        God.createWorld()
        val america = GeographicUnit("Америка", 9834000.0, TypeOfGeographicUnit.COUNTRY, true)
        units.add(america)
        God.createGeoUnit(america, 7)
        val england = GeographicUnit("Англия", 130395.0, TypeOfGeographicUnit.COUNTRY, true)
        units.add(england)
        God.createGeoUnit(england, 8)
        val newYork = GeographicUnit("Нью-Йорк", 783800.0, TypeOfGeographicUnit.CITY, false)
        units.add(newYork)
        God.createGeoUnit(newYork, 9)
        val bogart = Actor("Хамфри", "Богарт", 167)
        actors.add(bogart)
        mcducks.create()
    }

    fun destructionWord() {
        for (unit in units) {
            unit.destruction()
            God.destructionGeoUnit(unit)
        }
        for (actor in actors) {
            actor.destruction()
        }
        mcducks.destruction()
    }

    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            val newWord = Word("Земля", ArrayList(), ArrayList())
            newWord.create()
            newWord.destructionWord()
        }
    }

}