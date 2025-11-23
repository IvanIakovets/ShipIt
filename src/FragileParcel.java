public class FragileParcel extends Parcel implements Trackable {

    final int baseCost = 4;
    private String location = "Почтовое отделение";// базовая стоимость одной единицы отправления
    private String newLocation = "";
    private ParcelsType parcelsType = ParcelsType.FRAGILE;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    public int getBaseCost() {
        return baseCost;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void packageItem(String description) {
        System.out.println("Посылка " + description + " обёрнута в защитную плёнку");
        System.out.println("Посылка " + description + " упакована.");
    }

    @Override
    public void reportStatus() {
        if (newLocation.isBlank()) {
            System.out.println("Местоположение посылки " + getDescription() + ": " + location);
        } else {
            System.out.println("Хрупкая посылка " + getDescription() + " изменила местоположение на " + getNewLocation());
            setLocation(newLocation);
        }
    }

}
