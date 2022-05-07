package com.example.myapplicationasdf;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.Hashtable;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    FirebaseDatabase database;
    private GoogleSignInClient mGoogleSignInClient;
    EditText id1, password1,passck1,name1;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        id1 = (EditText) findViewById(R.id.Rid);
        password1 = (EditText) findViewById(R.id.Rpass);
        passck1 =(EditText) findViewById(R.id.Rpassck);
        name1=(EditText)findViewById(R.id.et_name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnRegister2 = (Button) findViewById(R.id.btn_register2);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stpassword1 = password1.getText().toString().trim();
                String stpasswordcheck=passck1.getText().toString().trim();
                String stEmail1 = id1.getText().toString().trim();
                String stName1=name1.getText().toString().trim();
                if (stEmail1.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (stpassword1.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (stpasswordcheck.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stName1.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if (!stpassword1.equals(stpasswordcheck)) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 불일치합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this,"Email:"+stEmail+",password :"+stpassword,Toast.LENGTH_LONG).show();
                mAuth.createUserWithEmailAndPassword(stEmail1, stpassword1)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    DatabaseReference myRef = database.getReference("유저").child(user.getUid());
                                    Hashtable<Object, String> numbers = new Hashtable<Object, String>();
                                    numbers.put("email", user.getEmail());
                                    numbers.put("name",stName1);
                                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                                    myRef.setValue(numbers);
                                    //updateUI(user);
                                    Intent in2 = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(in2);
                                } else {
                                    // If sign in failsMBJHN L;'/IOL;, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "회원가입 실패.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }

            public void onStart() {
                // super.onStart();
                // Check if user is signed in (non-null) and update UI accordingly.
                FirebaseUser currentUser = mAuth.getCurrentUser();
                //updateUI(currentUser);
            }
        });
    }
}