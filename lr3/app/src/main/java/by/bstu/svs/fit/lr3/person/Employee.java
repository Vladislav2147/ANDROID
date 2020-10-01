package by.bstu.svs.fit.lr3.person;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Person implements Serializable {

    private String organisation;

    public Employee(Person person, String organisation) {
        this.setFirstName(person.getFirstName());
        this.setSecondName(person.getSecondName());
        this.setAge(person.getAge());
        this.setEmail(person.getEmail());
        this.setNumber(person.getNumber());
        this.setSocialNetwork(person.getSocialNetwork());
        this.setImage(person.getImage());
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "organisation='" + organisation + '\'' +
                "} " + super.toString();
    }
}
