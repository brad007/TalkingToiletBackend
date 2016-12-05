package com.software.fire.talkingtoiletbackend.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.software.fire.talkingtoiletbackend.R;
import com.software.fire.talkingtoiletbackend.models.TalkingToiletModel;
import com.software.fire.talkingtoiletbackend.utils.Constants;

import static com.software.fire.talkingtoiletbackend.utils.Constants.UID;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton mFoldedRB;
    private RadioButton mCrumpledRB;
    private TextView mThinkingET;

    private boolean mIsCrumpled;
    private String mThoughts;
    private String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialiseScreen();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              sendEditedDataToFirebse();
            }
        });
    }

    private void sendEditedDataToFirebse(){
        showProgressDialog();
        TalkingToiletModel model = new TalkingToiletModel();
        String thoughts = mThinkingET.getText().toString();

        model.setIsCrumpled(mIsCrumpled + "");
        model.setThoughts(thoughts);
        model.setUid(mUID);


        DatabaseReference talkingToiletRef = FirebaseDatabase.getInstance()
                .getReference(Constants.TALKING_TOILET).child(mUID);


        talkingToiletRef.setValue(model).addOnSuccessListener(EditActivity.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        }).addOnFailureListener(EditActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.this, "Unable to update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {
        ProgressDialog pd = new ProgressDialog(EditActivity.this);
        pd.setMessage("Calculating Results");
        pd.setCancelable(false);
        pd.show();
    }

    private void initialiseScreen() {
        mFoldedRB = (RadioButton) findViewById(R.id.folded_radio);
        mCrumpledRB = (RadioButton) findViewById(R.id.crumple_radio);
        mThinkingET = (EditText) findViewById(R.id.thinkinget);

        mCrumpledRB.setOnClickListener(this);
        mFoldedRB.setOnClickListener(this);

        Intent intent = getIntent();
        mUID = intent.getStringExtra(UID);
        mIsCrumpled = intent.getBooleanExtra(Constants.IS_CRUMPLED, false);
        mThoughts = intent.getStringExtra(Constants.THOUGHTS);

        if (mIsCrumpled) {
            mCrumpledRB.setChecked(mIsCrumpled);
        } else {
            mFoldedRB.setChecked(true);
        }
        mThinkingET.setText(mThoughts);

    }

    @Override
    public void onClick(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.crumple_radio:
                if (checked)
                    mIsCrumpled = true;
                break;
            case R.id.folded_radio:
                if (checked)
                    mIsCrumpled = false;
                break;
        }
    }
}
