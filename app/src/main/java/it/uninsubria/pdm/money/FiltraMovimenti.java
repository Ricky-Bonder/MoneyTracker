package it.uninsubria.pdm.money;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FiltraMovimenti extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ListView ListViewFilter;
    Query databaseMovimento;
    public List<Movimento> movFilteredList;

    TextView statistica;
    TextView annuale;
    TextView mensile;
    Button SelezionaData;
    Spinner tagFilter;
    Button calcola;
    Calendar calendar;
    String data;

    LayoutListaMovimenti adapter;

    double Totale;
    double Mensile;
    double Annuale;
    String substYear;
    String substMonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_movimenti);

        ListViewFilter = findViewById(R.id.ListViewFilter);
        statistica = findViewById(R.id.mostraStats);
        SelezionaData = findViewById(R.id.btn_dataInizio);
        calcola = findViewById(R.id.btn_calcola);
        tagFilter = findViewById(R.id.spinnerFilter);
        annuale = findViewById(R.id.mostraAnnuale);
        mensile = findViewById(R.id.mostraMensile);


        movFilteredList = new ArrayList<>();
        adapter = new LayoutListaMovimenti(this, movFilteredList);
        ListViewFilter.setAdapter(adapter);



        SelezionaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateStartPicker = new SelezionaData();
                dateStartPicker.show(getSupportFragmentManager(), "start date picker");

            }
        });


        calcola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = tagFilter.getSelectedItem().toString();
                databaseMovimento = FirebaseDatabase.getInstance().getReference("movimento").orderByChild("macroarea").equalTo(tag);
                databaseMovimento.addValueEventListener(valueEventListener);
                Toast.makeText(getApplicationContext(), "Movimenti Filtrati", Toast.LENGTH_LONG).show();

            }
        });

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            movFilteredList.clear();
            if(dataSnapshot.exists()) {
                Totale = 0.0;
                Mensile = 0.0;
                Annuale = 0.0;
                for(DataSnapshot movFilteredSnapshot : dataSnapshot.getChildren()) {
                    Movimento movimento = movFilteredSnapshot.getValue(Movimento.class);
                    movFilteredList.add(movimento);
                    substYear = movimento.getRealDate().substring(0, 4);
                    substMonth = movimento.getRealDate().substring(4, 6);
                    if (substYear.equals(data.substring(0, 4))) {
                        Annuale += Double.parseDouble(String.valueOf(movimento.getAmount()));
                        if(substMonth.equals(data.substring(4, 6))) {
                            Mensile += Double.parseDouble(String.valueOf(movimento.getAmount()));
                        }
                    }
                    Totale += Double.parseDouble(String.valueOf(movimento.getAmount()));
                }

                annuale.setText(String.valueOf(Annuale));
                mensile.setText(String.valueOf(Mensile));
                statistica.setText(String.valueOf(Totale));

                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onDateSet (DatePicker view,int year, int month, int dayOfMonth){

            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        data = String.valueOf(android.text.format.DateFormat.format("yyyyMMdd", calendar));
    }
}
