package it.uninsubria.pdm.money;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button nuovo_movimento;
    Button lista_movimenti;
    Button statistiche;
    TextView userEmail;
    Button userLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.tv);
        userLogout = findViewById(R.id.btnLogout);
        nuovo_movimento = findViewById(R.id.btn_movimento);
        lista_movimenti = findViewById(R.id.btn_listamovimenti);
        statistiche = findViewById(R.id.btn_filtra);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userEmail.setText(firebaseUser.getEmail());

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        nuovo_movimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuovo_mov = new Intent(MainActivity.this, AggiungiMovimento.class);
                startActivity(nuovo_mov);
            }
        });

        lista_movimenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lista_mov = new Intent(MainActivity.this, ListViewMovimenti.class);
                startActivity(lista_mov);
            }
        });

        statistiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stats_mov = new Intent(MainActivity.this, FiltraMovimenti.class);
                startActivity(stats_mov);
            }
        });

    }
}
