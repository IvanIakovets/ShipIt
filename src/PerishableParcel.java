public class PerishableParcel extends Parcel {

    private int timeToLive; // срок в днях, за который посылка не испортится
    final int baseCost = 3; // базовая стоимость одной единицы отправления
    private ParcelsType parcelsType = ParcelsType.PERISHABLE;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getBaseCost() {
        return baseCost;
    }

    protected boolean isExpired(int currentDay, int sendDay, int timeToLive) {
        return currentDay <= (sendDay + timeToLive);
    }

}
