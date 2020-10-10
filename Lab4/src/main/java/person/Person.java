package person;

import report.Reported;

public class Person {
    @Reported(reportFieldName = "Имя")
    String name;

    @Reported(reportFieldName = "Возраст")
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
