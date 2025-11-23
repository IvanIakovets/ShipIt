abstract class Parcel {

    private String description; //  краткое описание
    private int weight; // вес
    private String deliveryAddress; // адрес места назначения посылки
    private int sendDay;// день месяца, в который посылка была отправлена
    private  int baseCost;
    private ParcelsType parcelsType;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getSendDay() {
        return sendDay;
    }

    public void setSendDay(int sendDay) {
        this.sendDay = sendDay;
    }

    public void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    public int getBaseCost() {
        return baseCost;
    }

    public ParcelsType getParcelsType() {
        return parcelsType;
    }

    public void packageItem(String description) {
        System.out.println("Посылка " + description + " упакована.");
    }

    public void deliver(String description, String deliveryAddress) {
        System.out.println("Посылка " + description + " доставлена по адресу " + deliveryAddress + ".");
    }

    public int calculateDeliveryCost (int weight, int baseCost ) {
        return weight * baseCost;
    }

}
