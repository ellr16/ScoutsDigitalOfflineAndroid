package za.scouts.digital.scoutsdigitaloffline;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.*;
import android.database.sqlite.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import android.text.Html;

public class MainActivity extends AppCompatActivity {

    private String email, password;
    private EditText emailField, passwordField;
    private Button loginButton;

    private static final String urlSD = "https://app.scouts.digital";
    private static final String urlLogin = "https://app.scouts.digital/login.php";
    private static final String urlInitialDownload = "https://app.scouts.digital/initialDownload.php";
    private static final String charset = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = findViewById(R.id.loginInputEmail);
        passwordField = findViewById(R.id.loginInputPassword);
        loginButton = findViewById(R.id.loginButton);
        email = "";
        password = "";

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();
                if(email.isEmpty() || password.isEmpty()) {
                    System.out.println("Incomplete");
                    Toast.makeText(getApplicationContext(), "Incomplete", Toast.LENGTH_LONG).show();
                }
                else {
                    email = "ellisrory63@gmail.com";
                    password = "0.18Patrick";
                    String query = "username=" + email + "&password=" + password;
                    String url = urlLogin + "?" + query;
                    String[] urlArr = new String[1];
                    urlArr[0] = url;
                    new AsyncSDLogin().execute(urlArr);
                }
            }
        });
    }

    public static class AsyncSDLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experience
            /*ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            HttpsURLConnection connection = null;
            String current = "";
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
                // return the data to onPostExecute method
                return current;
            } catch(Exception e) {
                System.out.println("Exception");
                e.printStackTrace();
                System.out.println(e.toString());
            } finally {
                if (connection != null) {
                }
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            //System.out.println(s);
            //Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            //progressDialog.dismiss();
            /*try {
                // JSON Parsing of data
                JSONArray jsonArray = new JSONArray(s);
                JSONObject oneObject = jsonArray.getJSONObject(0);
                System.out.println(jsonArray.toString());
                System.out.println(oneObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }*/


        }
    }
}
