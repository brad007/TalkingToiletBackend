package com.software.fire.talkingtoiletbackend.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.software.fire.talkingtoiletbackend.R;
import com.software.fire.talkingtoiletbackend.models.TalkingToiletModel;
import com.software.fire.talkingtoiletbackend.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTalkingToiletRecycler;
    private FirebaseRecyclerAdapter<TalkingToiletModel, TalkingToiletViewHolder> mTalkingToiletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialiseScreen();
        setupAdapter();
    }

    private void setupAdapter() {
        mTalkingToiletAdapter = new FirebaseRecyclerAdapter<TalkingToiletModel, TalkingToiletViewHolder>(
                TalkingToiletModel.class,
                R.layout.item_layout_talking_toilet,
                TalkingToiletViewHolder.class,
                FirebaseDatabase.getInstance().getReference(Constants.TALKING_TOILET)
        ) {
            @Override
            protected void populateViewHolder(TalkingToiletViewHolder viewHolder, TalkingToiletModel model, int position) {

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class TalkingToiletViewHolder extends RecyclerView.ViewHolder {
        public TalkingToiletViewHolder(View itemView) {
            super(itemView);
        }
    }
}
