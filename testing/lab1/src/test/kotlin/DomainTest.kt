import domain.TypeOfGeographicUnit
import domain.Word
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

class DomainTest {
    @Test
    fun testCreatingWorldGeoUnit(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()
        for (unit in newWorld.units) {
            assertNotNull(unit.name, "Не указано название географической единицы.")
            assertEquals(true, unit.isExists,
                "При создании мира все географические объекты должны существовать. Ошибка при создании ${unit.name}.")
            assert(unit.size > 0,
                { "Размер существующего географического объекта должен быть больше 0. Ошибка при создании ${unit.name}." })
            assert(unit.type in TypeOfGeographicUnit.values(),
                { "Тип географического объекта должен быть из TypeOfGeographicUnit. Ошибка при создании ${unit.name}." })
        }
    }

    fun testCreatingWorldDollar(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()
        assert(newWorld.dollar.value > 0, { "После создания мира, доллар должен быть больше 0." })
        assertEquals(true, newWorld.dollar.isExist, "При создании мира доллар должен существовать.")
    }

    fun testCreatingWorldActors(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()
        for (actor in newWorld.actors) {
            assertNotNull(actor.lastName, "Фамилия актера должна существовать.")
            assertNotNull(actor.firstName, "Имя актера должно существовать. Ошибка с актером ${actor.lastName}.")
            assert(actor.isExist, {"После создания мира актер должен существовать. Ошибка с актером ${actor.lastName}."})
            assert(actor.countFilms >= 0, {"Количество фильмов должно быть больше или рабно 0. Ошибка с актером ${actor.lastName}."})
        }
    }
}