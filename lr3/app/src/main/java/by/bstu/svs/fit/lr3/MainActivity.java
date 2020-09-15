package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        File file = new File(getExternalFilesDir(null),"json.json");
//
//        Manager manager = new Manager();
//        Course course = manager.generateCourse(file);
//
//
//        for (Person person :
//                course.getListeners()) {
//            Log.d("Person", person.toString());
//        }
//
//        manager.sortByAgeThenBySecondName(course);
//
//        for (Person person :
//                course.getListeners()) {
//            Log.d("Sorted", person.getAge() + " " + person.getSecondName());
//        }
//
//        List<Student> topThree = manager.getTopThreeStudentsByMark(course);
//
//        for (Student student:
//             topThree) {
//            Log.d("top three", student.toString());
//        }

    }

    public void onClickNextButton(View view) {

        Intent intent = new Intent(this, ChooseTypeActivity.class);
        String firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        String secondName = ((EditText)findViewById(R.id.secondName)).getText().toString();
        Integer age;
        try {
            age = Integer.parseInt(((EditText)findViewById(R.id.age)).getText().toString());
        }
        catch (NumberFormatException ex) {
            age = null;
        }

        intent.putExtra("firstName", firstName);
        intent.putExtra("secondName", secondName);
        intent.putExtra("age", age);

        startActivity(intent);

    }



}