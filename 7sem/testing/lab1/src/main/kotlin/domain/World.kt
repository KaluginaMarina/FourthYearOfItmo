package domain

import kotlin.collections.ArrayList

class World(val name: String, val units: ArrayList<GeographicUnit>, val actors: ArrayList<Actor>) {
    val mcducks = Mcdonalds()
    val dollar = Dollar(74.9)
    var isExist = false

    fun create() {
        if (isExist) {
            throw WordAlreadyExistException()
        }
        God.createWorld()
        val america = GeographicUnit("Америка", 9834000.0, TypeOfGeographicUnit.COUNTRY)
        units.add(america)
        God.createGeoUnit(america, 7)
        val england = GeographicUnit("Англия", 130395.0, TypeOfGeographicUnit.COUNTRY)
        units.add(england)
        God.createGeoUnit(england, 8)
        val newYork = GeographicUnit("Нью-Йорк", 783800.0, TypeOfGeographicUnit.CITY)
        units.add(newYork)
        God.createGeoUnit(newYork, 9)
        val bogart = Actor("Хамфри", "Богарт", 167)
        actors.add(bogart)
        mcducks.create()
        isExist = true
    }

    fun destructionWord() {
        if(!isExist) {
            throw WordNotExistException()
        }
        isExist = false
        for (unit in units) {
            unit.destruction()
            God.destructionGeoUnit(unit)
        }
        for (actor in actors) {
            actor.destruction()
        }
        mcducks.destruction()
        dollar.destruction()
    }
}