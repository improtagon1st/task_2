import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задание (1-5):");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        switch (taskNumber) {
            case 1:
                task1();
                break;
            case 2:
                task2();
                break;
            case 3:
                task3();
                break;
            case 4:
                task4();
                break;
            case 5:
                task5();
                break;
            default:
                System.out.println("Неверный номер задания.");
        }
    }

    // Задание 1: Работа с потоками ввода-вывода
    private static void task1() {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String upperCaseLine = line.toUpperCase();
                writer.write(upperCaseLine);
                writer.newLine();
            }

            System.out.println("Файл успешно обработан и записан в " + outputFilePath);

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    // Задание 2: Реализация паттерна Декоратор
    private static void task2() {
        TextProcessor processor = new ReplaceDecorator(
                new UpperCaseDecorator(
                        new TrimDecorator(new SimpleTextProcessor())
                )
        );
        String result = processor.process(" Hello world ");
        System.out.println("Результат обработки: " + result);
    }

    // Задание 3: Сравнение производительности IO и NIO
    private static void task3() {
        String inputFilePath = "input.txt";
        String outputFilePathIO = "output.txt";
        String outputFilePathNIO = "output.txt";

        // IO
        long startTimeIO = System.currentTimeMillis();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePathIO))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTimeIO = System.currentTimeMillis();
        System.out.println("Время выполнения IO: " + (endTimeIO - startTimeIO) + " ms");

        // NIO
        long startTimeNIO = System.currentTimeMillis();
        try (FileChannel srcChannel = new FileInputStream(inputFilePath).getChannel();
             FileChannel destChannel = new FileOutputStream(outputFilePathNIO).getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024); // 1 MB buffer
            while (srcChannel.read(buffer) != -1) {
                buffer.flip();
                destChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTimeNIO = System.currentTimeMillis();
        System.out.println("Время выполнения NIO: " + (endTimeNIO - startTimeNIO) + " ms");
    }

    // Задание 4: Программа с использованием Java NIO
    private static void task4() {
        String sourceFilePath = "input.txt";
        String destFilePath = "output.txt";

        try (FileChannel srcChannel = new FileInputStream(sourceFilePath).getChannel();
             FileChannel destChannel = new FileOutputStream(destFilePath).getChannel()) {

            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            System.out.println("Файл успешно скопирован.");

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }

    // Задание 5: Асинхронное чтение файла с использованием NIO.2
    private static void task5() {
        String filePath = "input.txt";

        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(filePath), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024); // 1 MB buffer
            long position = 0;

            fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Чтение завершено, прочитано " + result + " байт");
                    attachment.flip();
                    byte[] data = new byte[attachment.limit()];
                    attachment.get(data);
                    System.out.println(new String(data));
                    attachment.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.err.println("Ошибка при чтении файла: " + exc.getMessage());
                }
            });

            // Ждем завершения асинхронной операции
            Thread.sleep(1000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Интерфейс и классы для задания 2
interface TextProcessor {
    String process(String text);
}

class SimpleTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

class UpperCaseDecorator implements TextProcessor {
    private TextProcessor processor;

    public UpperCaseDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).toUpperCase();
    }
}

class TrimDecorator implements TextProcessor {
    private TextProcessor processor;

    public TrimDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).trim();
    }
}

class ReplaceDecorator implements TextProcessor {
    private TextProcessor processor;

    public ReplaceDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text).replace(' ', '_');
    }
}