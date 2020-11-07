package com.example.multilangsemanticsearch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

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

    // List View object

    // Define array adapter for ListView

    // Define array List for List View data

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        // initialise ListView with id
        initializeData();
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        new GrpcTask(MainActivity.this)
                .execute(
                        "192.168.1.3",
                        "6066"
                );
        // Set adapter to ListView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem
                = menu.findItem(R.id.search_bar);
        SearchView searchView
                = (SearchView) MenuItemCompat
                .getActionView(searchViewItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    // Override onQueryTextSubmit method
                    // which is call
                    // when submitquery is searched

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // If the list contains the search query
                        // than filter the adapter
                        // using the filter method
                        // with the query as its argument
                        new GrpcTask(MainActivity.this)
                                .execute(
                                        "192.168.1.3",
                                        "6066"
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

    private List<Person> persons;
    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));
    }

    private class GrpcTask extends AsyncTask<String, Void, String> {
        private final WeakReference<Activity> activityReference;
        private ManagedChannel channel;
        private GrpcTask(Activity activity) {
            this.activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.w("MYWORNING", "DO IN BACK");
            String host = params[0];
//            String message = params[1];
            String portStr = params[1];
            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
            try {
                Log.w("Port", Integer.toString(port));
                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
                Log.w("reply", "TESTING1");
                SemanticSearchGrpc.SemanticSearchBlockingStub stub = SemanticSearchGrpc.newBlockingStub(channel);
                Log.w("reply", "TESTING2");
                Phrase request = Phrase.newBuilder().setLang("en").setText("Test").build();
                Log.w("reply", "TESTING3");
                Phrase reply = stub.getSemanticSearchResult(request);
                Log.w("reply", reply.getText());

                return reply.getText();
            } catch (Exception e) {
                Log.w("reply", "TESTING-");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                return String.format("Failed... : %n%s", sw);
            }
        }
    }
}