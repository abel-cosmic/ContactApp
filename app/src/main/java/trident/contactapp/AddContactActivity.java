package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trident.contactapp.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {
    ActivityAddContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // used to add fot testing 
//        contactList.forEach(
//                c ->{
//                    ContentValues values = new ContentValues();
//
//                    // fetching text from user
//                    values.put(ContactDataBase.name, c.getName());
//                    values.put(ContactDataBase.phoneNumber, c.getPhoneNumber());
//                    values.put(ContactDataBase.email, c.getEmail());
//                    values.put(ContactDataBase.address, c.getAddress());
//
//                    // inserting into database through content URI
//                    getContentResolver().insert(ContactDataBase.CONTENT_URI, values);
//                }
//        );

        binding.save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.nameInputEditText.getText().toString().equals("") || binding.phoneInputEditText.getText().toString().equals("")){
                            Toast.makeText(AddContactActivity.this, "Name and phone number are needed to add contact", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ContentValues values = new ContentValues();

                        // fetching text from user
                        values.put(ContactDataBase.name, binding.nameInputEditText.getText().toString());
                        values.put(ContactDataBase.phoneNumber, binding.phoneInputEditText.getText().toString());
                        values.put(ContactDataBase.email, binding.emailInputEditText.getText().toString());
                        values.put(ContactDataBase.address, binding.addressInputEditText.getText().toString());

                        // inserting into database through content URI
                        getContentResolver().insert(ContactDataBase.CONTENT_URI, values);

                        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
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



    List<Contact> contactList = new ArrayList<>(Arrays.asList(
            new Contact("John Smith", "1234567890", "john@example.com", "123 Main St", 0),
            new Contact("Alice Johnson", "9876543210", "alice@example.com", "456 Elm St", 1),
            new Contact("Michael Davis", "5555555555", "michael@example.com", "789 Oak St", 0),
            new Contact("Emily Wilson", "1112223333", "emily@example.com", "321 Maple Ave", 1),
            new Contact("Robert Brown", "9998887777", "robert@example.com", "789 Pine St", 0),
            new Contact("Sophia Clark", "4443332222", "sophia@example.com", "567 Cedar Rd", 0),
            new Contact("Daniel Lee", "7776665555", "daniel@example.com", "890 Elmwood Dr", 0),
            new Contact("Olivia Garcia", "2221110000", "olivia@example.com", "456 Oak Ave", 0),
            new Contact("Matthew Turner", "6667778888", "matthew@example.com", "123 Pine Rd", 1),
            new Contact("Emma Johnson", "5554443333", "emma@example.com", "789 Elm St", 0),
            new Contact("Sarah Adams", "7777777777", "sarah@example.com", "456 Cherry Ln", 0),
            new Contact("David Wilson", "8888888888", "david@example.com", "789 Oakwood Ave", 0),
            new Contact("Sophie Taylor", "3333333333", "sophie@example.com", "567 Maple Rd", 0),
            new Contact("Daniel Miller", "4444444444", "daniel@example.com", "890 Elm St", 1),
            new Contact("Oliver Anderson", "2222222222", "oliver@example.com", "456 Pine Ave", 0),
            new Contact("Charlotte Davis", "5555555555", "charlotte@example.com", "123 Elmwood Dr", 1),
            new Contact("Jacob Wilson", "6666666666", "jacob@example.com", "789 Maple St", 0),
            new Contact("Isabella Thompson", "7777777777", "isabella@example.com", "567 Oak Ave", 0),
            new Contact("Ethan Brown", "8888888888", "ethan@example.com", "890 Cedar Rd", 0),
            new Contact("Mia Jackson", "9999999999", "mia@example.com", "456 Elm St", 0)
    ));
}