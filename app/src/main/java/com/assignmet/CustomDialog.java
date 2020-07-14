package com.assignmet;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomDialog {

    public static void statusDialog(int type, String message, Activity mContext, final DialogClick dialogClick)
    {
        final AlertDialog mProgressDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.status_dialog, null);
        ImageView dialog_img =  dialogView.findViewById(R.id.dialog_img);
        TextView dialog_text =  dialogView.findViewById(R.id.dialog_text);
        Button submit_btn =  dialogView.findViewById(R.id.submit_btn);
        Button cancel_btn =  dialogView.findViewById(R.id.cancel_btn);
        if(type == Constants.WARNING_DIALOG)
        {
            dialog_img.setImageResource(R.drawable.warning);
            cancel_btn.setVisibility(View.GONE);
            submit_btn.setText("OK");
        }else if(type == Constants.SUCCESS_DIALOG) {
            dialog_img.setImageResource(R.drawable.success);
            cancel_btn.setVisibility(View.GONE);
            submit_btn.setText("DONE");

        }else if(type == Constants.ERROR_DIALOG)
        {
            cancel_btn.setVisibility(View.GONE);
            submit_btn.setText("Close");
            dialog_img.setImageResource(R.drawable.error);
        }else if(type == Constants.WARNING_CONFIRM_DIALOG)
        {
            dialog_img.setImageResource(R.drawable.warning);
            cancel_btn.setVisibility(View.VISIBLE);
            submit_btn.setText("YES");
            cancel_btn.setText("NO");
        }
        dialog_text.setText(message);
        builder.setView(dialogView);
        builder.setCancelable(false);
        mProgressDialog = builder.create();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogClick!=null)
                dialogClick.statusDialogButtonClick(true);
                mProgressDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialogClick!=null)
                dialogClick.statusDialogButtonClick(false);
                mProgressDialog.dismiss();
            }
        });
        mProgressDialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        mProgressDialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        mProgressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomUpAnimation;
        mProgressDialog.show();
    }


}
