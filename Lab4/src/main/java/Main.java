import person.Person;
import person.PersonWIthSecondName;
import report.ReportGeneratorCSV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        printPersonsCSVWithAnnotation();
        System.out.println();
        System.out.println();
        printPersonsWithSecondNameCSVWithAnnotation();
        System.out.println();
        System.out.println();
        printPersonsWithSecondNameCSVWithoutAnnotation();
        System.out.println();
        System.out.println();
        printPersonsWithSecondNameCSVWithoutAnnotationAndReplacedMap();
    }

    static void printPersonsCSVWithAnnotation() throws IOException {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("Alice", 25));
        persons.add(new Person(null, 21));
        persons.add(new PersonWIthSecondName("Ruby", 35, null));

        ReportGeneratorCSV<Person> generator = new ReportGeneratorCSV<>(Person.class, null);
        generator.generateOnlyAnnotation(persons).writeTo(System.out);
    }

    static void printPersonsWithSecondNameCSVWithAnnotation() throws IOException {
        ArrayList<PersonWIthSecondName> persons = new ArrayList<>();
        persons.add(new PersonWIthSecondName("Alice", 25, null));
        persons.add(new PersonWIthSecondName("Bob", 21, "Java"));
        persons.add(new PersonWIthSecondName("Haskell", 35, null));

        ReportGeneratorCSV<PersonWIthSecondName> generator = new ReportGeneratorCSV<>(
                PersonWIthSecondName.class, null);
        generator.generateOnlyAnnotation(persons).writeTo(System.out);
    }

    static void printPersonsWithSecondNameCSVWithoutAnnotation() throws IOException {
        ArrayList<PersonWIthSecondName> persons = new ArrayList<>();
        persons.add(new PersonWIthSecondName("Alice", 25, null));
        persons.add(new PersonWIthSecondName("Bob", 21, "Java"));
        persons.add(new PersonWIthSecondName("Haskell", 35, null));

        ReportGeneratorCSV<PersonWIthSecondName> generator = new ReportGeneratorCSV<>(
                PersonWIthSecondName.class, null);
        generator.generateWithAllFields(persons).writeTo(System.out);
    }

    static void printPersonsWithSecondNameCSVWithoutAnnotationAndReplacedMap() throws IOException {
        ArrayList<PersonWIthSecondName> persons = new ArrayList<>();
        persons.add(new PersonWIthSecondName("Alice", 25, null));
        persons.add(new PersonWIthSecondName("Bob", 21, "Java"));
        persons.add(new PersonWIthSecondName("Haskell", 35, null));

        Map<String, String> replaceFieldsNames = new TreeMap<>();
        replaceFieldsNames.put("withoutAnnotationField", "It field is replaced");
        replaceFieldsNames.put("name", "Это новое название для поля 'Имя'");

        ReportGeneratorCSV<PersonWIthSecondName> generator = new ReportGeneratorCSV<>(
                PersonWIthSecondName.class, replaceFieldsNames);
        generator.generateWithAllFields(persons).writeTo(System.out);
    }
}
