package com.alinrosu.app.jakewhartonrepolistexample.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.alinrosu.app.jakewhartonrepolistexample.R;
import com.alinrosu.app.jakewhartonrepolistexample.interfaces.StringCallback;

/**
 * Created by alin on 9/17/2017.
 */

public class Alert {
    public static void show(Context context, String titleS, String desc, final StringCallback back) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_alert);
            TextView title = (TextView) dialog.findViewById(R.id.textTitle);
            TextView description = (TextView) dialog.findViewById(R.id.textDescription);
            description.setText(desc);
            title.setText(titleS);
            dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            Log.i("", "dialog error is : " + e.getMessage());
        }
    }
}
