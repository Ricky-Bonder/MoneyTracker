package it.uninsubria.pdm.money;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class ListViewMovimenti extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ListView ListView;

    DatabaseReference databaseMovimento;
    public List<Movimento> mov_list;

    Calendar c;

    public final static String DATA_MOVIMENTO = "dd-mm-yyyy";
    public final static String IMPORTO_MOVIMENTO = "00.00";
    public final static String TAG_MOVIMENTO = "tag";
    public final static String CAUSALE_MOVIMENTO = "causale";
    public final static String UTENTE_MOVIMENTO = "utente";
    public static Double CASH = 00.00;


    public interface MovListCallback {
        void onCallback(List<Movimento> mov_list);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_movimenti);

        ListView = findViewById(R.id.ListView);
        databaseMovimento = FirebaseDatabase.getInstance().getReference("movimento");
        mov_list = new ArrayList<>();


        getMovimentiFromFirebase(new MovListCallback() {
            @Override
            public void onCallback(List<Movimento> mov_list) {
                Log.d("TAG", mov_list.toString());
            }
        });


        ListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Movimento movimento = mov_list.get(position);
                showUpdateDialog(movimento.getId_movimento(), movimento.getDate(), CASH, movimento.getU(), movimento.getCausale(), movimento.getMacroarea());
                return false;
            }
        });


    }

    private void getMovimentiFromFirebase(final MovListCallback myCallback) {
        databaseMovimento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mov_list.clear();
                for (@NonNull DataSnapshot movSnapshot : dataSnapshot.getChildren()) {
                    Movimento movimento = movSnapshot.getValue(Movimento.class);

                    mov_list.add(movimento);

                    myCallback.onCallback(mov_list);
                }

                LayoutListaMovimenti adapter = new LayoutListaMovimenti(ListViewMovimenti.this, mov_list);
                ListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void showUpdateDialog(final String idMov, String dateMovimento, final double amountMoney, String user, String caus, String area) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_edit_movimento, null);
        dialogBuilder.setView(dialogView);

        final TextView DataM = dialogView.findViewById(R.id.DataM);
        final TextView MacroareaM = dialogView.findViewById(R.id.MacroareaM);
        final TextView UtenteM = dialogView.findViewById(R.id.UtenteM);
        Button editDate = dialogView.findViewById((R.id.btn_editDate));
        final Spinner EditTag = dialogView.findViewById((R.id.spinnerEditTag));
        final EditText ImportoM = dialogView.findViewById(R.id.ImportoM);
        final EditText CausaleM = dialogView.findViewById(R.id.CausaleM);
        Button EditMov = dialogView.findViewById(R.id.Aggiorna);
        Button DelMov = dialogView.findViewById(R.id.Elimina);

        dialogBuilder.setTitle("Modifica il Movimento");


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new SelezionaData();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });


        EditMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = DateFormat.getDateInstance().format(c.getTime());
                String realData = String.valueOf(android.text.format.DateFormat.format("yyyyMMdd", c));
                String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                double amount = Double.parseDouble(ImportoM.getText().toString());
                String caus = CausaleM.getText().toString();
                String tag = EditTag.getSelectedItem().toString();

                updateMov(idMov, data, realData, amount, user, caus, tag);
                alertDialog.dismiss();

            }
        });

        DelMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMov(idMov);
                alertDialog.dismiss();
            }


        });

    }

    private void deleteMov(String idMov) {
        DatabaseReference dbMov = FirebaseDatabase.getInstance().getReference("movimento").child(idMov);
        dbMov.removeValue();
        Toast.makeText(this, "Il Movimento è stato eliminato", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    private boolean updateMov(String idMov, String dateMovimento, String realData, double amountMoney, String user, String caus, String area) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("movimento").child(idMov);

        Movimento movimento = new Movimento(idMov, dateMovimento, realData, amountMoney, user, caus, area);

        databaseReference.setValue(movimento);

        Toast.makeText(this, "Movimento Aggiornato", Toast.LENGTH_LONG).show();

        return true;
    }
}
