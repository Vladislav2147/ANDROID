package by.bstu.svs.fit.lr3.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Student extends Person {

    private University university;
    private Integer mark;

    public Student(String firstName, String secondName, Integer age, University university, Integer mark) {
        super(firstName, secondName, age);
        this.university = university;
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "university=" + university +
                ", mark=" + mark +
                "} " + super.toString();
    }
}
