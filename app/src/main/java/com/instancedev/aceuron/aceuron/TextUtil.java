package com.instancedev.aceuron.aceuron;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.AlgorithmParameters;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by instance on 06/09/17.
 */

public class TextUtil {

    // TODO For this whole file: Documentation
    // TODO Might wanna refactor this into two files: Database / Security

    /* Database related utilities */

    static class Note
    {
        public String title;
        public String content;
        public boolean encrypted;
    };

    public static String SQL_CREATE_NOTES_TBL = "CREATE TABLE notes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, " +
            "content TEXT, " +
            "encrypted INTEGER)";



    // You can get the context with getContext();
    public static long insertNote(Context c, String title, String content, boolean encrypted) {
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("encrypted", encrypted ? 1 : 0); // 1 = true, 0 = false
        long newRowId = db.insert("notes", null, values);
        return newRowId;
    }

    // You can get the context with getContext();
    public static List<Note> getNoteByTitle(Context c, String title) {
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getReadableDatabase();


        String[] projection = {
                "title",
                "content",
                "encrypted"
        };

        String selection = "title = ?";
        String[] selectionArgs = { title };

        Cursor cursor = db.query(
                "notes",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List notes = new ArrayList<Note>();
        while(cursor.moveToNext()) {
            Note n = new Note();
            n.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            n.encrypted = cursor.getInt(cursor.getColumnIndexOrThrow("encrypted")) == 1;
            notes.add(n);
        }
        cursor.close();

        return notes;
    }

    public static List<Note> getAllNotes(Context c, boolean encrypted) {
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getReadableDatabase();


        String[] projection = {
                "title",
                "content",
                "encrypted"
        };

        String selection = "encrypted = ?";
        String encryptedString = encrypted + "";
        String[] selectionArgs = { encryptedString };



        Cursor cursor = db.query(
                "notes",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List notes = new ArrayList<Note>();
        while(cursor.moveToNext()) {
            Note n = new Note();
            n.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            n.encrypted = cursor.getInt(cursor.getColumnIndexOrThrow("encrypted")) == 1;
            notes.add(n);
        }
        cursor.close();

        return notes;
    }

    //no idea if this makes any sense lol
    public static void deleteNote(Context c, String title) {
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getReadableDatabase();

        //temporary false
        List notes = getAllNotes(c, false);

        Cursor cursor = notesDB.simpleTitleCursor(true, title);
        while(cursor.moveToNext()){
            db.delete("notes", "title" + " = " + title, null);
        }

    }

    /* Security related utilities */

    public static void savePassword(String pw, Activity a) {
        SharedPreferences sharedPreferences = a.getSharedPreferences("default", MODE_PRIVATE);
        sharedPreferences.edit().putString("p", pw).apply();
    }

    public static String loadPassword(String pw, Activity a) {
        SharedPreferences sharedPreferences = a.getSharedPreferences("default", MODE_PRIVATE);
        return sharedPreferences.getString("p", "");
    }

    public static SecretKeySpec getSecretKey(String pw) {
        String salt="random";
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec keySpec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 10000, 128);
            SecretKey keyTmp = keyFactory.generateSecret(keySpec);
            return new SecretKeySpec(keyTmp.getEncoded(), "AES");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String password, String text) {
        SecretKeySpec key = getSecretKey(password);
        if (null != key) {
            try {
                Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                pbeCipher.init(Cipher.ENCRYPT_MODE, key);
                AlgorithmParameters parameters = pbeCipher.getParameters();
                IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
                byte[] cryptoText = pbeCipher.doFinal(text.getBytes("UTF-8"));
                byte[] iv = ivParameterSpec.getIV();
                return new String(iv) + ":" + new String(cryptoText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String decrypt(String password, String encryptedText) {
        SecretKeySpec key = getSecretKey(password);
        String iv = encryptedText.split(":")[0];
        String property = encryptedText.split(":")[1];
        try {
            Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv.getBytes()));
            return new String(pbeCipher.doFinal(property.getBytes()), "UTF-8");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
