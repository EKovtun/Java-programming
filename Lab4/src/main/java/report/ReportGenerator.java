package report;

import java.util.List;

 interface ReportGenerator<T> {
    /**
     * Генерация значений только для полей с аннотацией <code>@Reported</code>
     * @param entities Объекты класса генерации
     * @return Сгенерированный репорт
     */
    Report generateOnlyAnnotation(List<? extends T> entities);

    /**
     * Генерация значений для всех полей класса
     * @param entities Объекты класса генерации
     * @return Сгенерированный репорт
     */
    Report generateWithAllFields(List<? extends T> entities);
}
