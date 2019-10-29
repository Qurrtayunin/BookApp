package com.example.bookapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//<input,proses,ouput>
public class FetchBook extends AsyncTask<String,Void,String> {
    private ArrayList<ItemData> itemValues;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    Context context;

    public FetchBook(Context context, ArrayList<ItemData> itemValues, ItemAdapter itemAdapter, RecyclerView recyclerView){
        this.itemValues=itemValues;
        this.itemAdapter=itemAdapter;
        this.context=context;
        this.recyclerView=recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryString = strings[0];
        //set up variables for the try block that needs to be closed
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
        final String QUERY_PARAM = "q";
        Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, queryString).build();
        try {
            URL requestURL = new URL(builtURI.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            //menginisialisasi koneksi ke servernya
            urlConnection.connect();
            //pengambilan datanya menggunakan input stream
            InputStream inputStream=urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            reader =new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            //untuk mendapatkan kembalian bila panjang lebih dari 0
            if(builder.length()==0){
                return null;
            }
            bookJSONString=builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookJSONString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        itemValues=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray itemsArray=jsonObject.getJSONArray("items");
            int i=0;
            String title = null;
            String authors = null;
            String desc=null;
            String image=null;

            while (i<itemsArray.length()){
                JSONObject book=itemsArray.getJSONObject(i);
                JSONObject volumeInfo=book.getJSONObject("volumeInfo");

                try{
                    title=volumeInfo.getString("title");
                    if(volumeInfo.has("author")){   //pengecekan kembaliannya ada gak
                        authors = volumeInfo.getString("author");
                    } else {
                        authors="";
                    }

                    if(volumeInfo.has("description")){   //pengecekan kembaliannya ada gak
                        desc = volumeInfo.getString("description");
                    } else {
                        desc="";
                    }

                    if(volumeInfo.has("imageLinks")){   //pengecekan kembaliannya ada gak
                        image = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                    } else {
                        image="";
                    }

                    ItemData itemData = new ItemData();
                    itemData.title = title;
                    itemData.subtitle = authors;
                    itemData.itemDescription = desc;
                    itemData.itemImage = image;
                    Log.d("CLOG","Title"+title);
                    itemValues.add(itemData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.itemAdapter=new ItemAdapter(context, itemValues);
        this.recyclerView.setAdapter(this.itemAdapter);
//        itemAdapter.notifyDataSetChanged();
    }
}
