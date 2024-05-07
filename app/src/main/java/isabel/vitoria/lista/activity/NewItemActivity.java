package isabel.vitoria.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import isabel.vitoria.lista.R;
import isabel.vitoria.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class); //obtem o ViewModel referente a NewItemActivity

        Uri selectPhotoLocation = vm.getSelectPhotoLocation(); //obtem o endereço Uri
        if (selectPhotoLocation != null) { //se não for nulo, o usuário escolheu uma imagem
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview); //seta a imagem no ImagemView
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }

        ImageButton imgCI = findViewById(R.id.imbCI); //obtemos o botão para selecionar a imagem
        imgCI.setOnClickListener(new View.OnClickListener() { //definimos o ouvidor de cliques
            @Override
            public void onClick(View v) { //abrir a galeria
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //Intent implícito para abrir o documento
                photoPickerIntent.setType("image/*"); //seleciona apenas imagem
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST); //entrega a imagem
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem); //obtém o botão

        btnAddItem.setOnClickListener(new View.OnClickListener() { //ouvidor de cliques
            @Override
            public void onClick(View v) { //verifica se os campos foram preenchidos pelo usuário
                Uri photoSelected = vm.getSelectPhotoLocation(); //pega as imagens de vm e salva
                if (photoSelected == null) { //se nenhuma foto for escolhida exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) { //se nenhum título for escolhido exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();
                if (desc.isEmpty()) { //se nenhuma descrição for escolhida exibe uma mensagem de erro
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_SHORT).show();
                    return;
                }

                // mostram como uma Activity pode retornar dados para a Activity que a chamou
                Intent i = new Intent(); //guarda os dados a serem retornados
                i.setData(photoSelected); //setamos o Uri da imagem escolhida dentro do Intent.
                i.putExtra("title", title); //setamos o título
                i.putExtra("description", desc); //setamos a descrição
                setResult(Activity.RESULT_OK, i); // indica o resultado  da Activity /  indica que há dados de retorno
                finish(); //Activity finalizada
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_PICKER_REQUEST) { //verifica se requestCode é referente ao fornecido na chamada
            if (resultCode == Activity.RESULT_OK) { //verfica se tá certo
                Uri photoSelected = data.getData(); //se tiver obtém isso
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview); //obtem p URI da imagem e guarda

                imvfotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class); //obtem o ViewModel

                vm.setSelectPhotoLocation(photoSelected); //guarda o endereço da imagem
            }
        }
    }
}