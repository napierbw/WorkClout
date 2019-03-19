package com.example.workclout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {
    private EditText userName2, passWord2, rePassWord;
    private String userNameInput2, passWordInput2, rePassWordInput;
    private Button register2;
    private CheckBox coachCheck;

    private FirebaseFirestore mFirestore;


    /*****************************************************
     * Paul Cochran
     * This method produces a random unique User Id by
     * taking the user's email address, triming everything
     * before the @ symbol, and adding a random 4 digit
     * number to the end of it. This has a very slim chance
     * of producing a duplicate, so error handling is needed
     * at a later time.
     * @param email
     * @return
     */
    private String createUserId(String email){
        String UId;
        UId = email.substring(0, email.indexOf("@")).toLowerCase();  //trims part of email
        int digit = (int) Math.random()*9000; //Creates 4 digit string
        UId = UId + digit;     //adds two parts together

        return UId;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userName2=findViewById(R.id.UserNameID2);
        passWord2=findViewById(R.id.PassWordID2);
        register2=findViewById(R.id.RegisterID2);
        coachCheck=findViewById(R.id.coachCheck);

        mFirestore = FirebaseFirestore.getInstance();


        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameInput2 = userName2.getText().toString();// takes input
                passWordInput2 = passWord2.getText().toString();// takes input

                Map<String, String> dataToAdd = new HashMap<>();

                dataToAdd.put("username", userNameInput2);
                dataToAdd.put("password", passWordInput2);

                String loginType = "athletes";
                if (coachCheck.isChecked()){
                    loginType = "coaches";
                }

                String UId = createUserId(userNameInput2);

                mFirestore.collection(loginType).document(UId).set(dataToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Registration.this, "Username added", Toast.LENGTH_SHORT).show();
                        Intent loginSuccess = new Intent(Registration.this, SetupProfile.class);
                        startActivity(loginSuccess);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();

                        Toast.makeText(Registration.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
