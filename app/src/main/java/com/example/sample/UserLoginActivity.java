package com.example.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText e1,e2;
    TextView t1;
    ConstraintLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        e1=findViewById(R.id.useremailid);
        e2=findViewById(R.id.userpassword);
        mAuth = FirebaseAuth.getInstance();
    }
    public void layoutclicked(View v)
    {try {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    }
    public void loginbtn(View view) {
        if (e1.getText().toString().equals("") || e2.getText().toString().equals("") ) {
            Toast.makeText(UserLoginActivity.this, "Fill all the columns", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(e1.getText().toString(), e2.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(UserLoginActivity.this, UserScreenActivity.class);
                                startActivity(intent);

                            }

                            // ...
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e != null)
                        Toast.makeText(UserLoginActivity.this, e.getMessage() + e.getCause(), Toast.LENGTH_LONG).show();
                }
            });
        } }

    public void signupbtn(View view) {if (e1.getText().toString().equals("") || e2.getText().toString().equals("") ) {
        Toast.makeText(UserLoginActivity.this, "Fill all the columns", Toast.LENGTH_SHORT).show();
    } else{
        mAuth.createUserWithEmailAndPassword(e1.getText().toString(), e2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent=new Intent(UserLoginActivity.this,UserLoginActivity2.class);
                            startActivity(intent);

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLoginActivity.this,e.getMessage()+e.getCause(),Toast.LENGTH_LONG).show();
            }
        });
    }
}}
