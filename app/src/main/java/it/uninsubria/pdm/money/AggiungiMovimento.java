package it.uninsubria.pdm.money;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AggiungiMovimento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText causale;
    Button pickerData;
    EditText amount;
    Button submit;
    Button cancel;
    Spinner macroaree;
    Calendar c;

    DatabaseReference databaseMovimento;

    public List<Movimento> mov_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_movimento_);

        databaseMovimento = FirebaseDatabase.getInstance().getReference("movimento");
        mov_list = new ArrayList<>();

        causale = findViewById(R.id.add_causale);
        pickerData = findViewById(R.id.btn_picker);
        amount = findViewById(R.id.text_amount);
        submit = findViewById(R.id.btn_submit);
        cancel = findViewById(R.id.btn_cancel);
        macroaree = findViewById(R.id.spinnerTag);
        mov_list = new ArrayList<>();


        pickerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new SelezionaData();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovimento();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_act = new Intent(AggiungiMovimento.this, MainActivity.class);
                startActivity(main_act);
            }
        });

    }

    private void addMovimento()  {
        String idmov = databaseMovimento.push().getKey();
        String data = DateFormat.getDateInstance().format(c.getTime());
        String realData = String.valueOf(android.text.format.DateFormat.format("yyyyMMdd", c));
        Double importo = Double.parseDouble(amount.getText().toString());
        String utente = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String descrizione = causale.getText().toString();
        String macroarea = macroaree.getSelectedItem().toString();


        Movimento mov = new Movimento(idmov , data , realData, importo, utente, descrizione, macroarea);
        assert idmov != null;

        databaseMovimento.child(idmov).setValue(mov);
        Toast.makeText(this , "Movimento Aggiunto ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    }

}
