package isabel.vitoria.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class NewItemActivityViewModel extends ViewModel {
    Uri selectPhotoLocation = null; //guarda somente o endereço Uri

    public Uri getSelectPhotoLocation() { //obtém os itens da lista
        return selectPhotoLocation;
    }

    public void setSelectPhotoLocation(Uri selectPhotoLocation) { //seta o endereço Uri
        this.selectPhotoLocation = selectPhotoLocation;
    }
}
