package report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGeneratorCSV<T> implements ReportGenerator<T> {
    Class<T> clazz;
    Map<String, String> fieldsNamesMap = new HashMap<>();

    /**
     * @param clazz Класс, над которым производится рефлексия
     * @param fieldsNames Словарь полей, имя которых следует сменить при генерации.
     *                    Ключ словаря - фактическое имя поля, значение словаря - новое имя поля.
     *                    <p>
     *                    При выводе имя для поля выбирается следующем образом:
     *                    <ul>
     *                      <li>Если новое имя для поля задано в словаре - применить новое имя из словаря
     *                      <li>Если имя указано в аннотации @Reported - взять имя из аннотации
     *                      <li>Если новое имя отсутствует в словаре и не задано в аннотации - взять фактическое имя поля
     *                    </ul>
     * @throws IllegalArgumentException Исключение выбрасывается при <code>clazz == null</code>
     */
    public ReportGeneratorCSV(Class<T> clazz, Map<String, String> fieldsNames) throws IllegalArgumentException {
        if (clazz == null) throw new IllegalArgumentException();
        if (fieldsNames != null) this.fieldsNamesMap = fieldsNames;
        this.clazz = clazz;
    }

    @Override
    public ReportCSV generateOnlyAnnotation(List<? extends T> entities) {
        if (entities == null || entities.isEmpty()) return new ReportCSV(new byte[0]);
        ArrayList<Field> reportedFields = new ArrayList<>(
                ReportUtils.getReportedFieldsForClass(clazz, true));
        return generate(entities, reportedFields);
    }

    @Override
    public Report generateWithAllFields(List<? extends T> entities) {
        if (entities == null || entities.isEmpty()) return new ReportCSV(new byte[0]);
        ArrayList<Field> reportedFields = new ArrayList<>(
                ReportUtils.getReportedFieldsForClass(clazz, false));
        return generate(entities, reportedFields);
    }

    /**
     * Генерация CSV-репорта
     * @param entities объекты класса из которых собираются значения
     * @param reportedFields репорт-поля по которым строится репорт
     * @return CSV-репорт
     */
    private ReportCSV generate(List<? extends T> entities, ArrayList<Field> reportedFields) {
        if (reportedFields == null || reportedFields.isEmpty()) return new ReportCSV(new byte[0]);

        StringBuilder builder = new StringBuilder();
        builder.append(String.join(",", ReportUtils.getReportedFieldsNames(reportedFields, fieldsNamesMap)));

        ArrayList<String> valuesOfFields = new ArrayList<>();
        valuesOfFields.ensureCapacity(reportedFields.size());

        for(T entity : entities) {
            valuesOfFields.clear();

            for(Field reportedField : reportedFields) {
                reportedField.setAccessible(true);
                try {
                    valuesOfFields.add(reportedField.get(entity).toString());
                } catch (NullPointerException | IllegalAccessException e ) {
                    valuesOfFields.add("null");
                }
            }

            builder.append('\n').append(String.join(",", valuesOfFields));
        }

        return new ReportCSV(builder.toString().getBytes());
    }
}
