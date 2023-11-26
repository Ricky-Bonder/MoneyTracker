package it.uninsubria.pdm.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrazione extends AppCompatActivity {

    Button btn_reg ;
    Button btn_log;
    Toolbar toolbar;
    ProgressBar progressBar;
    EditText email;
    EditText password;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        firebaseAuth = FirebaseAuth.getInstance();


        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        btn_log = findViewById(R.id.btn_login);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loggaggio = new Intent(Registrazione.this, LoginActivity.class);
                startActivity(loggaggio);
            }
        });

        btn_reg = findViewById(R.id.btn_registrazione);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()) {
                            Toast.makeText(Registrazione.this, "Registrazione avvenuta con successo",
                                    Toast.LENGTH_LONG).show();
                            email.setText("");
                            password.setText("");
                        }
                        else {
                            Toast.makeText(Registrazione.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
