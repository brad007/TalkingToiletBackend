package com.software.fire.talkingtoiletbackend.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.software.fire.talkingtoiletbackend.utils.Constants;

/**
 * Created by Brad on 12/5/2016.
 */

@SuppressLint("ValidFragment")
public class DeleteDialog extends DialogFragment {

    private String mUID;

    public DeleteDialog(String UID) {
        mUID = UID;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Deleting Toilet Talk");
        builder.setMessage("Are you sure you want to delete this toilet talk");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference deleteRef = FirebaseDatabase.getInstance()
                        .getReference(Constants.TALKING_TOILET)
                        .child(mUID);
                deleteRef.setValue(null);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
