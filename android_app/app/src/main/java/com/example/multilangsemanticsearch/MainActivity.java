package com.example.multilangsemanticsearch;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import tutorial.Phrase;
import tutorial.SemanticSearchGrpc;


public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    RadioGroup radioButton;
    String lang = "en";
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        radioButton = findViewById(R.id.toggle);
        radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.en_lang:
                        lang = "en";
                        break;
                    case R.id.ru_lang:
                        lang = "ru";
                        break;
                }
            }
        });
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem
                = menu.findItem(R.id.search_bar);
        SearchView searchView
                = (SearchView) MenuItemCompat
                .getActionView(searchViewItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        new GrpcTask(MainActivity.this)
                                .execute(
                                        query,
                                        lang
                                        );
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    private List<Massage> phrase;
    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        phrase = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex("id");
            int messageIndex = c.getColumnIndex("message");
            int langIndex = c.getColumnIndex("lang");
            int messageResIndex = c.getColumnIndex("message_respones");
            int langResIndex = c.getColumnIndex("lang_respones");

            do {
                Log.d("ID", "ID : " + c.getInt(idIndex));
                phrase.add(new Massage(c.getString(messageIndex), c.getString(langIndex),
                        c.getString(messageResIndex), c.getString(langResIndex), c.getInt(idIndex)));
                Log.d("Message", "mes : " + c.getString(messageIndex) );
            } while (c.moveToNext());
        }
        RVAdapter adapter = new RVAdapter(phrase, this);
        rv.setAdapter(adapter);
    }

    private class GrpcTask extends AsyncTask<String, Void, ArrayList<Phrase>> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private GrpcTask(Activity activity) {
            this.activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        protected ArrayList<Phrase> doInBackground(String... params) {
            String host = "192.168.1.3";
            String portStr = "6066";
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
            try {
                Log.w("Port", Integer.toString(port));
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

                SemanticSearchGrpc.SemanticSearchBlockingStub stub = SemanticSearchGrpc.newBlockingStub(channel);
                ArrayList<Phrase> phrases = new ArrayList<>();
                Phrase request = Phrase.newBuilder().setLang(params[1]).setText(params[0]).build();
                Phrase reply = stub.getSemanticSearchResult(request);
                phrases.add(request);
                phrases.add(reply);
                return phrases;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                ArrayList<Phrase> error = new ArrayList<>();
                error.add(Phrase.newBuilder().setLang("error").setText("Failled").build());
                return error;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Phrase> phrases) {
            if (phrases.get(0).getLang() != "error") {
                ContentValues cv = new ContentValues();
                cv.put("message", phrases.get(0).getText());
                cv.put("lang", phrases.get(0).getLang());
                cv.put("message_respones", phrases.get(1).getText());
                cv.put("lang_respones", phrases.get(1).getLang());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert("mytable", null, cv);
                initializeData();
            }
        }
    }
}