package it.uninsubria.pdm.money;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;
import static it.uninsubria.pdm.money.ListViewMovimenti.CASH;

public class EditMovimento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView DataM;
    TextView MacroareaM;
    TextView UtenteM;
    Button EditDate;
    Spinner EditTag;
    EditText ImportoM;
    EditText CausaleM;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movimento);
        DataM = findViewById(R.id.DataM);
        MacroareaM = findViewById(R.id.MacroareaM);
        UtenteM = findViewById(R.id.UtenteM);
        EditDate = findViewById(R.id.btn_editDate);
        EditTag = findViewById(R.id.spinnerEditTag);
        ImportoM = findViewById(R.id.ImportoM);
        CausaleM = findViewById(R.id.CausaleM);

        Intent intent = getIntent();

        String data = intent.getStringExtra(ListViewMovimenti.DATA_MOVIMENTO);
        String area = intent.getStringExtra(ListViewMovimenti.TAG_MOVIMENTO);
        String user = intent.getStringExtra(ListViewMovimenti.UTENTE_MOVIMENTO);
        amount = intent.getDoubleExtra(ListViewMovimenti.IMPORTO_MOVIMENTO, CASH);
        String causal = intent.getStringExtra(ListViewMovimenti.CAUSALE_MOVIMENTO);

        String ammontare = String.valueOf(amount);

        DataM.setText(data);
        MacroareaM.setText(area);
        UtenteM.setText(user);
        ImportoM.setText(ammontare);
        CausaleM.setText(causal);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        DataM = findViewById(R.id.DataM);
        DataM.setText(currentDateString);
    }
}