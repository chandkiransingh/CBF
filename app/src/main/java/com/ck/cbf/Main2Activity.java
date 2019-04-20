package com.ck.cbf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    TextView textView;
    Button send;
    EditText editText;
    String sendstr;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference chat   = database.getReference("chats");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        final String Id = intent.getStringExtra("Id");
        textView = findViewById(R.id.textView2);
        editText = findViewById(R.id.editText2);
        send = findViewById(R.id.send);

        // Read from the database
        chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Value db", "Value is: " + value);
                textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendstr = editText.getText().toString();
                textView.setText(textView.getText()+"\n"+Id+": "+sendstr);
                editText.setText("");
                chat.setValue(textView.getText().toString());
            }
        });

        Log.d("text :", "onCreate: text written"+textView.getText().toString());

    }
}
