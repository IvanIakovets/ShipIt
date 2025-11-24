import java.util.ArrayList;

public class ParcelBox {
    private  ParcelsType parcelsType;
    private  int maxBoxWeight;
    private  int totalBoxWeight = 0;

    private ArrayList<Parcel> parcelBox = new ArrayList<>();

    public ParcelBox(ParcelsType parcelsType, int maxBoxWeight) {
        this.parcelsType = parcelsType;
        this.maxBoxWeight = maxBoxWeight;
    }

    public ArrayList<Parcel> getParcelBox() {
        return parcelBox;
    }

    protected boolean addParcel(Parcel parcel) {
        int safeBoxSpace = maxBoxWeight-totalBoxWeight;
        if (maxBoxWeight <= totalBoxWeight) {
            parcelBox.add(parcel);
            System.out.println("Посылка " + parcel.getDescription() +
                    " успешно добавлена в коробку " + parcelsType);
            totalBoxWeight += parcel.getWeight();
            System.out.println("Осталось свободного места: " + safeBoxSpace);
            return true;
        } else {
            System.out.println("Посылка слишком тяжелая, попробуйте добавить другую. \n" +
                    "Осталось свободного веса: " + safeBoxSpace);
            System.out.println("Если посылок с подходящим весом нет, то создайте новую коробку.");
            return false;
        }
    }

    protected void getAllParcels () {
        System.out.println("Начинаю распаковывать коробку!");
        System.out.println("Всего будет распаковано " + parcelBox.size() + " посылок из коробки");
        ArrayList<String> unpackingParcel = new ArrayList<>();
        for (Parcel parcel : parcelBox) {
            unpackingParcel.add(parcel.getDescription());
        }
        System.out.println("Были распакованы следующие посылки:");
        for (String string : unpackingParcel) {
            System.out.println(string);
        }
        parcelBox.clear();
        System.out.println("Коробка типа " + parcelsType + " пуста, допустимый вес: " + maxBoxWeight);
    }


}
