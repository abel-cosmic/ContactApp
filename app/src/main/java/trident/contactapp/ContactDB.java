package trident.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class ContactDB {
    ContactDBHelper dbHelper;
    ContactDB(Context context) {
         dbHelper = new ContactDBHelper(context);

    }
    public static class FeedEntry implements BaseColumns { //This class has all the columns of the table
        public static final String TABLE_NAME = "contact";
        public static final String NAME = "name";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL = "email";
        public static final String ADDRESS = "address";
    }

    private static final String SQL_CREATE_ENTRIES = //This is THE QUERY THAT CREATES YOUR TABLE IN YOUR ANDROID
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.NAME + " TEXT NOT NULL," +
                    FeedEntry.PHONE_NUMBER + " TEXT NOT NULL," +
                    FeedEntry.EMAIL + " TEXT," +
                    FeedEntry.ADDRESS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public class ContactDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Contacts.db";

        public ContactDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    void create(@NonNull Contact contact){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.NAME, contact.getName());
        values.put(FeedEntry.PHONE_NUMBER, contact.getPhoneNumber());
        values.put(FeedEntry.EMAIL, contact.getEmail());
        values.put(FeedEntry.ADDRESS, contact.getAddress());
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }
    @NonNull
    Contact read(String id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = FeedEntry._ID + " = ?";
        String[] selectionArgs = { id };
        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                null,
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,
                null,
                null
        );

        int columnIndexOfID = 0 ;
        int columnIndexOfName = 0 ;
        int columnIndexOfPhoneNumber = 0 ;
        int columnIndexOfEmail = 0 ;
        int columnIndexOfAddress = 0 ;
        try {
            columnIndexOfID = cursor.getColumnIndex(FeedEntry._ID);
            columnIndexOfName = cursor.getColumnIndexOrThrow(FeedEntry.NAME);
            columnIndexOfPhoneNumber = cursor.getColumnIndexOrThrow(FeedEntry.PHONE_NUMBER);
            columnIndexOfEmail = cursor.getColumnIndexOrThrow(FeedEntry.EMAIL);
            columnIndexOfAddress = cursor.getColumnIndexOrThrow(FeedEntry.ADDRESS);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        cursor.moveToNext();
        return new Contact(
                cursor.getLong(columnIndexOfID),
                cursor.getString(columnIndexOfName),
                cursor.getString(columnIndexOfPhoneNumber),
                cursor.getString(columnIndexOfEmail),
                cursor.getString(columnIndexOfAddress)
        );
    }
    @NonNull
    List<Contact> readAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sortOrder =
                FeedEntry.NAME + " ASC";
        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );

        int columnIndexOfID = 0 ;
        int columnIndexOfName = 0 ;
        int columnIndexOfPhoneNumber = 0 ;
        int columnIndexOfEmail = 0 ;
        int columnIndexOfAddress = 0 ;
        try {
            columnIndexOfID = cursor.getColumnIndex(FeedEntry._ID);
            columnIndexOfName = cursor.getColumnIndexOrThrow(FeedEntry.NAME);
            columnIndexOfPhoneNumber = cursor.getColumnIndexOrThrow(FeedEntry.PHONE_NUMBER);
            columnIndexOfEmail = cursor.getColumnIndexOrThrow(FeedEntry.EMAIL);
            columnIndexOfAddress = cursor.getColumnIndexOrThrow(FeedEntry.ADDRESS);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        List<Contact> contacts= new ArrayList<>();
        while(cursor.moveToNext()) {
            Contact contact = new Contact(
                    cursor.getLong(columnIndexOfID),
                    cursor.getString(columnIndexOfName),
                    cursor.getString(columnIndexOfPhoneNumber),
                    cursor.getString(columnIndexOfEmail),
                    cursor.getString(columnIndexOfAddress)
            );
            contacts.add(contact);
        }
        cursor.close();

        return contacts;
    }
    void delete(@NonNull String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = FeedEntry._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { id };
        // Issue SQL statement.
        int deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
    int update(@NonNull Contact contact){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.NAME, contact.getName());
        values.put(FeedEntry.PHONE_NUMBER, contact.getPhoneNumber());
        values.put(FeedEntry.EMAIL, contact.getEmail());
        values.put(FeedEntry.ADDRESS, contact.getAddress());


        String selection = FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { contact.getId() + ""};

        return db.update(
                FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
