package sundarchaupal.logingoogle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name, email;
    Button singout;
    private FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView =findViewById(R.id.imageview);
        name= findViewById(R.id.name);
        email=findViewById(R.id.email);
        singout=findViewById(R.id.singout);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient= GoogleSignIn.getClient(this,gso);

        mAuth = FirebaseAuth.getInstance();
       //to get the current user data
        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
              //  finish();
                /*Intent intent=new  Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);*/
                googleSignInClient.signOut().addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });


            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        //to display user name
        name.setText(user.getDisplayName());
        //to display user email
        email.setText(user.getEmail());

        //to display the user name
        Glide.with(this).load(user.getPhotoUrl()).into(imageView);


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
        if (currentUser ==null)
        {
            Intent intent=new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);


        }
    }
}
