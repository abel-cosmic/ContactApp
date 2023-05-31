package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class SearchContact extends AppCompatActivity {
    private ContactDB contactDB;
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private TextView noResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);
        contactDB = new ContactDB(this);

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
        String searchQuery = searchEditText.getText().toString();
        List<Contact> searchResults = contactDB.searchContacts(searchQuery);
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
}
