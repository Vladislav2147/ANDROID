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
public class Student extends Person implements Serializable {

    private University university;
    private Integer mark;

    public Student(Person person, Integer mark, University university) {
        this.setFirstName(person.getFirstName());
        this.setSecondName(person.getSecondName());
        this.setAge(person.getAge());
        this.setEmail(person.getEmail());
        this.setNumber(person.getNumber());
        this.setImage(person.getImage());
        this.mark = mark;
        this.university = university;
    }

    @Override
    public String toString() {
        return "Student{" +
                "university=" + university +
                ", mark=" + mark +
                "} " + super.toString();
    }
}
