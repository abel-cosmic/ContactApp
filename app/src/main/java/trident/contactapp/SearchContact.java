package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchContact extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);

        recyclerView = findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchEditText = findViewById(R.id.searchEditText);
        AppCompatImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> performSearch());

        noResultsTextView = findViewById(R.id.noRecentSearchesTextView);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(
                    CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        List<Contact> searchResults = searchContacts(searchEditText.getText().toString());
        ContactItemAdapter searchAdapter = new ContactItemAdapter(searchResults);
        recyclerView.setAdapter(searchAdapter);

        if (searchResults.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noResultsTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    List<Contact> searchContacts(String name){
        String sortOrder = ContactDataBase.name + " ASC";
        String selection = ContactDataBase.name + " LIKE ?";
        String[] selectionArgs = { "%" + name + "%" };

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
