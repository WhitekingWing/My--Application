package com.example.myapplication.data;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AddChangeItemActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DownFragment extends androidx.fragment.app.Fragment {
    String readingStatus;
    String bookShelf;
    TextView textViewBookshelf;
    TextView textViewStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.down_fragment, null);
            Bundle bundle = getArguments();
            readingStatus = bundle.getString("status");
            bookShelf = bundle.getString("bookShelf");
            textViewStatus = (TextView) view.findViewById(R.id.textViewReadingStatus);
            textViewBookshelf = (TextView) view.findViewById(R.id.textViewBookShelf);
            textViewStatus.setText(readingStatus);
            textViewBookshelf.setText(bookShelf);
            FloatingActionButton editButton =  (FloatingActionButton) view.findViewById(R.id.floatingActionEditButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentUpdate=new Intent(getActivity(), AddChangeItemActivity.class);
                    intentUpdate.putExtra("position",bundle.getInt("position"));
                    intentUpdate.putExtra("title",bundle.getString("title"));
                    intentUpdate.putExtra("author",bundle.getString("author"));
                    intentUpdate.putExtra("translator",bundle.getString("translator"));
                    intentUpdate.putExtra("publisher",bundle.getString("publisher"));
                    intentUpdate.putExtra("pubYear",bundle.getInt("pubYear"));
                    intentUpdate.putExtra("pubMonth",bundle.getInt("pubMonth"));
                    intentUpdate.putExtra("ISBN",bundle.getString("ISBN"));
                    intentUpdate.putExtra("status",readingStatus);
                    intentUpdate.putExtra("bookShelf",bookShelf);
                    intentUpdate.putExtra("notes",bundle.getString("notes"));
                    intentUpdate.putExtra("law",bundle.getString("law"));
                    intentUpdate.putExtra("hyperlink",bundle.getString("hyperlink"));
                    intentUpdate.putExtra("content",bundle.getString("content"));
                    intentUpdate.putExtra("mark",0);
                    startActivity(intentUpdate);
                }
            });
            return view;
    }
}
