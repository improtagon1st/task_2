import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Выберите задание:");
            System.out.println("1. Текущая дата и время");
            System.out.println("2. Сравнение дат");
            System.out.println("3. Дни до Нового года");
            System.out.println("4. Проверка високосного года");
            System.out.println("5. Количество выходных в месяце");
            System.out.println("6. Время выполнения метода");
            System.out.println("7. Форматирование и парсинг даты");
            System.out.println("8. Конвертация часового пояса");
            System.out.println("9. Расчёт возраста");
            System.out.println("10. Создание календаря месяца");
            System.out.println("11. Генерация случайной даты");
            System.out.println("12. Время до события");
            System.out.println("13. Расчёт рабочих часов");
            System.out.println("14. Конвертация даты с учётом локали");
            System.out.println("15. Определение дня недели");
            System.out.println("16. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    printCurrentDateTime();
                    break;
                case 2:
                    LocalDate d1 = LocalDate.of(2023, 12, 25);
                    LocalDate d2 = LocalDate.of(2023, 12, 31);
                    System.out.println(compareDates(d1, d2));
                    break;
                case 3:
                    System.out.println("Дней до Нового года: " + daysUntilNewYear());
                    break;
                case 4:
                    System.out.println("2024 год високосный: " + isLeapYear(2024));
                    break;
                case 5:
                    System.out.println("Количество выходных в декабре 2023 года: " + countWeekendsInMonth(2023, 12));
                    break;
                case 6:
                    measureExecutionTime();
                    break;
                case 7:
                    System.out.println("Результат форматирования и парсинга даты: " + formatAndParseDate("25-12-2023"));
                    break;
                case 8:
                    System.out.println("Конвертация времени в часовой пояс Europe/Moscow: " +
                            convertToTimeZone(LocalDateTime.now(ZoneOffset.UTC), "Europe/Moscow"));
                    break;
                case 9:
                    LocalDate birthDate = LocalDate.of(1990, 5, 15);
                    System.out.println("Возраст: " + calculateAge(birthDate) + " лет");
                    break;
                case 10:
                    System.out.println("Календарь декабря 2023 года:");
                    createMonthlyCalendar(2023, 12);
                    break;
                case 11:
                    System.out.println("Случайная дата: " +
                            generateRandomDate(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)));
                    break;
                case 12:
                    LocalDateTime eventDate = LocalDateTime.of(2023, 12, 31, 23, 59);
                    System.out.println("Время до события: " + timeUntilEvent(eventDate));
                    break;
                case 13:
                    LocalDateTime startWork = LocalDateTime.of(2023, 12, 25, 9, 0);
                    LocalDateTime endWork = LocalDateTime.of(2023, 12, 25, 17, 0);
                    System.out.println("Количество рабочих часов: " + calculateWorkingHours(startWork, endWork));
                    break;
                case 14:
                    System.out.println("Дата с учётом локали (ru): " +
                            localeSensitiveDateConversion(LocalDate.of(2023, 12, 25), "ru"));
                    break;
                case 15:
                    System.out.println("День недели: " + getDayOfWeekInRussian(LocalDate.of(2023, 12, 25)));
                    break;
                case 16:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
            }
        }
    }

    public static void printCurrentDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Текущая дата и время: " + currentDate.atTime(currentTime).format(formatter));
    }

    public static String compareDates(LocalDate d1, LocalDate d2) {
        if (d1.isAfter(d2)) return "Дата 1 больше, чем дата 2";
        if (d1.isBefore(d2)) return "Дата 1 меньше, чем дата 2";
        return "Обе даты равны";
    }

    public static long daysUntilNewYear() {
        LocalDate today = LocalDate.now();
        LocalDate newYear = LocalDate.of(today.getYear() + 1, 1, 1);
        return ChronoUnit.DAYS.between(today, newYear);
    }

    public static boolean isLeapYear(int year) {
        return Year.isLeap(year);
    }

    public static long countWeekendsInMonth(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return start.datesUntil(end.plusDays(1))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .count();
    }

    public static void measureExecutionTime() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {}
        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }

    public static String formatAndParseDate(String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(dateString, inputFormatter);
        return date.plusDays(10).format(outputFormatter);
    }

    public static String convertToTimeZone(LocalDateTime dateTime, String zoneId) {
        ZonedDateTime utcTime = dateTime.atZone(ZoneOffset.UTC);
        return utcTime.withZoneSameInstant(ZoneId.of(zoneId)).toString();
    }

    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static void createMonthlyCalendar(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        start.datesUntil(end.plusDays(1)).forEach(date -> {
            String dayType = (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) ? "Выходной" : "Будний день";
            System.out.println(date + ": " + dayType);
        });
    }

    public static LocalDate generateRandomDate(LocalDate start, LocalDate end) {
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        Random random = new Random();
        return start.plusDays(random.nextInt((int) daysBetween + 1));
    }

    public static String timeUntilEvent(LocalDateTime eventDateTime) {
        Duration duration = Duration.between(LocalDateTime.now(), eventDateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return hours + " часов, " + minutes + " минут, " + seconds + " секунд";
    }

    public static long calculateWorkingHours(LocalDateTime start, LocalDateTime end) {
        long hours = 0;
        while (!start.isAfter(end)) {
            if (start.getDayOfWeek() != DayOfWeek.SATURDAY && start.getDayOfWeek() != DayOfWeek.SUNDAY) {
                hours++;
            }
            start = start.plusHours(1);
        }
        return hours;
    }

    public static String localeSensitiveDateConversion(LocalDate date, String localeCode) {
        Locale locale = new Locale(localeCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", locale);
        return date.format(formatter);
    }

    public static String getDayOfWeekInRussian(LocalDate date) {
        Locale russianLocale = new Locale("ru");
        return date.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, russianLocale);
    }
}
