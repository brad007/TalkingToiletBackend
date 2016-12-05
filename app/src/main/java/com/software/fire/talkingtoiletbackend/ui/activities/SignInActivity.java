package com.software.fire.talkingtoiletbackend.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.software.fire.talkingtoiletbackend.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailET;
    private EditText mPasswordET;
    private Button mSignInButton;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialiseScreen();
    }

    private void initialiseScreen() {
        mEmailET = (EditText) findViewById(R.id.email_et);
        mPasswordET = (EditText) findViewById(R.id.password_et);
        mSignInButton = (Button) findViewById(R.id.sign_in_bt);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showProgressDialog();
        String email = mEmailET.getText().toString();
        if (email.contains("@")) {
            String password = mPasswordET.getText().toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(SignInActivity.this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.hide();
                    Toast.makeText(SignInActivity.this, getString(R.string.no_access), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showProgressDialog() {
        pd = new ProgressDialog(SignInActivity.this);
        pd.setTitle(R.string.sign_in_title);
        pd.setMessage(getString(R.string.sign_in_message));
        pd.show();
    }
}
