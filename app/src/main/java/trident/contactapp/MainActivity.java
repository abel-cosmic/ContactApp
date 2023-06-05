package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trident.contactapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean favoritesTab = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchContact.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = binding.contactsRecyclerView;
        ContactItemAdapter adapter2 = new ContactItemAdapter(readAll());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter2);


        binding.add.setOnClickListener(
                view -> {
                    Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                    startActivity(intent);
                }
        );
        binding.favourites.setOnClickListener(
                view -> {
                    if (favoritesTab){
                        RecyclerView recyclerView1 = binding.contactsRecyclerView;
                        ContactItemAdapter adapter = new ContactItemAdapter(readAll());
                        recyclerView1.setHasFixedSize(false);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView1.setAdapter(adapter);
                        binding.favourites.setImageDrawable(getDrawable(R.drawable.unfilled_star));
                        favoritesTab = false;
                    } else {
                        RecyclerView recyclerView1 = binding.contactsRecyclerView;
                        ContactItemAdapter adapter = new ContactItemAdapter(readAllFavorites());
                        recyclerView1.setHasFixedSize(false);
                        recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView1.setAdapter(adapter);
                        binding.favourites.setImageDrawable(getDrawable(R.drawable.filled_star));
                        favoritesTab = true;
                    }
                }
        );
    }

    private List<Contact> readAll(){
        String sortOrder =
                ContactDataBase.name + " ASC";
        Cursor cursor = getContentResolver().query(
                ContactDataBase.CONTENT_URI,
                null, null, null, sortOrder);

        // checking if the column exists

        int idIndex = cursor.getColumnIndex(ContactDataBase.id);
        int nameIndex = cursor.getColumnIndex(ContactDataBase.name);
        int phoneNumberIndex = cursor.getColumnIndex(ContactDataBase.phoneNumber);
        int emailIndex = cursor.getColumnIndex(ContactDataBase.email);
        int addressIndex = cursor.getColumnIndex(ContactDataBase.address);
        int isFavoriteIndex = cursor.getColumnIndex(ContactDataBase.isFavorite);
        if (idIndex == -1 || nameIndex == -1 || phoneNumberIndex == -1 || addressIndex == -1 || isFavoriteIndex == -1){
            return new ArrayList<>();
        }

        List<Contact> contacts= new ArrayList<>();
        while(cursor.moveToNext()) {
            Contact contact = new Contact(
                    cursor.getInt(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(phoneNumberIndex),
                    cursor.getString(emailIndex),
                    cursor.getString(addressIndex),
                    cursor.getInt(isFavoriteIndex)
            );
            contacts.add(contact);
        }
        cursor.close();

        return contacts;
    }

    List<Contact> readAllFavorites(){
        String sortOrder = ContactDataBase.name + " ASC";
        String selection = ContactDataBase.isFavorite + " = ?";
        String[] selectionArgs = { "1" };
        Cursor cursor = getContentResolver().query(
                ContactDataBase.CONTENT_URI,
                null, selection, selectionArgs, sortOrder);

        int idIndex = cursor.getColumnIndex(ContactDataBase.id);
        int nameIndex = cursor.getColumnIndex(ContactDataBase.name);
        int phoneNumberIndex = cursor.getColumnIndex(ContactDataBase.phoneNumber);
        int emailIndex = cursor.getColumnIndex(ContactDataBase.email);
        int addressIndex = cursor.getColumnIndex(ContactDataBase.address);
        int isFavoriteIndex = cursor.getColumnIndex(ContactDataBase.isFavorite);
        if (idIndex == -1 || nameIndex == -1 || phoneNumberIndex == -1 || addressIndex == -1 || isFavoriteIndex == -1){
            return new ArrayList<>();
        }

        List<Contact> contacts= new ArrayList<>();
        while(cursor.moveToNext()) {
            Contact contact = new Contact(
                    cursor.getInt(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(phoneNumberIndex),
                    cursor.getString(emailIndex),
                    cursor.getString(addressIndex),
                    cursor.getInt(isFavoriteIndex)
            );
            contacts.add(contact);
        }
        cursor.close();

        return contacts;
    }

}