package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import trident.contactapp.databinding.ActivityEditContactBinding;
import trident.contactapp.databinding.ActivityViewContactBinding;

public class EditContactActivity extends AppCompatActivity {
    ActivityEditContactBinding binding;
    private ContactDB contactDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactDB = new ContactDB(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println("+++++++++++++++++++++++++++++++++++++++ edit =========" + id);
        Contact contact = contactDB.read(id);
        binding.nameInputEditText.setText(contact.getName());
        binding.phoneInputEditText.setText(contact.getPhoneNumber());
        binding.emailInputEditText.setText(contact.getEmail());
        binding.addressInputEditText.setText(contact.getAddress());
        binding.save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.nameInputEditText.getText().toString().equals("") || binding.phoneInputEditText.getText().toString().equals("")){
                            Toast.makeText(EditContactActivity.this, "Name and phone number are needed to edit contact", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        contact.setName(binding.nameInputEditText.getText().toString());
                        contact.setPhoneNumber(binding.phoneInputEditText.getText().toString());
                        contact.setEmail(binding.emailInputEditText.getText().toString());
                        contact.setAddress(binding.addressInputEditText.getText().toString());
                        contactDB.update(contact);
                        Intent viewContactIntent = new Intent(EditContactActivity.this, ViewContact.class);
                        viewContactIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        viewContactIntent.putExtra("id",contact.getId().toString());
                        startActivity(viewContactIntent);
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