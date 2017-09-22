package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Build;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class DialogUtils {
    public static AlertDialog.Builder getDialog(Context context) {
       AlertDialog.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else{
            builder = new android.app.AlertDialog.Builder(context);
        }
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton(AppContext.getStringById(R.string.confirm), onOkClickListener);
        builder.setNegativeButton(AppContext.getStringById(R.string.cancel), onCancleClickListener);
        return builder;
    }
}
