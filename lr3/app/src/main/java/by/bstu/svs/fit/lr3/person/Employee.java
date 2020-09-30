package by.bstu.svs.fit.lr3.person;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Employee extends Person implements Serializable {

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
