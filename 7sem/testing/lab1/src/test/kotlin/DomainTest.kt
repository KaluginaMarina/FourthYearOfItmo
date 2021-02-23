import domain.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    fun testCreatingWord() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        assert(!newWorld.isExist, { "Перед созданием isExist долен быть равен false" })
        newWorld.create()
        assert(newWorld.isExist, { "После создания isExist долен быть равен true" })
        newWorld.destructionWord()
        assert(!newWorld.isExist, { "После уничтодения isExist долен быть равен false" })
    }

    @Test
    fun testCreatingWorldGeoUnit() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        for (unit in newWorld.units) {
            assertNotNull(unit.name, "Не указано название географической единицы.")
            assertEquals(
                true, unit.isExists,
                "При создании мира все географические объекты должны существовать. Ошибка при создании ${unit.name}."
            )
            assert(unit.size > 0,
                { "Размер существующего географического объекта должен быть больше 0. Ошибка при создании ${unit.name}." })
            assert(unit.type in TypeOfGeographicUnit.values(),
                { "Тип географического объекта должен быть из TypeOfGeographicUnit. Ошибка при создании ${unit.name}." })
        }
    }

    @Test
    fun testCreatingWorldDollar() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        assert(newWorld.dollar.value > 0, { "После создания мира, доллар должен быть больше 0." })
        assertEquals(true, newWorld.dollar.isExist, "При создании мира доллар должен существовать.")
    }

    @Test
    fun testCreatingActors() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        for (actor in newWorld.actors) {
            assertNotNull(actor.lastName, "Фамилия актера должна существовать.")
            assertNotNull(actor.firstName, "Имя актера должно существовать. Ошибка с актером ${actor.lastName}.")
            assert(
                actor.isExist,
                { "После создания мира актер должен существовать. Ошибка с актером ${actor.lastName}." })
            assert(
                actor.countFilms >= 0,
                { "Количество фильмов должно быть больше или рабно 0. Ошибка с актером ${actor.lastName}." })
        }
    }

    @Test
    fun testCreatingGeoUnit() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()

        for (geoUnit in newWorld.units) {
            assertNotNull(geoUnit.name, "Имя у созданного географического объекта должно существовать")
            assert(geoUnit.size > 0, { "Размер созданного географического объекта должен быть больше 0" })
            assert(
                geoUnit.type in TypeOfGeographicUnit.values(),
                { "Тип географического объекта должен быть из TypeOfGeographicUnit" })
            assert(geoUnit.isExists, { "Только что созданый географический объект должен существовать" })
        }
    }

    @Test
    fun testCreateMcDonalds() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()

        assert(newWorld.mcducks.isExist, { "Только что созданный магазин макдональдс должен существовать" })
        assert(newWorld.mcducks.hamburgerCount >= 0, { "Число гамбургеров в макдаке не может быть отрицательным" })
        assert(newWorld.mcducks.countMcdonalds >= 0, { "Колтчество макдаков не может быть мельне 0" })
        assert(newWorld.mcducks.getSize() > 0, { "Занимаемая площадь макдаков на планете должен быть больше 0" })
    }

    @Test
    fun testGodCreatingWorld() {
        God.createWorld()
        assertEquals(
            "На первый день Бог создал небо, землю и свет, и отделил свет от тьмы;\n" +
                    "на второй день — создал твердь посреди воды, отделил воду над твердью от воды под твердью, и назвал " +
                    "твердь небом; \nна третий — сушу, моря и растения, \nна четвёртый — светила на тверди небесной, \nна пятый — " +
                    "рыб, пресмыкающихся и птиц. \nНаконец, на шестой — зверей земных, скот, гадов земных и человека.",
            outputStreamCaptor.toString().trim(),
            "Сказанное Богом не соответствует тексту в Библии."
        )
    }

    @Test
    fun testGodGeoUnit() {
        God.createGeoUnit(GeographicUnit("Бали", 5780.0, TypeOfGeographicUnit.COUNTRY), 6)
        assertEquals(
            "На 6 день создал Бог место под названием Бали, размером 5780.0 км^2",
            outputStreamCaptor.toString().trim(),
            "При создании географического объекта Бог говорит что-то не то."
        )
    }

    @Test
    fun testGodDistractionGeoUnit() {
        God.destructionGeoUnit(GeographicUnit("Бали", 5780.0, TypeOfGeographicUnit.COUNTRY))
        assertEquals(
            "Место с названием Бали было уничтожено.",
            outputStreamCaptor.toString().trim(),
            "При уничтожении географического объекта Бог говорит что-то не то."
        )
    }

    @Test
    fun testBrainControlSystem() {
        val brain = BrainControlSystem()
        assert(brain.MAX_SIZE_FOR_REALIZE >= 0, { "Размер не может быть отрицательным" })
    }

    @Test
    fun testRealizeDestructionBrainControlSystemPositiveTest() {
        val brain = BrainControlSystem()

        assertEquals(
            false,
            brain.realizeDestruction(1.0, true),
            "[size == 1.0,isExist == true] нельзя осознать, что объект не существует, если он существует."
        )
        assertEquals(
            false,
            brain.realizeDestruction(brain.MAX_SIZE_FOR_REALIZE / 10, true),
            "[size == brain.MAX_SIZE_FOR_REALIZE / 10,isExist == true] нельзя осознать, что объект не существует, если он существует."
        )
        assertEquals(
            false,
            brain.realizeDestruction(0.0, true),
            "[size == 0.0,isExist == true] нельзя осознать, что объект не существует, если он существует."
        )
        assertEquals(
            false,
            brain.realizeDestruction(Double.MAX_VALUE, true),
            "[size == MAX_VALUE,isExist == true] нельзя осознать, что объект не существует, если он существует."
        )
        assertEquals(
            false,
            brain.realizeDestruction(Double.MIN_VALUE, true),
            "[size == MIN_VALUE,isExist == true] нельзя осознать, что объект не существует, если он существует."
        )

    }

    @Test
    fun testRealizeDestructionBrainControlSystemExistFalse() {
        val brain = BrainControlSystem()

        assertEquals(
            1.0 <= brain.MAX_SIZE_FOR_REALIZE,
            brain.realizeDestruction(1.0, false),
            "[size == 1.0, MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
        assertEquals(
            true,
            brain.realizeDestruction(brain.MAX_SIZE_FOR_REALIZE / 10, false),
            "[size == brain.MAX_SIZE_FOR_REALIZE / 10, MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
        assertEquals(
            0.0 <= brain.MAX_SIZE_FOR_REALIZE,
            brain.realizeDestruction(0.0, false),
            "[size == 0.0, MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
        assertEquals(
            Double.MAX_VALUE <= brain.MAX_SIZE_FOR_REALIZE,
            brain.realizeDestruction(Double.MAX_VALUE, false),
            "[size == MAX_VALUE, MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
        assertEquals(
            Double.MIN_VALUE <= brain.MAX_SIZE_FOR_REALIZE,
            brain.realizeDestruction(Double.MIN_VALUE, false),
            "[size == MIN_VALUE, MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
        assertEquals(
            true,
            brain.realizeDestruction(brain.MAX_SIZE_FOR_REALIZE, false),
            "[size == MAX_SIZE_FOR_REALIZE == ${brain.MAX_SIZE_FOR_REALIZE}, isExist == False], размер должен быть меньше или равен, чем максимальный размер, который можно осознать"
        )
    }

    @Test
    fun testBrainControlSystemCheckPositiveCorrectUnit() {
        val brain = BrainControlSystem()
        val unit = GeographicUnit("Москва", 2511.0, TypeOfGeographicUnit.CITY)

        assertEquals(
            "Москва все еще существует.",
            brain.checkUnit(unit),
            "Существующий юнит должен существовать. Нельзя осознать, что он разрушен."
        )
    }

    @Test
    fun testBrainControlSystemCheckSmallDestroyedUnit() {
        val brain = BrainControlSystem()
        val unit = GeographicUnit("Очень маленький город", 0.1, TypeOfGeographicUnit.CITY, false)

        assertEquals(
            "Очень маленький город больше не существует.",
            brain.checkUnit(unit),
            "Разрушенный маленький город должен быть осознан героем. MAX_SIZE_FOR_REALIZE = ${brain.MAX_SIZE_FOR_REALIZE}"
        )
    }

    @Test
    fun testBrainControlSystemCheckDestroyedUnit() {
        val brain = BrainControlSystem()
        val unit = GeographicUnit("Очень большой город", Double.MAX_VALUE, TypeOfGeographicUnit.CITY, false)

        assertEquals(
            "Очень большой город все еще существует.",
            brain.checkUnit(unit),
            "Разрушенный большой город не может быть осознан героем. MAX_SIZE_FOR_REALIZE = ${brain.MAX_SIZE_FOR_REALIZE}"
        )
    }

    @Test
    fun testDestructionWorld() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        newWorld.destructionWord()
        assert(!newWorld.isExist, { "После уничтожения мира isExist должен быть равен false" })
        assert(!newWorld.mcducks.isExist, { "После уничтожения мира макдональдсы в нем не должны существовать" })
        assert(!newWorld.dollar.isExist, { "После уничтожения мира доллары в нем не должны существовать" })
        for (unit in newWorld.units) {
            assert(!unit.isExists, { "После уничтожения мира географические единицы в нем не должны существовать" })
        }
        for (actor in newWorld.actors) {
            assert(!actor.isExist, { "После уничтожения мира актеры в нем не должны существовать" })
        }
    }

    @Test
    fun testDestructionNotExistWorld() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        assertThrows<WordNotExistException> { newWorld.destructionWord() }
    }

    @Test
    fun testCreatingAlreadyExistWorld() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        assertThrows<WordAlreadyExistException> { newWorld.create() }
    }

    @Test
    fun testDestroyedActor() {
        val actor = Actor("Иван", "Пупкин", 100)
        actor.destruction()
        assert(!actor.isExist, { "После уничтожение актера он больше не может существовать" })
    }

    @Test
    fun testDestructionDollar() {
        val dollar = Dollar(Double.MAX_VALUE)
        dollar.destruction()
        assert(!dollar.isExist, { "После уничтожения доллара он не может существовать" })
    }

    @Test
    fun testDestructionGeoUnit() {
        val unit = GeographicUnit("Москва", 2511.0, TypeOfGeographicUnit.CITY)
        unit.destruction()
        assert(!unit.isExists, { "После уничтожения географический объект не может существовать" })
    }

    @Test
    fun testDestructionMcdonalds() {
        val mcdonalds = Mcdonalds()
        mcdonalds.destruction()
        assert(!mcdonalds.isExist, { "После уничтожения макдональдс не может существовать" })
    }

    @Test
    fun testDestructionWorldAtribittes() {
        val newWorld = World("Земля", ArrayList(), ArrayList())
        newWorld.create()
        newWorld.destructionWord()
        assert(!newWorld.mcducks.isExist,
            { "При уничтожении мира должны уничтожиться все его составляющие, в том числе макдак" })
        assert(!newWorld.dollar.isExist,
            { "При уничтожении мира должны уничтожиться все его составляющие, в том числе доллар" })
        for (unit in newWorld.units) {
            assert(!unit.isExists,
                { "При уничтожении мира должны уничтожиться все его составляющие, в том числе географический объект ${unit.name}" })
        }
        for (actor in newWorld.actors) {
            assert(!actor.isExist,
                { "При уничтожении мира должны уничтожиться все его составляющие, в том числе актер: ${actor.lastName} ${actor.firstName}" })
        }
    }

    @Test
    fun testheckExistGeoUnitDestructing() {
        val brain = BrainControlSystem()
        val smallUnit = GeographicUnit("Очень маленький город", 0.1, TypeOfGeographicUnit.CITY, true)
        val bigUnit = GeographicUnit("Очень большой город", Double.MAX_VALUE, TypeOfGeographicUnit.CITY, true)
        assertEquals(
            "Очень маленький город все еще существует.",
            brain.checkUnit(smallUnit),
            "Нельзя осознать, что не уничтоженный маленький город уничтожен"
        )
        assertEquals(
            "Очень большой город все еще существует.",
            brain.checkUnit(bigUnit),
            "Нельзя осознать, что не уничтоженный большой город уничтожен."
        )
    }

    @Test
    fun testCheckNotExistSmallGeoUnitDestructing() {
        val brain = BrainControlSystem()
        val smallUnit =
            GeographicUnit("Очень маленький город", brain.MAX_SIZE_FOR_REALIZE / 10, TypeOfGeographicUnit.CITY, false)
        assertEquals(
            "Очень маленький город больше не существует.",
            brain.checkUnit(smallUnit),
            "Уничтожение не существующего маленького города герой может осознать"
        )
    }

    @Test
    fun testCheckNotExistBigGeoUnitDestructing() {
        val brain = BrainControlSystem()
        val smallUnit =
            GeographicUnit("Очень большой город", Double.MAX_VALUE, TypeOfGeographicUnit.CITY, false)
        assertEquals(
            "Очень большой город все еще существует.",
            brain.checkUnit(smallUnit),
            "Уничтожение не существующего большого города герой не может осознать "
        )
    }

    @Test
    fun testRealizeDestructionExistsGeoUnit() {
        val brain = BrainControlSystem()
        val smallUnit = GeographicUnit("Очень маленький город", 0.1, TypeOfGeographicUnit.CITY, true)
        val bigUnit = GeographicUnit("Очень большой город", Double.MAX_VALUE, TypeOfGeographicUnit.CITY, true)
        assertEquals(
            "Это не охватывается. Мне кажется, Очень маленький город все еще существует.",
            brain.realizeDestructionGeoUnit(smallUnit),
            "Нельзя осознать, что не уничтоженный маленький город уничтожен"
        )
        assertEquals(
            "Это не охватывается. Мне кажется, Очень большой город все еще существует.",
            brain.realizeDestructionGeoUnit(bigUnit),
            "Нельзя осознать, что не уничтоженный большой город уничтожен."
        )
    }

    @Test
    fun testRealizeNotExistSmallGeoUnitDestructing() {
        val brain = BrainControlSystem()
        val smallUnit =
            GeographicUnit("Очень маленький город", brain.MAX_SIZE_FOR_REALIZE / 10, TypeOfGeographicUnit.CITY)
        smallUnit.destruction()
        assertEquals(
            "Очень маленький город больше не существует. Он смог это осознать.",
            brain.realizeDestructionGeoUnit(smallUnit),
            "Уничтожение не существующего маленького города герой может осознать"
        )
    }

    @Test
    fun testRealizeNotExistBigGeoUnitDestructing() {
        val brain = BrainControlSystem()
        val bigUnit =
            GeographicUnit("Очень большой город", Double.MAX_VALUE, TypeOfGeographicUnit.CITY)
        bigUnit.destruction()
        assertEquals(
            "Это не охватывается. Мне кажется, Очень большой город все еще существует.",
            brain.realizeDestructionGeoUnit(bigUnit),
            "Уничтожение не существующего большого города герой не может осознать "
        )
    }

    @Test
    fun testCheckExistsDollar() {
        val brain = BrainControlSystem()
        val dollar = Dollar(74.0)

        assertEquals(
            "Все хорошо.",
            brain.checkDollar(dollar),
            "Если доллар все еще существует, то с ним должно быть все хорошо"
        )
    }

    @Test
    fun testCheckNotExistsDollar() {
        val brain = BrainControlSystem()
        val dollar = Dollar(74.0)
        dollar.destruction()
        assertEquals(
            "Что-то ощущается.",
            brain.checkDollar(dollar),
            "Если доллар уничтожен, то должно что-то ощущаться"
        )
    }

    @Test
    fun testCheckExistsActor() {
        val brain = BrainControlSystem()
        val actor = Actor("Вася", "Пупкин", 1)

        assertEquals(
            "Пойду посмотрю еще один фильм с Пупкин.",
            brain.checkActor(actor),
            "Так как актер существует нельзя осознать того, что его нет"
        )
    }

    @Test
    fun testCheckFamousActor() {
        val brain = BrainControlSystem()
        val actor = Actor("Джекки", "Чан", brain.MAX_SIZE_FOR_REALIZE.toInt())
        actor.destruction()

        assertEquals(
            "Пойду посмотрю еще один фильм с Чан.",
            brain.checkActor(actor),
            "Так как объем вклада актера слишклм велик, то нельзя осознать того, что он уничтожен"
        )
    }

    @Test
    fun testCheckNotFamousActor() {
        val brain = BrainControlSystem()
        val actor = Actor("Вася", "Пупкин", (brain.MAX_SIZE_FOR_REALIZE / 10).toInt())
        actor.destruction()

        assertEquals(
            "Все фильмы с Пупкин пропали.",
            brain.checkActor(actor),
            "Не сильно знаменитого уничтоженного актера можно осознать"
        )
    }

    @Test
    fun testExistMcduck() {
        val brain = BrainControlSystem()
        val mcdonalds = Mcdonalds()

        assertEquals("Макдональдс!", brain.checkMcduck(mcdonalds), "Если макдак существует, то все хорошо")
    }

    @Test
    fun testNotExistMcduck() {
        val brain = BrainControlSystem()
        val mcdonalds = Mcdonalds()
        mcdonalds.destruction()

        assertEquals(
            "Макдональдс... Больше никогда не будет маковских гамбургеров...",
            brain.checkMcduck(mcdonalds),
            "Если макдональдса больше не существует, то ты некогда не сможешь попробовать макдаковских булочек"
        )
    }
}