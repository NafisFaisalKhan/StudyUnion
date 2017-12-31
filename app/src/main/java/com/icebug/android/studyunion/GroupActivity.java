package com.icebug.android.studyunion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener  {

    private FirebaseListAdapter<GroupRoom> adapter;
    private DatabaseReference groupsRef;

    private ListView groupList;
    private TextView text;
    private static final String TAG = "MainActivity";
    private FloatingActionButton fab;

    final List<GroupRoom> groups = new LinkedList<>();

    private Intent intentthis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        getSupportActionBar().setTitle("Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intentthis = getIntent();

        text = (TextView)findViewById(R.id.text);
        groupList = (ListView)findViewById(R.id.group_list);
        groupList.setItemsCanFocus(false);
        fab = (FloatingActionButton)findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateGroup.class));
            }

        });

        displayGroups();


        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GroupRoom selectedGroup = (GroupRoom) parent.getAdapter().getItem(position);

                if(permission(selectedGroup.getSelectedUsers())) {

                    Intent intent = new Intent(getApplicationContext(), GroupChat.class);

                    intent.putExtra("GroupID", selectedGroup.getUid());
                    intent.putExtra("GroupName", selectedGroup.getGroupName());
                    intent.putExtra("GroupSize", selectedGroup.getNumberOfPeople());

                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "You are not added to this group!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //doesnt work
        groupList.setEmptyView(findViewById(R.id.empty_view));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    private Boolean permission(ArrayList<User> selectedUsers) {

    if(selectedUsers != null) {
        for (int i = 0; i < selectedUsers.size(); i++) {

            Log.v(TAG, selectedUsers.get(i).getUid());
            Log.v(TAG, currentFirebaseUser.getUid());

            if (selectedUsers.get(i).getUid().equals(currentFirebaseUser.getUid())) {

                return true;
            }

        }
    }
    return false;
    }

    private void displayGroups() {

        adapter = new FirebaseListAdapter<GroupRoom>(this,GroupRoom.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference().child("GroupRooms")) {

        @Override
        protected void populateView(View v, GroupRoom group, int position) {

            TextView messageText,messageUser;
            messageText = (TextView)v.findViewById(R.id.message_text);
            messageUser = (TextView)v.findViewById(R.id.message_user);

            messageText.setText(group.getGroupName());
            messageUser.setText(group.getGroupName());

            groups.add(group);

        }
    };
       // groupList.setEmptyView(findViewById(R.id.empty_view));
        groupList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }
}
