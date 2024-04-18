package isabel.vitoria.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isabel.vitoria.lista.R;
import isabel.vitoria.lista.activity.MainActivity;
import isabel.vitoria.lista.model.MyItem;

public class MyAdapter extends RecyclerView.Adapter { //herda o adapter do Recycle

    MainActivity mainActivity;
    List<MyItem> itens;

    //o construtor da classe aceitando dois parâmetros
    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity; //instância
        this.itens = itens; //lista de objetos
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity); //inflator de layouts
        View v = inflater.inflate(R.layout.item_list,parent,false); //cria os elementos de interface
        return new MyViewHolder(v); //guarda o objeto e retorna pela função
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //preenche a UI
        MyItem myItem = itens.get(position); //indica qual elemento da lista deve ser usado para preencher o item

        View v = holder.itemView; //guarda os itens de interface

        //preenche a UI com os dados
        ImageView imvphoto = v.findViewById(R.id.imvPhoto);
        imvphoto.setImageURI(myItem.photo);

        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        TextView tvDesc = v.findViewById(R.id.tvDesc);
        tvDesc.setText(myItem.desc);
    }

    @Override
    public int getItemCount() { //informa quantos elementos a lista possui
        return itens.size();
    }
}
