package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import trident.contactapp.databinding.ActivityMainBinding;
import trident.contactapp.databinding.ActivityPhoneBinding;

public class PhoneActivity extends AppCompatActivity {
    ActivityPhoneBinding binding;
    private ContactDB contactDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactDB = new ContactDB(this);

        binding.save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.nameInputEditText.toString().equals("") || binding.phoneInputEditText.toString().equals("")){
                            Toast.makeText(PhoneActivity.this, "Name and phone number are needed to add contact", Toast.LENGTH_SHORT).show();
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
                        finish();//TODO MAKE IT GO TO MAIN ACTIVITY WITH NEW RECYCLER VIEW
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