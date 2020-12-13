package report;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyList;

class ReportUtils {
    /**
     * Получить репорт-поля для класса
     * @param clazz Класс, с которого требуется получить репорт-поля
     * @param onlyWithAnnotation Возврат только репорт-полей с аннотацией <code>@Reported</code>
     * @return Репорт-поля
     */
    static List<Field> getReportedFieldsForClass(Class<?> clazz, boolean onlyWithAnnotation) {
        List<Field> fields = new ArrayList<>();
        int insertIndex = 0;

        while (clazz != null) {
            for (var field : clazz.getDeclaredFields())
                if (!onlyWithAnnotation || field.isAnnotationPresent(Reported.class)) {
                    field.setAccessible(true);
                    fields.add(insertIndex++, field);
                }

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
    static List<String> getReportedFieldsNames(List<Field> reportedFields, Map<String, String> fieldsNamesMap) {
        if (reportedFields == null) return emptyList();
        List<String> reportedNames = new ArrayList<>();

        for(Field field : reportedFields) {
            String fieldName = field.getName();
            String replaceFieldName = fieldsNamesMap.getOrDefault(fieldName, fieldIsReportedAndHasReportedName(field)?
                    field.getAnnotation(Reported.class).reportFieldName().trim() : null);

            reportedNames.add(Objects.requireNonNullElse(replaceFieldName, fieldName));
        }

        return reportedNames;
    }

    private static boolean fieldIsReportedAndHasReportedName(Field field) {
        return field.isAnnotationPresent(Reported.class)
                && !field.getAnnotation(Reported.class).reportFieldName().trim().isEmpty();
    }
}
