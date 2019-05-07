package za.scouts.digital.scoutsdigitaloffline;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class DBHelperUsers extends SQLiteOpenHelper {

    private static final String urlLogin = "https://app.scouts.digital/login.php";
    public static final String DATABASE_NAME = "UserDB.db";

    public DBHelperUsers(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (firstName text, surname text, groupID integer, " +
                "userID integer primary key, groupName text, roleName text, email text, " +
                "cellPhone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void login(String email, String password) {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            String responseBody = Html.fromHtml(scanner.next()).toString();
            JSONObject objJSON = new JSONObject(responseBody);
            JSONObject objResponse = objJSON.getJSONObject("response");
            if (objResponse.getString("type").equals("PASS")) {
                JSONObject persData = objResponse.getJSONObject("personalData");
                System.out.println(persData);
            }
            else if (objResponse.getString("type").equals("FAIL")) {

            }
        } catch(Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
            System.out.println(e.toString());
        } finally {
            if (connection != null) {
        }
    }
    }
}
