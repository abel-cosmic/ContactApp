package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import trident.contactapp.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {
    ActivityAddContactBinding binding;
    private ContactDB contactDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactDB = new ContactDB(this);

        binding.save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.nameInputEditText.getText().toString().equals("") || binding.phoneInputEditText.getText().toString().equals("")){
                            Toast.makeText(AddContactActivity.this, "Name and phone number are needed to add contact", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        contactDB.create(
                                new Contact(
                                        binding.nameInputEditText.getText().toString(),
                                        binding.phoneInputEditText.getText().toString(),
                                        binding.emailInputEditText.getText().toString(),
                                        binding.addressInputEditText.getText().toString(),
                                        0
                                )
                        );
                        System.out.println("=======================================================New========================================");
                        contactDB.readAll().forEach(
                                System.out::println
                        );
                        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);//TODO MAKE IT GO TO MAIN ACTIVITY WITH NEW RECYCLER VIEW
                    }
                }
        );

        binding.cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }
}