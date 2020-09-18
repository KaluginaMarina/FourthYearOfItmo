import domain.GeographicUnit
import domain.God
import domain.TypeOfGeographicUnit
import domain.Word
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class DomainTest {

    val standardOut = System.out
    val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

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

    @Test
    fun testCreatingWorldDollar(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()
        assert(newWorld.dollar.value > 0, { "После создания мира, доллар должен быть больше 0." })
        assertEquals(true, newWorld.dollar.isExist, "При создании мира доллар должен существовать.")
    }

    @Test
    fun testCreatingActors(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()
        for (actor in newWorld.actors) {
            assertNotNull(actor.lastName, "Фамилия актера должна существовать.")
            assertNotNull(actor.firstName, "Имя актера должно существовать. Ошибка с актером ${actor.lastName}.")
            assert(actor.isExist, {"После создания мира актер должен существовать. Ошибка с актером ${actor.lastName}."})
            assert(actor.countFilms >= 0, {"Количество фильмов должно быть больше или рабно 0. Ошибка с актером ${actor.lastName}."})
        }
    }

    @Test
    fun testCreatingGeoUnit(){
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()

        for (geoUnit in newWorld.units) {
            assertNotNull(geoUnit.name, "Имя у созданного географического объекта должно существовать")
            assert(geoUnit.size > 0, {"Размер созданного географического объекта должен быть больше 0"})
            assert(geoUnit.type in TypeOfGeographicUnit.values(), {"Тип географического объекта должен быть из TypeOfGeographicUnit"})
            assert(geoUnit.isExists, {"Только что созданый географический объект должен существовать"})
        }
    }

    @Test
    fun testCreateMcDonalds() {
        val newWorld = Word("Земля", ArrayList(), ArrayList())
        newWorld.create()

        assert(newWorld.mcducks.isExist, {"Только что созданный магазин макдональдс должен существовать"})
        assert(newWorld.mcducks.hamburgerCount >= 0, {"Число гамбургеров в макдаке не может быть отрицательным"})
        assert(newWorld.mcducks.countMcdonalds >= 0, {"Колтчество макдаков не может быть мельне 0"})
        assert(newWorld.mcducks.getSize() > 0, {"Занимаемая площадь макдаков на планете должен быть больше 0"})
    }

    @Test
    fun testGodCreatingWorld(){
        God.createWorld()
        assertEquals("На первый день Бог создал небо, землю и свет, и отделил свет от тьмы;\n" +
                "на второй день — создал твердь посреди воды, отделил воду над твердью от воды под твердью, и назвал " +
                "твердь небом; \nна третий — сушу, моря и растения, \nна четвёртый — светила на тверди небесной, \nна пятый — " +
                "рыб, пресмыкающихся и птиц. \nНаконец, на шестой — зверей земных, скот, гадов земных и человека.",
            outputStreamCaptor.toString().trim(),
            "Сказанное Богом не соответствует тексту в Библии."
        )
    }

    @Test
    fun testGodGeoUnit(){
        God.createGeoUnit(GeographicUnit("Бали", 10000.0, TypeOfGeographicUnit.COUNTRY), 6)
        assertEquals("На 6 день создал Бог место под названием Бали, размером 10000.0 км^2",
            outputStreamCaptor.toString().trim(),
            "При создании географического объекта Бог говорит что-то не то."
        )
    }

    @Test
    fun testGodDistractionGeoUnit(){
        God.destructionGeoUnit(GeographicUnit("Бали", 10000.0, TypeOfGeographicUnit.COUNTRY))
        assertEquals("Место с названием Бали было уничтожено.",
            outputStreamCaptor.toString().trim(),
            "При уничтожении географического объекта Бог говорит что-то не то."
        )
    }
}