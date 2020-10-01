package by.bstu.svs.fit.lr3.person;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = Employee.class),
        @JsonSubTypes.Type(value = Student.class),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person implements Serializable {

    private String firstName;
    private String secondName;
    private Integer age;
    private String email;
    private String number;
    private String socialNetwork;
    private String image;


    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", socialNetwork='" + socialNetwork + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(secondName, person.secondName) &&
                Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, age);
    }
}
