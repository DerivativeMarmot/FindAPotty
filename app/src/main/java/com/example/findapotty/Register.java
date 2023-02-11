package com.example.findapotty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


//import com.example.findapotty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    TextView btn;

    private EditText inputUsername, inputPassword, inputEmail, confirmPassword;
    Button fl_register_button, fl_account_button;
    private FirebaseAuth mAuth;
    private ProgressBar mLoadingBar;


   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Register binding = Register.inflate(inflater, R.layout.activity_register, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //btn = findViewById(R.id.alreadyHaveAccount);
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        mAuth = FirebaseAuth.getInstance();
        // mLoadingBar = new ProgressBar(Register.this);


        fl_register_button = findViewById(R.id.fl_register_button);
        fl_account_button = findViewById(R.id.fl_account_button);
        //fl_register_button.setOnClickListener((v) -> {checkCredentials();});
        fl_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        fl_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


    }


    //was commented out
//        btn.setOnClickListener((v) -> {
//            startActivity(new Intent(MainActivity.this, MainActivity.class));
//       });
    //}


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void checkCredentials() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (username.isEmpty() || username.length() < 7) {
            showError(inputUsername, "Your username is not valid!");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not valid!");
        } else if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "Password must be at least 7 characters long!");
        } else if (confirmPass.isEmpty() || !confirmPass.equals(password)) {
            showError(confirmPassword, "Password does not match!");
        } else {
            //progress bar stuff would go here
            //Toast.makeText(this, "Call Registration method", Toast.LENGTH_SHORT).show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Successful Registration", Toast.LENGTH_SHORT).show();

                        //changed MainActivity.class in second parameter to Login.class
                        //was commented out
                        Intent intent = new Intent(Register.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {

                        Toast.makeText(Register.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}

