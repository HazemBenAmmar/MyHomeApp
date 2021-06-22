package tn.org.myhomeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.authentication));
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, ItemsList.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        String emailValue = email.getText().toString();
        String passwordValue = password.getText().toString();
        if (emailValue.isEmpty()) {
            email.setError(getString(R.string.required));
            return;
        }

        if (passwordValue.isEmpty()) {
            email.setError(getString(R.string.required));
            return;
        }

        if (v.getId() == R.id.login) {
            mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Welcome",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, ItemsList.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            mAuth.createUserWithEmailAndPassword(emailValue, passwordValue)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Welcome",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, ItemsList.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}