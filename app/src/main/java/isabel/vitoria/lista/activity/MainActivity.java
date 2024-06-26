package isabel.vitoria.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import isabel.vitoria.lista.R;
import isabel.vitoria.lista.adapter.MyAdapter;
import isabel.vitoria.lista.model.MainActivityViewModel;
import isabel.vitoria.lista.model.MyItem;
import isabel.vitoria.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST =1;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem); //botão FAB
        fabAddItem.setOnClickListener(new View.OnClickListener() { //registramos um ouvidor de cliques
            @Override
            public void onClick(View v) { //realizamos a navegação pelo intent explícito para ir para a outra tela
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST); //Executa o intent / startActivityForResult = retorna os dados em algum momento

            }
        });

        RecyclerView rvItens = findViewById(R.id.rvItens); //obtemos o Recycle

        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); //ViewModel referente a MainActivity (MainActivityViewModel) é obtido
        List<MyItem> itens = vm.getItens(); //a lista de itens é obtida a partir do ViewModel e repassada para o Adapter

        myAdapter = new MyAdapter(this,itens); //cria o MyAdapter
        rvItens.setAdapter(myAdapter); //seta no Recycle

        rvItens.setHasFixedSize(true); //indica q não há variação de tamanho

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); //cria o gerenciador de layout do tipo linear
        rvItens.setLayoutManager(layoutManager); //seta no Recycle

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL); //cria uma linha
        rvItens.addItemDecoration(dividerItemDecoration); //seta no Recycle

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) { //verifica se foi cumprido
                MyItem myItem = new MyItem(); //cria uma instância
                myItem.title = data.getStringExtra("title"); //obtemos os dados retornados e guardamos
                myItem.desc = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100); //carrega a imagem e a guarda dentro de um Bitmap
                    myItem.photo = photo; //guardamos o bitmap
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class); //obtemos o ViewModel
                List<MyItem> itens = vm.getItens(); //obtemos a lista de itens que o ViewModel guarda

                itens.add(myItem); //adiciona o item a uma lista de itens
                myAdapter.notifyItemInserted(itens.size()-1); //notifica para q um novo item seja mostrado
            }
        }
    }



}