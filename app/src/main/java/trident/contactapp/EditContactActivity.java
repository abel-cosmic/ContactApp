package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import trident.contactapp.databinding.ActivityEditContactBinding;
import trident.contactapp.databinding.ActivityViewContactBinding;

public class EditContactActivity extends AppCompatActivity {
    ActivityEditContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Contact contact = read(id);
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
                        update(contact);

                        Intent viewContactIntent = new Intent(EditContactActivity.this, MainActivity.class);
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

    Contact read(String id){
        String selection = ContactDataBase.id + " = ?";
        String[] selectionArgs = { id };
        Cursor cursor = getContentResolver().query(
                ContactDataBase.CONTENT_URI,
                null, selection, selectionArgs, null);

        int idIndex = cursor.getColumnIndex(ContactDataBase.id);
        int nameIndex = cursor.getColumnIndex(ContactDataBase.name);
        int phoneNumberIndex = cursor.getColumnIndex(ContactDataBase.phoneNumber);
        int emailIndex = cursor.getColumnIndex(ContactDataBase.email);
        int addressIndex = cursor.getColumnIndex(ContactDataBase.address);
        int isFavoriteIndex = cursor.getColumnIndex(ContactDataBase.isFavorite);

        cursor.moveToNext();
        Contact contact = new Contact(
                cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(phoneNumberIndex),
                cursor.getString(emailIndex),
                cursor.getString(addressIndex),
                cursor.getInt(isFavoriteIndex)
        );

        cursor.close();

        return contact;
    }

    void update(Contact contact){
        ContentValues values = new ContentValues();

        values.put(ContactDataBase.name, contact.getName());
        values.put(ContactDataBase.phoneNumber, contact.getPhoneNumber());
        values.put(ContactDataBase.email, contact.getEmail());
        values.put(ContactDataBase.address, contact.getAddress());

        String selection = ContactDataBase.id + " = ?";
        String[] selectionArgs = { contact.getId().toString() };

        int count = getContentResolver().update(ContactDataBase.CONTENT_URI, values, selection, selectionArgs);

    }
}