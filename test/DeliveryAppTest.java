import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeliveryAppTest {
    @Test
    void testStanCalculateDeliveryCost() {
        StandardParcel standardParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5); // Создаём стандартную посылку весом 5 кг
        double expectedCost = 5 * 2; // Пример базовой стоимости для стандартной посылки
        Assertions.assertEquals(expectedCost, standardParcel.calculateDeliveryCost(standardParcel.getWeight(), standardParcel.getBaseCost())); // Проверяем, что вычисленная стоимость соответствует ожидаемой
    }

    @Test
    void testPerCalculateDeliveryCost() {
        PerishableParcel perishableParcel = new PerishableParcel("TestTitle", 5, "TestAddress", 5, 5); // Создаём стандартную посылку весом 5 кг
        double expectedCost = 5 * 3; // Пример базовой стоимости для скоропортящейся посылки
        Assertions.assertEquals(expectedCost, perishableParcel.calculateDeliveryCost(perishableParcel.getWeight(), perishableParcel.getBaseCost())); // Проверяем, что вычисленная стоимость соответствует ожидаемой
    }

    @Test
    void testFragCalculateDeliveryCost() {
        FragileParcel fragileParcel = new FragileParcel("TestTitle", 5, "TestAddress", 5); // Создаём хрупкую посылку весом 5 кг
        double expectedCost = 5 * 4; // Пример базовой стоимости для хрупкой посылки
        Assertions.assertEquals(expectedCost, fragileParcel.calculateDeliveryCost(fragileParcel.getWeight(), fragileParcel.getBaseCost())); // Проверяем, что вычисленная стоимость соответствует ожидаемой
    }

    @Test
    void testParcelIsNotExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("TestTitle", 5, "TestAddress", 5, 5);
        int currentDay = 7;
        Assertions.assertTrue(perishableParcel.isExpired(currentDay, perishableParcel.getSendDay(), perishableParcel.getTimeToLive())); // Проверяем, что для неистекшей посылки метод возвращает false
    }

    @Test
    void testParcelIsExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("TestTitle", 5, "TestAddress", 5, 5);
        int currentDay = 11;
        Assertions.assertFalse(perishableParcel.isExpired(currentDay, perishableParcel.getSendDay(), perishableParcel.getTimeToLive())); // Проверяем, что для истёкшей посылки метод возвращает true
    }

    @Test
    void testParcelAtDayExpired() {
        PerishableParcel perishableParcel = new PerishableParcel("TestTitle", 5, "TestAddress", 5, 5);
        int currentDay = 10;
        Assertions.assertTrue(perishableParcel.isExpired(currentDay, perishableParcel.getSendDay(), perishableParcel.getTimeToLive())); // Проверяем, что для неистекшей посылки метод возвращает false
    }

    @Test
    void testAddStanParcelWithinWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг
        StandardParcel standardParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5);
        Assertions.assertTrue(parcelBox.addParcel(standardParcel)); // Проверяем, что посылка добавляется успешно
        Assertions.assertTrue(parcelBox.getParcelBox().contains(standardParcel)); // Проверяем, что посылка действительно добавлена
    }

    @Test
    void testAddStanParcelExceedingWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг
        StandardParcel heavyParcel = new StandardParcel("TestTitle", 15, "TestAddress", 5); // Создаём тяжёлую посылку весом 20 кг
        Assertions.assertFalse(parcelBox.addParcel(heavyParcel)); // Проверяем, что попытка добавления посылки не удаётся
        Assertions.assertFalse(parcelBox.getParcelBox().contains(heavyParcel)); // Проверяем, что тяжёлая посылка не добавлена
    }

    @Test
    void testAddStanParcelEqualsWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг// Создаём коробку с максимальным весом 10 кг
        StandardParcel heavyParcel = new StandardParcel("TestTitle", 15, "TestAddress", 5); // Создаём тяжёлую посылку весом 20 кг
        Assertions.assertTrue(parcelBox.addParcel(heavyParcel)); // Проверяем, что попытка добавления посылки не удаётся
        Assertions.assertTrue(parcelBox.getParcelBox().contains(heavyParcel)); // Проверяем, что тяжёлая посылка не добавлена
    }

    @Test
    void testAddTwoStanParcelWithinWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг
        StandardParcel oneStandardParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5);
        StandardParcel twoStandardParcel = new StandardParcel("TestTitle", 2, "TestAddress", 5);
        Assertions.assertTrue(parcelBox.addParcel(oneStandardParcel));
        Assertions.assertTrue(parcelBox.addParcel(twoStandardParcel)); // Проверяем, что посылка добавляется успешно
        Assertions.assertTrue(parcelBox.getParcelBox().contains(oneStandardParcel));
        Assertions.assertTrue(parcelBox.getParcelBox().contains(twoStandardParcel)); // Проверяем, что посылка действительно добавлена
    }

    @Test
    void testAddTwoStanParcelExceedingWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг
        StandardParcel oneHeavyParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5);
        StandardParcel twoHeavyParcel = new StandardParcel("TestTitle", 7, "TestAddress", 5); // Создаём тяжёлую посылку весом 20 кг
        Assertions.assertTrue(parcelBox.addParcel(oneHeavyParcel)); // Проверяем, что попытка добавления посылки не удаётся
        Assertions.assertFalse(parcelBox.addParcel(twoHeavyParcel));
        Assertions.assertTrue(parcelBox.getParcelBox().contains(oneHeavyParcel));
        Assertions.assertFalse(parcelBox.getParcelBox().contains(twoHeavyParcel)); // Проверяем, что тяжёлая посылка не добавлена
    }

    @Test
    void testAddTwoStanParcelEqualsWeightLimit() {
        ParcelBox parcelBox = new ParcelBox(ParcelsType.STANDARD, 10); // Создаём коробку с максимальным весом 10 кг// Создаём коробку с максимальным весом 10 кг
        StandardParcel oneHeavyParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5);
        StandardParcel twoHeavyParcel = new StandardParcel("TestTitle", 5, "TestAddress", 5);// Создаём тяжёлую посылку весом 20 кг
        Assertions.assertTrue(parcelBox.addParcel(oneHeavyParcel)); // Проверяем, что попытка добавления посылки не удаётся
        Assertions.assertTrue(parcelBox.addParcel(twoHeavyParcel));
        Assertions.assertTrue(parcelBox.getParcelBox().contains(oneHeavyParcel));
        Assertions.assertTrue(parcelBox.getParcelBox().contains(twoHeavyParcel)); // Проверяем, что тяжёлая посылка не добавлена
    }

}
