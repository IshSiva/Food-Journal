package com.example.foodjournal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBox extends AppCompatDialogFragment {
    @NonNull

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState, final String foodName, Context context) {
        final DatabaseHelper databaseHelper = new DatabaseHelper(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Eaten the Food")
                .setMessage("Do you want to mark this food as eaten")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper.markAsEaten(foodName);
                    }
                });
        return alertDialog.create();
    }
}
