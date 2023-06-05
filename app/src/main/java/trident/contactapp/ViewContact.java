package trident.contactapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileNotFoundException;
import java.io.InputStream;

import trident.contactapp.databinding.ActivityViewContactBinding;

public class ViewContact extends AppCompatActivity {
    ActivityViewContactBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton selectImageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        Contact contact = read(id);

        if (contact.getIsFavorite() == 1) {
            binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFF100")));
        }
        if (contact.getEmail().equals("") && contact.getAddress().equals("")) {
            binding.emailHolder.setVisibility(View.INVISIBLE);
            binding.addressHolder.setVisibility(View.INVISIBLE);
        }
        binding.contactName.setText(contact.getName());
        binding.phoneNumber.setText(contact.getPhoneNumber());
        binding.email.setText(contact.getEmail());
        binding.address.setText(contact.getAddress());

        selectImageButton = findViewById(R.id.select_image_button);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        binding.bottomNavigation.setOnItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.favourites:
                            if (contact.getIsFavorite() == 1) {
                                binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                                contact.setIsFavorite(0);
                                removeFromFavorite(contact.getId().toString());
                            } else {
                                binding.bottomNavigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFF100")));
                                contact.setIsFavorite(1);
                                addToFavorite(contact.getId().toString());
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
                            delete(id);
                            Intent goToMain = new Intent(ViewContact.this, MainActivity.class);
                            startActivity(goToMain);
                            break;
                    }
                    return true;
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
    private void addToFavorite(String id){
        ContentValues values = new ContentValues();
        values.put(ContactDataBase.isFavorite, 1);
        String selection = ContactDataBase.id + " = ?";
        String[] selectionArgs = { id };
        int count = getContentResolver().update(ContactDataBase.CONTENT_URI, values, selection, selectionArgs);
    }
    private void removeFromFavorite(String id){
        ContentValues values = new ContentValues();
        values.put(ContactDataBase.isFavorite, 0);
        String selection = ContactDataBase.id + " = ?";
        String[] selectionArgs = { id };
        int count = getContentResolver().update(ContactDataBase.CONTENT_URI, values, selection, selectionArgs);
    }

    private void delete(String id){
        String selection = ContactDataBase.id + " = ?";
        String[] selectionArgs = { id };
        int count = getContentResolver().delete(ContactDataBase.CONTENT_URI, selection, selectionArgs);
    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Convert the bitmap to a circular shape
                Bitmap circularBitmap = getCircleBitmap(bitmap);
                selectImageButton.setImageBitmap(circularBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to convert the bitmap to a circular shape
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap circleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        return circleBitmap;
    }


}
