package Architecture;

// Задача считывания файлов из разных источников и записи в файл как пример Шаблонного метода

import java.util.ArrayList;
import java.util.Scanner;

public class Sample01 {
    // Давайте определим некий логгер - String data  и класс наследник PoemReader будет его читать

    public static String data = """
            У ЛУкоморья дуб зеленый;
            Златая цепь на дубе том:
            И днем и ночью кот ученый
            Все ходит по цепи кругом;
             """;

    public static void main(String[] args) {
        //LogReader - базовый класс для PoemReader, поэтому можно объявить переменную базового класса и присвоить ей
        //ссылку на объект класс-наследника
        LogReader logReader = new PoemReader(data);

        //можно изменить позицю для начала считывания
        logReader.setCurrentPosition(2);

        //readLogEntry возвращает коллекцию типа LogEntry интерфейс Iterable, поэтому коллекцию можно перечислить.
        for (LogEntry log: logReader.readLogEntry()) {
            System.out.println(log.getText());
        }
    }
}

    /*Обычный класс записи лога*/
class LogEntry{
    /*Какая-то строка лога*/
    private String text;

    /*Конструктор для инициализации строки лога*/
    public LogEntry(String text) {
        this.text = text;
    }

    /*Геттер для работы с объектом LogEntry*/
    public String getText() {
        return text;
    }
}

/* Вне зависимости от способа считывания данных, в конце нужна коллекция объектов типа LogEntry -
все логи в объектном виде */

/*Абстрактный класс как каркас алгоритма - самые базовые наработки. Подклассы могут переопределять
некоторые шаги алгоритма*/

abstract class LogReader{

    // начальное положение считывания и гетеры и сеттеры к ней
    public Integer currentPosition = 0;

    public Integer getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition = currentPosition;
    }

    // Методы добавлены для инициализации и возврата источника даных
    public abstract void setDatasource(Object data);

    public abstract Object getDatasource();


    /*Сам шаблонный метод называется - readLogEntry, который использует 2 метода ниже как строительные блоки
        public - потому что его и будем вызывать. Ниже описываем как получаем эту самую нужную коллекцию*/

    public Iterable<LogEntry> readLogEntry(){

        //Пустая коллекция ТИПА LogEntry называется logList - надо ее наполнить и вернуть
        ArrayList<LogEntry> logList = new ArrayList<>();

        for (String s:readEntries(currentPosition)) {
            logList.add(parseLogEntry(s)); // Запись в коллекцию уже объекта, полученного из преобразвоания строки.
        }
        return logList;
    }

    /*метод для внутреннего использования - возвращает объект типа Iterable - значит что объект должен быть
    перечисляемым, то есть им может быть любая коллекция. Интерфейс Iterable нужен чтобы уйти от каких-то
    конкретных типов. Должен уметь считывать данные из какого-то источника и возвращать коллекцию строк типа String
    Внутрь метода передаем некую позицию с какой будем считывать данные*/

    protected abstract Iterable<String> readEntries (Integer position);

    //нужен механизм преобразования считанной строки в объект LogEntry, который мы должны вернуть.

    protected abstract LogEntry parseLogEntry(String stringEntry);

}

class PoemReader extends LogReader{
    // Вспомогательная переменная data и конструктор для инициализации данных - передача данных в класс
    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public PoemReader(String data) {
        this.data = data;
    }
    // создадим базовый конструктор без данных.
    public PoemReader() {
    }
    // реализация абстрактных методов в классе-наследнике. Родительский класс с ихсодными методами - LogReader
    @Override
    public void setDatasource(Object data) {
        this.data = data.toString();
    }

    @Override
    public Object getDatasource() {
        return data;
    }

    @Override
    protected Iterable<String> readEntries(Integer position) {
        Scanner scanner = new Scanner(data);
        ArrayList<String>logEntry = new ArrayList<>();
        // вспомогательная переменная перед началом считывания
        int counter = 0;
        while (scanner.hasNextLine()){
            if (counter >=position){
                position = counter;
                String line = scanner.nextLine();
                logEntry.add(line);
            }
            else {
                scanner.nextLine();
            }
            counter++;
        }
        return logEntry;
    }

    @Override
    protected LogEntry parseLogEntry(String stringEntry) {
        return new LogEntry(stringEntry);
    }
}
