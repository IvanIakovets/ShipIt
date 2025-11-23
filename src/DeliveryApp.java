import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Parcel> allParcels = new ArrayList<>();
    private static ArrayList<Parcel> trackingParcels = new ArrayList<>();
    private static ParcelBox standardParcelBox = new ParcelBox( ParcelsType.STANDARD, 50);
    private static ParcelBox perishableParcelBox = new ParcelBox( ParcelsType.PERISHABLE, 50);
    private static ParcelBox fragileParcelBox = new ParcelBox( ParcelsType.FRAGILE, 50);


    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addParcel();
                case 2 -> sendParcels();
                case 3 -> calculateCosts();
                case 4 -> reportStatus();
                case 5 -> changeTrackingStatus();
                case 6 -> addParcelInBox();
                case 7 -> getAllParcels();
                case 8 -> perishableParcelCheck();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить все хрупкие посылки");
        System.out.println("5 — Внести изменения в местоположение отправленной посылки");
        System.out.println("6 — Добавить посылки в коробку");
        System.out.println("7 — Распаковать все посылки из коробки");
        System.out.println("8 — Проверить испортились ли скоропортящаяся посылки");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {

        // Подсказка: спросите тип посылки и необходимые поля, создайте объект и добавьте в allParcels
        int choiceParcel = 0;
        String description = "";
        int weight = 0;
        String deliveryAddress = "";
        int sendDay = 0;

        List<Integer> activeMenu = List.of(1, 2, 3, 4);
        while (!activeMenu.contains(choiceParcel)) {
            System.out.println("Какой вид посылки хотите отправить? \n" +
                    "1 - стандартная посылка.\n" +
                    "2 - хрупкая посылка.\n" +
                    "3 - скоропортящаяся посылка.\n" +
                    "4 - вернуться в главное меню."
            );
            choiceParcel = Integer.parseInt(scanner.nextLine());
        }
        if (choiceParcel == 4) {
            return;
        }

        while (description.isBlank()) {
            System.out.println("Укажите описание посылки.");
            description = scanner.nextLine();
        }

        while (!(weight > 0)) {
            System.out.println("Укажите вес посылки в килограммах.");
            weight = (int) Double.parseDouble(scanner.nextLine());
        }

        while (deliveryAddress.isBlank()) {
            System.out.println("Укажите адрес доставки посылки.");
            deliveryAddress = scanner.nextLine();
        }
        while (sendDay < 1 || sendDay > 31) {
            System.out.println("Введите дату отправки посылки.");
            sendDay = (int) Double.parseDouble(scanner.nextLine());
        }

        if (choiceParcel == 1) {
            StandardParcel sendParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
            allParcels.add(sendParcel);
        } else if (choiceParcel == 2) {
            FragileParcel sendParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
            allParcels.add(sendParcel);
            trackingParcels.add(sendParcel);
        } else if (choiceParcel == 3) {
            int timeToLive = 0;
            while ((timeToLive < 1 || timeToLive > 31) && (timeToLive + sendDay) < 31) {
                System.out.println("Введите срок хранения посылки. \n" +
                        "Срок хранения и доставки не должны в сумме превышать 31 день");
                timeToLive = (int) Double.parseDouble(scanner.nextLine());
            }
            PerishableParcel sendParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
            allParcels.add(sendParcel);
        }

    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem(parcel.getDescription());
            parcel.deliver(parcel.getDescription(), parcel.getDeliveryAddress());
            // Пройти по allParcels, вызвать packageItem() и deliver()
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;

        for (Parcel parcel : allParcels) {
            int parcelCost = parcel.calculateDeliveryCost(parcel.getWeight(), parcel.getBaseCost());
            totalCost += parcelCost;
        }
        System.out.println("Стоимость всех посылок равна: " + totalCost);// Посчитать общую стоимость всех доставок и вывести на экран
    }

    private static void reportStatus() {
        for (Parcel parcel : trackingParcels) {
            ((Trackable) parcel).reportStatus();
        }
    }

    private static void changeTrackingStatus() {
        System.out.println("Выберите посылку из списка, локацию которой необходимо изменить.\n" +
                "Введите текстом описание посылки которую вы хотите переместить.");
        for (Parcel parcel : trackingParcels) {
            System.out.println(parcel.getDescription());
        }

        String targetParcel = scanner.nextLine();
        for (int i = 0; i < trackingParcels.size(); i++) {
            Parcel changeParcel = trackingParcels.get(i);
            if (changeParcel.getDescription().equals(targetParcel)) {
                System.out.println("Текущая локация: " + ((FragileParcel) changeParcel).getLocation());
                System.out.println("Введите новую локацию: ");
                String newLocation = scanner.nextLine();
                ((FragileParcel) changeParcel).setNewLocation(newLocation);
                System.out.println("Локация успешно изменена.");
            }
        }
    }
    private static void addParcelInBox() {
        HashMap<Integer, Parcel> helpHash = new HashMap<>();
        int i = 0;

        System.out.println("Выберите номер посылки которую хотите добавить в коробку: ");
        if (!allParcels.isEmpty()) {
            for (Parcel parcel : allParcels) {
                System.out.println( (i+1) + "Посылка " + parcel.getDescription() +
                        " вес: " + parcel.getWeight());
                helpHash.put(i, parcel);
                i++;
            }
            int targetNumber = scanner.nextInt();
            if (targetNumber > 0 && targetNumber < allParcels.size()) {
                switch (helpHash.get(targetNumber).getParcelsType()) {
                    case FRAGILE -> fragileParcelBox.addParcel(helpHash.get(targetNumber));
                    case STANDARD -> standardParcelBox.addParcel(helpHash.get(targetNumber));
                    case PERISHABLE -> perishableParcelBox.addParcel(helpHash.get(targetNumber));
                }
            } else {
                System.out.println("Указан не верный номер посылки.");
            }
        } else {
            System.out.println("Посылки отсутствуют, необходимо вначале добавить из в систему.");
        }

    }

    private static void getAllParcels() {
        System.out.println("Выберите коробку которую хотите распаковать:");
        System.out.println("1. Коробка со стандартными посылками в количестве: " + standardParcelBox.getParcelBox().size());
        System.out.println("2. Коробка с хрупкими посылками в количестве: " + fragileParcelBox.getParcelBox().size());
        System.out.println("3. Коробка со скоропортящимися посылками в количестве: " + perishableParcelBox.getParcelBox().size());

        int targetNumber = scanner.nextInt();
        if (targetNumber > 0 && targetNumber < 4) {
            switch (targetNumber) {
                case 1 -> standardParcelBox.getAllParcels();
                case 2 -> fragileParcelBox.getAllParcels();
                case 3 -> perishableParcelBox.getAllParcels();
            }
        } else {
            System.out.println("Указан не верный номер посылки.");
        }
    }

    private static void perishableParcelCheck() {
        System.out.println("Укажите текущее число месяца.");
        int currentDay = scanner.nextInt();
        for (Parcel parcel : allParcels) {
            if (parcel.getParcelsType().equals(ParcelsType.PERISHABLE)) {
                PerishableParcel newParcel = (PerishableParcel) parcel;
                if (newParcel.isExpired(currentDay, newParcel.getSendDay(), newParcel.getTimeToLive())) {
                    System.out.println("Посылка " + newParcel.getDescription() +
                            " испортилась и будет утилизирована.");
                    allParcels.remove(newParcel);
                } else {
                    System.out.println("Срок годности всех посылок в порядке");
                }
            }
        }
    }
}
