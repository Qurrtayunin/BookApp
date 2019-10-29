package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editSearch;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> itemValues;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editSearch=findViewById(R.id.edit_query);
        buttonSearch=findViewById(R.id.button_search);
        recyclerView=findViewById(R.id.recycler_view);
        itemValues= new ArrayList<>();
        itemAdapter = new ItemAdapter(this, itemValues);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });
    }

    public void searchBooks(){
        //pengecekan status
        String queryString=editSearch.getText().toString();

        //mengecek apakah terhubung dengan koneksi internet
        ConnectivityManager connMgr=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected() && queryString.length()!=0){
            //mengambil data dari internet menggunakan asycn...
            new FetchBook(this,itemValues, itemAdapter,recyclerView).execute(queryString);
        } else {
            Toast.makeText(this, "Please check your connection!", Toast.LENGTH_SHORT).show();
        }

    }
}
