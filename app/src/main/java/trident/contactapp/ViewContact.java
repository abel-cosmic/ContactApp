package trident.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import trident.contactapp.databinding.ActivityAddContactBinding;
import trident.contactapp.databinding.ActivityViewContactBinding;

public class ViewContact extends AppCompatActivity {
    ActivityViewContactBinding binding;
    private ContactDB contactDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contactDB = new ContactDB(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println("+++++++++++++++++++++++++++++++++++++++ view ============" + id);
        Contact contact = contactDB.read(id);

        if (contact.getIsFavorite() == 1){
            binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFF100")));
        }
        binding.contactName.setText(contact.getName());
        binding.phoneNumber.setText(contact.getPhoneNumber());
        binding.email.setText(contact.getEmail());
        binding.address.setText(contact.getAddress());

        binding.bottomNavigation.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.favourites:
                            if (contact.getIsFavorite() == 1){
                                binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                                contact.setIsFavorite(0);
                                contactDB.update(contact);
                            } else {
                                binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFF100")));
                                contact.setIsFavorite(1);
                                contactDB.update(contact);
                            }
                            break;
                        case R.id.edit:
                            Intent editContactIntent = new Intent(ViewContact.this, EditContactActivity.class);
                            editContactIntent.putExtra("id", contact.getId().toString());
                            startActivity(editContactIntent);
                            break;
                        case R.id.share:
                            break;
                        case R.id.delete:
                            contactDB.delete(id);
                            Intent goToMain = new Intent(ViewContact.this, MainActivity.class);
                            startActivity(goToMain);
                            break;
                    }
                    return true;
                }
        );
    }

}