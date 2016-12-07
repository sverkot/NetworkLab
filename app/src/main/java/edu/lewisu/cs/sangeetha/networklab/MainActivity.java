package edu.lewisu.cs.sangeetha.networklab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.output);

    }

    public void goButtonClick(View v) {

        String urlString = "http://cs.lewisu.edu/~howardcy/php/books1.php";
        DownloadData downloadData = new DownloadData();
        downloadData.execute(urlString);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String jsonData = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line ="";
                line = bufferedReader.readLine();
                while (line != null) {
                    jsonData += line;
                    line = bufferedReader.readLine();

                }
                urlConnection.disconnect();
                String title;
                String isbn;
                String results = "";
                JSONArray jsonArray = new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    title = jsonObject.getString("title");
                    isbn = jsonObject.getString("isbn");
                    results += isbn + "\t" + title + "\n";

                }
                return results;

            } catch (Exception e) {
                Log.i("Error", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            output.setText(s);
        }
    }
}
