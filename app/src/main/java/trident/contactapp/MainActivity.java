package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import trident.contactapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ContactDB contactDB;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactDB = new ContactDB(this);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        contactDB.create(
                 new Contact(
                        "Dagim", "0963118546"
                )
        );
        System.out.println(contactDB.read("1").toString());
        contactDB.update(
                 new Contact(
                         1L, "Sami", "0942167872","sami@gmial.com","nefas silk lafto smt"
                )
        );
        contactDB.readAll().forEach(
                System.out::println
        );
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        contactDB.delete("2");
        contactDB.readAll().forEach(
                System.out::println
        );
    }
}