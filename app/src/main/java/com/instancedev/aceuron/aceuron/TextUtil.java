package com.instancedev.aceuron.aceuron;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.AlgorithmParameters;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    // TODO Will refactor this into two files: Database / Security

    /* Database related utilities */

    static class Note
    {
        public int id;
        public String title;
        public String content;
        public boolean encrypted;

        @Override
        public String toString(){
            short reviewLength = 13;
            String titleReview = title.equals("Title") ? "<No Title>" : title;
            String contentReview = (content.equals("Note")|| content.isEmpty()) ? "<No Content>" : ((content.length() >= reviewLength)? (content.substring(0,reviewLength)+"...") : content);

            StringBuilder sb = new StringBuilder();
            sb.append("Title:       " + titleReview);
            sb.append("\n");
            sb.append("Preview: " + contentReview);
            sb.append("\n");

          return sb.toString();
    };
    }

    static class ClassScheduleEntry
    {
        public int x;
        public int y;
        public String content;
    };

    static class CalendarEntry
    {
        public String date;
        public String content;
        boolean notify;
    };

    public static String SQL_CREATE_NOTES_TBL = "CREATE TABLE notes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "title TEXT, " +
            "content TEXT, " +
            "encrypted INTEGER)" ;

    public static String SQLITE_CREATE_CALENDAR_TBL = "CREATE TABLE calendar (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "date TEXT, " +
            "content TEXT, " +
            "notify INTEGER)";

    public static String SQLITE_CREATE_SCHEDULE_TBL = "CREATE TABLE schedule (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "x INTEGER, " +
            "y INTEGER, " +
            "content TEXT)";


    //Methods for notes
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

    public static void deleteNote(Context c, int id) {
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getWritableDatabase();

        String whereClause = "id == ?";
        String[] whereArgs = new String[] {
                Integer.toString(id)
        };

        db.delete("notes", whereClause, whereArgs);
    }

    public static Cursor getCursor(SQLiteOpenHelper helper, String table, String sel, String[] selArgs){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                table,
                null, // *
                sel,
                selArgs,
                null,
                null,
                "id"+" DESC"
        );

        return cursor;
    }

    // Returns whether or not a note with the specified title already exists in the database
    public static boolean containsTitle(SQLiteOpenHelper helper, String table, String[] selArgs){
        Cursor cursor = TextUtil.getCursor(helper, table, "title = ?", selArgs);
        return cursor.moveToNext();
    }

    public static List<Note> getNotesByTitle(Context c, String title) {
        NotesDBUtil notesDB = new NotesDBUtil(c);

        Cursor cursor = TextUtil.getCursor(notesDB, "notes", "title = ?", new String[] {title});

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





    public static void editNote(Context c, int id, String title, String content){
        NotesDBUtil notesDB = new NotesDBUtil(c);
        SQLiteDatabase db = notesDB.getReadableDatabase();

        // define the new value you want
        ContentValues newValues = new ContentValues();
        newValues.put("title", title);
        newValues.put("content", content);

        String whereClause = "id == ?";

        String[] whereArgs = new String[] {
                Integer.toString(id)
        };

        int amountOfUpdatedColumns = db.update("notes", newValues, whereClause, whereArgs);
    }





    public static List<Note> getNotesById(Context c, int id) {
        NotesDBUtil notesDB = new NotesDBUtil(c);

        Cursor cursor = TextUtil.getCursor(notesDB, "notes", "id = ?", new String[] {Integer.toString(id) });

        List notes = new ArrayList<Note>();
        while(cursor.moveToNext()) {
            Note n = new Note();
            n.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            n.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            n.encrypted = cursor.getInt(cursor.getColumnIndexOrThrow("encrypted")) == 1;
            notes.add(n);
        }
        cursor.close();

        return notes;
    }

    public static Note getFirstNoteByTitle(Context c, String title) {
        return getNotesByTitle(c, title).get(0);
    }

    public static List<Note> getAllNotes(Context c, boolean encrypted) {
        NotesDBUtil notesDB = new NotesDBUtil(c);

        Cursor cursor = TextUtil.getCursor(notesDB, "notes", "encrypted = ?", new String[] { encrypted ? "1" : "0"});

        List notes = new ArrayList<Note>();
        while(cursor.moveToNext()) {
            Note n = new Note();
            n.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            n.title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            n.encrypted = cursor.getInt(cursor.getColumnIndexOrThrow("encrypted")) == 1;
            notes.add(n);
        }
        cursor.close();

        return notes;
    }

    public static List<CalendarEntry> getCalendarByDateRange(Context c, Calendar startDate, int days) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("y-M-d");
        String[] selArgs = new String[days];
        int start_i = startDate.get(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < days; i++) {
            // TODO this is entirely untested
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, start_i + i);
            selArgs[i] = dateFormatter.format(cal.getTime());
        }

        CalendarDBUtil calendarDB = new CalendarDBUtil(c);

        Cursor cursor = TextUtil.getCursor(calendarDB, "calendar", "date = ?", selArgs);

        List entries = new ArrayList<CalendarEntry>();
        while(cursor.moveToNext()) {
            CalendarEntry n = new CalendarEntry();
            n.date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            n.notify = cursor.getInt(cursor.getColumnIndexOrThrow("notify")) == 1;
            entries.add(n);
        }
        cursor.close();

        return entries;
    }

    // Date should use the following formatter
    // SimpleDateFormat dateFormatter = new SimpleDateFormat("y-M-d");
    public static long insertCalendarEntry(Context c, String date, String content, boolean notify) {
        CalendarDBUtil notesDB = new CalendarDBUtil(c);
        SQLiteDatabase db = notesDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("content", content);
        values.put("notify", notify ? 1 : 0); // 1 = true, 0 = false
        long newRowId = db.insert("calendar", null, values);
        return newRowId;
    }

    public static List<ClassScheduleEntry> getClassSchedule(Context c) {
        ClassScheduleDBUtil scheduleDB = new ClassScheduleDBUtil(c);

        Cursor cursor = TextUtil.getCursor(scheduleDB, "schedule", null, null);

        List entries = new ArrayList<ClassScheduleEntry>();
        while(cursor.moveToNext()) {
            ClassScheduleEntry n = new ClassScheduleEntry();
            n.x = cursor.getInt(cursor.getColumnIndexOrThrow("x"));
            n.y = cursor.getInt(cursor.getColumnIndexOrThrow("y"));
            n.content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            entries.add(n);
        }
        cursor.close();

        return entries;
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
