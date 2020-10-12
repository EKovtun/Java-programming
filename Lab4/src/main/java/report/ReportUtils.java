package report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

class ReportUtils {
    /**
     * Получить репорт-поля для класса
     * @param clazz Класс, с которого требуется получить репорт-поля
     * @param onlyWithAnnotation Возврат только репорт-полей с аннотацией <code>@Reported</code>
     * @return Репорт-поля
     */
    static ArrayList<Field> getReportedFieldsForClass(Class<?> clazz, boolean onlyWithAnnotation) {
        ArrayList<Field> fields = new ArrayList<>();
        int insertIndex = 0;

        while (clazz != null) {
            for (var field : clazz.getDeclaredFields())
                if (!onlyWithAnnotation || field.isAnnotationPresent(Reported.class))
                    fields.add(insertIndex++, field);

            clazz = clazz.getSuperclass();
            insertIndex = 0;
        }

        return fields;
    }

    /**
     * Получить имена репорт-полей
     * @param reportedFields Репорт-поля
     * @param fieldsNamesMap Словарь полей, имя которых следует сменить при генерации.
     * @return Имена репорт-полей
     */
    static ArrayList<String> getReportedFieldsNames(ArrayList<Field> reportedFields, Map<String, String> fieldsNamesMap) {
        if (reportedFields == null) return new ArrayList<>();
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
}
