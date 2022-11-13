package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddChangeItemActivity extends AppCompatActivity{

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    private String status;
    private String bookShelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_item);
        position= this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        Double price=this.getIntent().getDoubleExtra("price",0);
        String author = this.getIntent().getStringExtra("author");
        String translator = this.getIntent().getStringExtra("translator");
        String publisher = this.getIntent().getStringExtra("publisher");
        int pubYear = this.getIntent().getIntExtra("pubYear",0);
        int pubMonth = this.getIntent().getIntExtra("pubMonth",0);
        String ISBN = this.getIntent().getStringExtra("ISBN");
        status = this.getIntent().getStringExtra("status");
        bookShelf = this.getIntent().getStringExtra("bookShelf");
        String notes = this.getIntent().getStringExtra("notes");
        String law = this.getIntent().getStringExtra("law");
        String hyperlink = this.getIntent().getStringExtra("hyperlink");
        Spinner spinner_one = findViewById(R.id.Spinner_one);
        Spinner spinner_two = findViewById(R.id.Spinner_two);
        EditText editTextTitle=findViewById(R.id.edittext_book_item_title);
        EditText editTextPrice=findViewById(R.id.edittext_book_item_price);
        EditText editTextAuthor=findViewById(R.id.edittext_book_item_author);
        EditText editTextTranslator=findViewById(R.id.edittext_book_item_translator);
        EditText editTextPublisher=findViewById(R.id.edittext_book_item_publisher);
        EditText editTextPubYear = findViewById(R.id.edittext_book_item_pubYear);
        EditText editTextPubMonth = findViewById(R.id.edittext_book_item_pubMonth);
        EditText editTextISBN=findViewById(R.id.edittext_book_item_isbn);
        EditText editTextNotes = findViewById(R.id.edittext_book_item_note);
        EditText editTextLaw = findViewById(R.id.edittext_book_item_label);
        EditText editTextHyperlink = findViewById(R.id.edittext_book_item_hyperlink);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String content = adapterView.getItemAtPosition(i).toString();
                status = content;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String content = adapterView.getItemAtPosition(i).toString();
                bookShelf = content;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(null!=title)
        {
            editTextTitle.setText(title);
            editTextPrice.setText(price.toString());
            editTextAuthor.setText(author);
            editTextTranslator.setText(translator);
            editTextPublisher.setText(publisher);
            editTextPubYear.setText(Integer.toString(pubYear));
            if(pubMonth < 10){
                editTextPubMonth.setText('0'+Integer.toString(pubMonth));
            }
            else {
                editTextPubMonth.setText(Integer.toString(pubMonth));
            }
            editTextISBN.setText(ISBN);
            editTextNotes.setText(notes);
            editTextLaw.setText(law);
            editTextHyperlink.setText(hyperlink);
        }

//这里在Activity的onCreat()方法里写
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);  //添加返回的图标

        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                //double price=Double.parseDouble( editTextPrice.getText().toString());
                //bundle.putDouble("price",price);
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putInt("position",position);
                bundle.putString("translator",editTextTranslator.getText().toString());
                bundle.putString("publisher",editTextPublisher.getText().toString());
                int pubYear = Integer.parseInt(editTextPubYear.getText().toString());
                bundle.putInt("pubYear",pubYear);
                int pubMonth = Integer.parseInt(editTextPubMonth.getText().toString());
                bundle.putInt("pubMonth",pubMonth);
                String ISBN = editTextISBN.getText().toString();
                bundle.putString("ISBN",ISBN);
                bundle.putString("status",status);
                bundle.putString("bookShelf",bookShelf);
                bundle.putString("notes",editTextNotes.getText().toString());
                bundle.putString("law",editTextLaw.getText().toString());
                bundle.putString("hyperlink",editTextHyperlink.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                AddChangeItemActivity.this.finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}