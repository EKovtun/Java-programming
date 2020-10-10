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
        ArrayList<Field> reportedFields = new ArrayList<>(getReportedFieldsForClass(clazz, true));
        return generate(entities, reportedFields);
    }

    @Override
    public Report generateWithAllFields(List<? extends T> entities) {
        if (entities == null || entities.isEmpty()) return new ReportCSV(new byte[0]);
        ArrayList<Field> reportedFields = new ArrayList<>(getReportedFieldsForClass(clazz, false));
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
        builder.append(String.join(", ", getReportedFieldsNames(reportedFields)));

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

            builder.append('\n').append(String.join(", ", valuesOfFields));
        }

        return new ReportCSV(builder.toString().getBytes());
    }

    /**
     * Получить имена репорт-полей
     * @param reportedFields Репорт-поля
     * @return Имена репорт-полей
     */
    private ArrayList<String> getReportedFieldsNames(ArrayList<Field> reportedFields) {
        if (reportedFields == null || reportedFields.isEmpty()) return new ArrayList<>();
        ArrayList<String> reportedNames = new ArrayList<>();

        for(Field field : reportedFields) {
            String fieldName = field.getName();
            String replaceFieldName = fieldsNamesMap.get(fieldName);
            if (replaceFieldName != null) {
                reportedNames.add(replaceFieldName);
            } else if (field.isAnnotationPresent(Reported.class)
                    && !field.getAnnotation(Reported.class).reportFieldName().trim().isEmpty()) {
                reportedNames.add(field.getAnnotation(Reported.class).reportFieldName().trim());
            } else {
                reportedNames.add(fieldName);
            }
        }

        return reportedNames;
    }

    /**
     * Получить репорт-поля для класса
     * @param clazz Класс, с которого требуется получить репорт-поля
     * @param withAnnotation Возврат только репорт-полей с аннотацией <code>@Reported</code>
     * @return Репорт-поля
     */
    private ArrayList<Field> getReportedFieldsForClass(Class<?> clazz, boolean withAnnotation) {
        if (clazz == null) return new ArrayList<>();
        ArrayList<Field> fields = new ArrayList<>();
        int insertIndex = 0;

        while (clazz != null) {
            for (var field : clazz.getDeclaredFields()) {
                if (!withAnnotation || field.isAnnotationPresent(Reported.class)) {
                    fields.add(insertIndex++, field);
                }
            }
            clazz = clazz.getSuperclass();
            insertIndex = 0;
        }

        return fields;
    }
}
