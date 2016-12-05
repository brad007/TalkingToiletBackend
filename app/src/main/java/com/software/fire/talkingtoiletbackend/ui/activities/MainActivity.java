package com.software.fire.talkingtoiletbackend.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.software.fire.talkingtoiletbackend.R;
import com.software.fire.talkingtoiletbackend.models.TalkingToiletModel;
import com.software.fire.talkingtoiletbackend.ui.dialogs.DeleteDialog;
import com.software.fire.talkingtoiletbackend.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTalkingToiletRecycler;
    private FirebaseRecyclerAdapter<TalkingToiletModel, TalkingToiletViewHolder> mTalkingToiletAdapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
            }
        };

        initialiseScreen();
        setupAdapter();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }



    private void showToast(int resource) {
        Toast.makeText(MainActivity.this, getString(resource), Toast.LENGTH_SHORT).show();
    }

    private void setupAdapter() {
        mTalkingToiletAdapter = new FirebaseRecyclerAdapter<TalkingToiletModel, TalkingToiletViewHolder>(
                TalkingToiletModel.class,
                R.layout.item_layout_talking_toilet,
                TalkingToiletViewHolder.class,
                FirebaseDatabase.getInstance().getReference(Constants.TALKING_TOILET)
        ) {
            @Override
            protected void populateViewHolder(TalkingToiletViewHolder viewHolder, final TalkingToiletModel model, int position) {

                if (model.getIsCrumpled().equals("true")) {
                    viewHolder.setMethodText("Crumpled");
                } else {
                    viewHolder.setMethodText("Folded");
                }
                viewHolder.setThinkingText(model.getThoughts());

                viewHolder.delete_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteDialog deleteDialog = new DeleteDialog(model.getUid());
                        deleteDialog.show(getSupportFragmentManager(), null);
                    }
                });

                viewHolder.edit_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra(Constants.UID, model.getUid());
                        intent.putExtra(Constants.IS_CRUMPLED, model.getIsCrumpled());
                        intent.putExtra(Constants.THOUGHTS, model.getThoughts());
                        startActivity(intent);
                    }
                });
            }
        };
        mTalkingToiletRecycler.setAdapter(mTalkingToiletAdapter);
    }

    private void initialiseScreen() {
        mTalkingToiletRecycler = (RecyclerView) findViewById(R.id.talking_toilet_rv);
        mTalkingToiletRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_stats) {
            startActivity(new Intent(MainActivity.this, ViewStatsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class TalkingToiletViewHolder extends RecyclerView.ViewHolder {
        private TextView method_tv;
        private TextView thinking_tv;

        private ImageView delete_iv;
        private ImageView edit_tv;

        public TalkingToiletViewHolder(View itemView) {
            super(itemView);
            method_tv = (TextView) itemView.findViewById(R.id.method_tv);
            thinking_tv = (TextView) itemView.findViewById(R.id.thinking_tv);
            delete_iv = (ImageView) itemView.findViewById(R.id.delete_iv);
            edit_tv = (ImageView) itemView.findViewById(R.id.edit_iv);
        }

        public void setMethodText(String methodText) {
            method_tv.setText(methodText);
        }

        public void setThinkingText(String thinkingText) {
            thinking_tv.setText(thinkingText);
        }
    }
}
