package it.uninsubria.pdm.money;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LayoutListaMovimenti extends ArrayAdapter<Movimento> {

    private Activity context ;
    private List<Movimento> mov_list ;

    public LayoutListaMovimenti(Activity context , List<Movimento> mov_list){
        super (context , R.layout.layout_lista_movimenti, mov_list);
        this.context = context;
        this.mov_list = mov_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        if( convertView == null ){
             inflater.inflate(R.layout.activity_list_view_movimenti, parent, false);
        }

        View listViewItem = inflater.inflate(R.layout.layout_lista_movimenti, null , true);

        TextView TextViewData = listViewItem.findViewById(R.id.Data);
        TextView TextViewImporto = listViewItem.findViewById(R.id.Importo);
        TextView TextViewCausale = listViewItem.findViewById(R.id.Causale);
        TextView TextViewUtente = listViewItem.findViewById(R.id.Utente);
        TextView TextViewArea = listViewItem.findViewById(R.id.Macroarea);


        for(int i=0; i < mov_list.size(); i++) {
            Movimento mov = mov_list.get(position);
            String giorno = mov.getDate();
            String cifra = Double.toString((mov.getAmount()));
            String descr = mov.getCausale();
            String user = mov.getU();
            String area = mov.getMacroarea();

            TextViewData.setText(giorno);
            TextViewImporto.setText(cifra);
            TextViewCausale.setText(descr);
            TextViewUtente.setText(user);
            TextViewArea.setText(area);

        }
        return listViewItem;
    }
}
