package com.example.foodon;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class ReusableCodeForAll {
    public static void showAlert(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        context.startActivity(new Intent(context, ChefLoginActivity.class));
                        ((ChefSignUpActivity) context).finish();
                    }
                }).show();
    }
}
