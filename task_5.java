import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class task_5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите задание:");
            System.out.println("1. Задание 1 - База данных (Singleton)");
            System.out.println("2. Задание 2 - Логирование (Singleton)");
            System.out.println("3. Задание 3 - Статусы заказа (Enum)");
            System.out.println("4. Задание 4 - Сезоны года (Enum)");
            System.out.println("5. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    DatabaseConnectionTest();
                    break;
                case 2:
                    LoggerTest();
                    break;
                case 3:
                    OrderStatusTest();
                    break;
                case 4:
                    SeasonsTest();
                    break;
                case 5:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
            }
        }
    }

    static class DatabaseConnection {
        private static DatabaseConnection instance;

        private DatabaseConnection() {
            System.out.println("Создание подключения к базе данных...");
        }

        public static DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
    }

    static void DatabaseConnectionTest() {
        System.out.println("Тестирование Singleton для базы данных...");
        DatabaseConnection connection1 = DatabaseConnection.getInstance();
        DatabaseConnection connection2 = DatabaseConnection.getInstance();
        System.out.println("Сравнение объектов connection1 и connection2: " + (connection1 == connection2));
    }

    static class Logger {
        private static Logger instance;
        private List<String> logs = new ArrayList<>();

        private Logger() {}

        public static Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }

        public void addLog(String message) {
            logs.add(message);
        }

        public void printLogs() {
            if (logs.isEmpty()) {
                System.out.println("Логи пусты.");
            } else {
                for (String log : logs) {
                    System.out.println(log);
                }
            }
        }
    }

    static void LoggerTest() {
        System.out.println("Тестирование Singleton для логирования...");
        Logger logger = Logger.getInstance();
        logger.addLog("Запуск программы.");
        logger.addLog("Ошибка подключения.");
        logger.printLogs();
    }

    enum OrderStatus {
        NEW, IN_PROGRESS, DELIVERED, CANCELLED
    }

    static class Order {
        private OrderStatus status;

        public Order() {
            this.status = OrderStatus.NEW;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void setStatus(OrderStatus status) {
            if (this.status == OrderStatus.DELIVERED && status == OrderStatus.CANCELLED) {
                System.out.println("Невозможно отменить доставленный заказ.");
            } else {
                this.status = status;
            }
        }
    }

    static void OrderStatusTest() {
        System.out.println("Тестирование статусов заказа...");
        Order order = new Order();
        System.out.println("Статус заказа: " + order.getStatus());
        order.setStatus(OrderStatus.IN_PROGRESS);
        System.out.println("Статус заказа после изменения: " + order.getStatus());
        order.setStatus(OrderStatus.DELIVERED);
        System.out.println("Статус заказа после доставки: " + order.getStatus());
        order.setStatus(OrderStatus.CANCELLED);
    }

    enum Season {
        WINTER, SPRING, SUMMER, AUTUMN;

        public String getSeasonNameInRussian() {
            switch (this) {
                case WINTER:
                    return "Зима";
                case SPRING:
                    return "Весна";
                case SUMMER:
                    return "Лето";
                case AUTUMN:
                    return "Осень";
                default:
                    return "Неизвестный сезон";
            }
        }
    }

    static void SeasonsTest() {
        System.out.println("Тестирование сезонов года...");
        for (Season season : Season.values()) {
            System.out.println(season + ": " + season.getSeasonNameInRussian());
        }
    }
}
