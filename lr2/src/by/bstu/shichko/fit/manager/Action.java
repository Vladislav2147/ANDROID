package by.bstu.shichko.fit.manager;

import by.bstu.shichko.fit.course.Course;

public interface Action {

    Course course = null;

    static String info() {
        return "Interface Action contains methods for working with course";
    }

    default Course generateCourse() {

    }

    
}
