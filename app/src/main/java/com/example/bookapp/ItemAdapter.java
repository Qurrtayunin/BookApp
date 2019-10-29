package com.example.bookapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ItemData> values;

    public ItemAdapter(Context context, ArrayList<ItemData> values) {
        this.context=context;
        this.values= values;
        this.inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ItemData data=values.get(i);
        viewHolder.tvtitle.setText(data.title);
        viewHolder.tvsubtitle.setText (data.subtitle);
        viewHolder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailActivity.class);      //mengirimkan data ke detail activity
                        intent.putExtra("DATA", data);
                        context.startActivity(intent);
                    }
                }
        );

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Hapus Kontak")
                        .setMessage("Apa anda yakin menghapus " + data.title + "?")
                        .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                values.remove(data);
                                notifyDataSetChanged();
                            }
                        })

                        .setNegativeButton("tidak", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtitle;
        TextView tvsubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle= itemView.findViewById(R.id.tvtitle);
            tvsubtitle=itemView.findViewById(R.id.tvsubtitle);
        }
    }
}
