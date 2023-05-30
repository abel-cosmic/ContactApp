package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import trident.contactapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ContactDB contactDB;
    private ActivityMainBinding binding;
    private boolean favoritesTab = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactDB = new ContactDB(this);

//        contactDB.readAll().forEach(
//                contact -> {
//                    contactDB.delete(contact.getId().toString());
//                }
//        );
//        contactList.forEach(
//                c ->{
//                    contactDB.create(c);
//                }
//        );
//
//        contactDB.readAll().forEach(
//                System.out::println
//        );


        RecyclerView recyclerView = binding.contactsRecyclerView;
//        ContactItemAdapter adapter = new ContactItemAdapter(contactDB.readAllFavorites());
        ContactItemAdapter adapter2 = new ContactItemAdapter(contactDB.readAll());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter2);


        binding.add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                        startActivity(intent);
                    }
                }
        );
        binding.favourites.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (favoritesTab){
                            RecyclerView recyclerView = binding.contactsRecyclerView;
                            ContactItemAdapter adapter = new ContactItemAdapter(contactDB.readAll());
                            recyclerView.setHasFixedSize(false);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(adapter);
                            binding.favourites.setImageDrawable(getDrawable(R.drawable.unfilled_star));
                            favoritesTab = false;
                        } else {
                            RecyclerView recyclerView = binding.contactsRecyclerView;
                            ContactItemAdapter adapter = new ContactItemAdapter(contactDB.readAllFavorites());
                            recyclerView.setHasFixedSize(false);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(adapter);
                            binding.favourites.setImageDrawable(getDrawable(R.drawable.filled_star));
                            favoritesTab = true;
                        }
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