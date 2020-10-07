package by.bstu.svs.fit.lr3.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Pattern;

import by.bstu.svs.fit.lr3.R;

public class RegistrationTextWatcher implements TextWatcher {

    private EditText editText;

    public RegistrationTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        switch (editText.getId()) {
            case R.id.firstName:
            case R.id.secondName:
                if (!isNameValid(editable.toString()))
                    editText.setError("name should be between 3 and 20");
                else
                    editText.setError(null);
                break;
            case R.id.age:
                if (!isAgeValid(editable.toString()))
                    editText.setError("age should be between 18 and 100");
                else
                    editText.setError(null);
                break;
            case R.id.email:
                if (!isEmailValid(editable.toString()))
                    editText.setError("invalid email");
                else
                    editText.setError(null);
                break;
            case R.id.phone:
                if (!isPhoneValid(editable.toString()))
                    editText.setError("invalid phone");
                else
                    editText.setError(null);
                break;
            case R.id.social:
                if (!isLinkValid(editable.toString()))
                    editText.setError("invalid link");
                else
                    editText.setError(null);
                break;
            default:
                break;
        }

    }

    public boolean isNameValid(String name) {
        return name.length() >= 3 && name.length() < 20;
    }
    public boolean isAgeValid(String ageText) {
        if (ageText.length() == 0) return true;
        int age = Integer.parseInt(ageText);
        return age >= 18 && age < 100;
    }
    private boolean isEmailValid(String email) {
        String regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regexp);
        return pattern.matcher(email).matches();
    }
    private boolean isPhoneValid(String phone) {
        return phone.length() == 0 || phone.length() == 19;
    }
    private boolean isLinkValid(String link) {
        if (link.length() == 0) return true;
        String regexp = "^[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$";
        Pattern pattern = Pattern.compile(regexp);
        return pattern.matcher(link).matches();
    }
}