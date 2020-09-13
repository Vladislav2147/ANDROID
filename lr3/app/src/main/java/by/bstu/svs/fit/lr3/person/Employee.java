package by.bstu.svs.fit.lr3.person;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Employee extends Person {

    private String organisation;

    public Employee(String firstName, String secondName, int age, String organisation) {

        super(firstName, secondName, age);
        this.organisation = organisation;

    }

    @Override
    public String toString() {
        return "Employee{" +
                "organisation='" + organisation + '\'' +
                "} " + super.toString();
    }
}
