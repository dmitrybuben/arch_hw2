package Architecture;

// Паттерн Фабричный метод Factory Method

import java.util.Objects;

public class Sample02 {
    public static void main(String[] args) {
        /*LogReader logReader1 = new OperationSystemLogEventReader();
        logReader1.setCurrentPosition(10);
        logReader1.readLogEntry();

        LogReader logReader2 = new DatabaseReader();
        logReader2.setCurrentPosition(10);
        logReader2.readLogEntry();*/

        // КАК СОЗДАТЬ КОНКРЕТНЫЙ ТИП? например PoemReader?? - enum и switch.
        ConcreteReaderCreator concreteReaderCreator = new ConcreteReaderCreator();
        LogReader logReader = concreteReaderCreator.createLogReader(LogType.Poem);

        // теперь, после создания абстракт.методов по установке и считыванию типа данных,

        // можно,к примеру задать источник данных в базовом классе:
        logReader.setDatasource(Sample01.data);

        // и прочесть эти данные - все эти методы доступны на базовом классе LogReader
        logReader.readLogEntry();
        for (LogEntry log: logReader.readLogEntry()) {
            System.out.println(log.getText());
        }       


        /*Как настроить источник данных для нескольких типов ридеров? ридер инициал.в фабр.методе и
        использ-ся конструкторы по умолчанию - не очень удобно этот ридер инициализировать в рамках
        источника данных, потому что возвращается переменная базового класса и для перехода на работу с
        классом-наследником надо делать downcasting - выглядит не очень - см ниже стр 23 - опасная непрове-
        ряемая операция компилятором. в базовый класс LogReader добавим 2 метода - setdatasource и getdata
        source и имплементируем в классах наследниках
        */
        ((PoemReader)logReader).setData("asdasdasd");
    }
}
// создаем перечисление для выбора создаваемого типа ридера - для создания экз.передаем тип логера:
enum LogType{
    Text,
    Poem,
    Database,
    System
}

abstract class BaseLogReaderCreator{
    // Вспомогательный метод Operation пользуется FаbricMethod для создания объекта
    public LogReader createLogReader(LogType logType){
        LogReader logReader = createLogReaderInstance(logType);
        //TODO: исполним базовые подготовительные алгоритмы ...
        // можем перейти на некоторую позицию
        logReader.setCurrentPosition(2);
        //можем вернуть при желании
        return logReader;
    }

    // Фабричный метод - может создавать экземпляры типа LogReader
    protected abstract LogReader createLogReaderInstance(LogType logType);
}

// Реализуем конкретный класс криэйтор, который будет унаследован от базового абстрактного класса
class ConcreteReaderCreator extends BaseLogReaderCreator{

    @Override
    protected LogReader createLogReaderInstance(LogType logType) {
        return switch (logType){
            case Poem -> new PoemReader();
            case Text -> new TextFileReader();
            case Database -> new DatabaseReader();
            case System -> new OperationSystemLogEventReader();
        };
    }
}

// Описываем разные источники получения данных

class TextFileReader extends LogReader{

    @Override
    protected Iterable<String> readEntries(Integer position) {
        return null;
    }

    @Override
    protected LogEntry parseLogEntry(String stringEntry) {
        return null;
    }

    @Override
    public void setDatasource(Object data) {

    }

    @Override
    public Object getDatasource() {
        return null;
    }
}

class DatabaseReader extends LogReader{

    @Override
    public void setDatasource(Object data) {

    }

    @Override
    public Object getDatasource() {
        return null;
    }

    @Override
    protected Iterable<String> readEntries(Integer position) {
        return null;
    }

    @Override
    protected LogEntry parseLogEntry(String stringEntry) {
        return null;
    }
}

class OperationSystemLogEventReader extends LogReader{

    @Override
    public void setDatasource(Object data) {

    }

    @Override
    public Object getDatasource() {
        return null;
    }

    @Override
    protected Iterable<String> readEntries(Integer position) {
        return null;
    }

    @Override
    protected LogEntry parseLogEntry(String stringEntry) {
        return null;
    }
}
