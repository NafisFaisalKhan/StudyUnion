package com.icebug.android.studyunion;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by nafis on 11-Aug-17.
 */

public class PostCreateDialogue extends DialogFragment implements View.OnClickListener {

    private FaqPost mPost;
    private ProgressDialog mProgressDialog;
    private View mRootView;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mProgressDialog = new ProgressDialog(getContext());

        if(getArguments().get("Type").equals("FAQ")) {

            mPost = new FaqPost();
            mRootView = getActivity().getLayoutInflater().inflate(R.layout.post_create_dialogue, null);
            mRootView.findViewById(R.id.post_dialog_send_imageview).setOnClickListener(this);
        }

        if(getArguments().get("Type").equals("Add")){

            mRootView = getActivity().getLayoutInflater().inflate(R.layout.edit_type_dialogue, null);
            mRootView.findViewById(R.id.post_dialog_send_imageview).setOnClickListener(this);
        }

        builder.setView(mRootView);
        return builder.create();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_dialog_send_imageview:

                if(getArguments().get("Type").equals("FAQ")) {
                    sendPost();
                }

                if(getArguments().get("Type").equals("Add")){
                    editType();
                }
            break;

        }
    }

    private void editType() {

        mProgressDialog.setMessage("Adding...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

      //  ArrayList<String> info = new ArrayList<String>();

        TextView type = (TextView) mRootView.findViewById(R.id.post_dialog_edittext);
        TextView dueDate = (TextView) mRootView.findViewById(R.id.type_due_date);

        card_list_activity.events.add(type.getText().toString()+","+dueDate.getText().toString());

        mProgressDialog.dismiss();

        dismiss();

    }

    private void sendPost() {

        mProgressDialog.setMessage("Sending post...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        TextView postDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_edittext);

        String text = postDialogTextView.getText().toString();

        if(!text.equals("")) {

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("FaqPost").push();

            mPost.setPostID(databaseRef.getKey());

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDate = df.format(c.getTime());

            mPost.setOP(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            mPost.setTimeCreated(formattedDate);

            mPost.setPost(text);

            databaseRef.setValue(mPost);

            mProgressDialog.dismiss();

            dismiss();

        }else{ mProgressDialog.dismiss();}
    }


}
