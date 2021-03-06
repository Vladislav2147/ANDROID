package by.bstu.shichko.fit.person;

public enum University {

    BSTU("Belarusian State University of Technologies"),
    BNTU("Belarusian National University of Technologies"),
    BSUIR("Belarusian State University of Informatics and Radioelectronics"),
    BSU("Belarusian State University"),
    BSEU("Belarusian State Economic University");

    private int studentCount;
    private String fullName;

    University(String fullName) {
        this.fullName = fullName;
    }

    public void addStudent() {
        studentCount++;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public String getFullName() {
        return fullName;
    }

}
