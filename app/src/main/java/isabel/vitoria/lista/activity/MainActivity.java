package isabel.vitoria.lista.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import isabel.vitoria.lista.R;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem); //botão FAB
        fabAddItem.setOnClickListener(new View.OnClickListener() { //registramos um ouvidor
            de cliques
            @Override
            public void onClick(View v) { //realizamos a navegação pelo intent explícito para ir para a outra tela
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST); //Executa o intent / startActivityForResult = retorna os dados em algum momento

            }
        });


    }
}