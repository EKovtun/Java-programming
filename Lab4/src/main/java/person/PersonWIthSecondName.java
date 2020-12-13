package person;

import report.Reported;

public class PersonWIthSecondName extends Person {
    @Reported
    final String secondName;

    String withoutAnnotationField = "=))";

    public PersonWIthSecondName(String name, int age, String secondName) {
        super(name, age);
        this.secondName = secondName;
    }
}
