package com.icebug.android.studyunion;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class Faq_activity extends AppCompatActivity  implements View.OnClickListener {

    private FloatingActionButton fab;
    private ListView postList;
    private FirebaseListAdapter<FaqPost> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_activity);

        getSupportActionBar().setTitle("FAQ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fab = (FloatingActionButton)findViewById(R.id.faq_fab);

        postList = (ListView)findViewById(R.id.group_list);
        postList.setItemsCanFocus(false);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                PostCreateDialogue dialog = new PostCreateDialogue();

                Bundle bundle = new Bundle();
                bundle.putString("Type","FAQ");

                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(),"Post");
            }

        });

        displayGroups();

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FaqPost faqComments = (FaqPost) parent.getAdapter().getItem(position);

                Intent intent = new Intent(getApplicationContext(), FaqCommentActivity.class);

                intent.putExtra("FaqPost", faqComments.getPost());
                intent.putExtra("FaqPostOP", faqComments.getOP());
                intent.putExtra("FaqPostID", faqComments.getPostID());
                intent.putExtra("FaqComments", faqComments.getComment());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    private void displayGroups() {

        adapter = new FirebaseListAdapter<FaqPost>(this,FaqPost.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference().child("FaqPost")) {

            @Override
            protected void populateView(View v, FaqPost post , int position) {

                TextView messageText,messageUser;
                messageText = (TextView)v.findViewById(R.id.message_text);
                messageUser = (TextView)v.findViewById(R.id.message_user);

                messageText.setText(post.getPost());
                messageUser.setText(post.getOP());


            }
        };
        postList.setAdapter(adapter);
    }

}
