package com.turtleriot.storage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StorageFotos {

    private StorageReference sfFotoAcciones;
    public static final int RC_FOTO_PLAYA = 1;

    private String foto;

    public StorageFotos(){
        sfFotoAcciones = FirebaseStorage.getInstance().getReference().child("FotosAcciones");
    }

    public void subirFoto(Intent data, Activity activity) {
        Uri selectedUri = data.getData();
        StorageReference fotoRef = sfFotoAcciones.child(selectedUri.getLastPathSegment());
        UploadTask ut = fotoRef.putFile(selectedUri);
        ut.addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        foto = uri.toString();
                    }
                });
            }
        });
    }

    public String getFoto() {
        return foto;
    }
}
