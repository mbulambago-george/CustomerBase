package com.example.george.customerbase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private DatabaseReference mRef;
    private EditText nameInput,emailInput;
    private Button addButton;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
    }

    private void initWidgets() {
        mRef= FirebaseDatabase.getInstance().getReference().child("Users");
        nameInput=(EditText)findViewById(R.id.name);
        emailInput=(EditText)findViewById(R.id.email);

        addButton =(Button)findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameInput.getText().toString().trim();
                String email=emailInput.getText().toString().trim();


                if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(email)){
                    return;
                }else {
                    uploadUserData(name,email);
                }

            }
        });




    }

    private void uploadUserData(String name, String email) {
        Log.d(TAG, "uploadUserData: Sending user data");
        final ProgressDialog dialo=new ProgressDialog(this);
        dialo.setMessage("Uploading user data");
        dialo.show();
        Map<String, String> userMap=new HashMap<>();
        userMap.put("name",name);
        userMap.put("email",email);

        mRef.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialo.dismiss();

                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Not uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

