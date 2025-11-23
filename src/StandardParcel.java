public class StandardParcel extends Parcel {

    final int baseCost = 2;// базовая стоимость одной единицы отправления
    private ParcelsType parcelsType = ParcelsType.STANDARD;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
        setBaseCost(baseCost);
    }


}
