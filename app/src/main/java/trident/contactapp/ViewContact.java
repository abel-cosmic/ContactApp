package trident.contactapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import trident.contactapp.databinding.ActivityViewContactBinding;

public class ViewContact extends AppCompatActivity {
    ActivityViewContactBinding binding;
    private ContactDB contactDB;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton selectImageButton;


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
