package com.example.myapplication;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.data.BookDetails;
import com.example.myapplication.data.HttpDataLoader;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("deprecation")
public class AddChangeItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    private String status;
    private String bookShelf;
    private String law;
    private String ISBN;
    EditText editTextTitle;
    EditText editTextAuthor;
    EditText editTextTranslator;
    EditText editTextPublisher;
    EditText editTextPubYear;
    EditText editTextPubMonth;
    EditText editTextISBN;
    EditText editTextNotes;
    EditText editTextHyperlink;
    BookDetails temp;
    Button button_isbn;
    int loadCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_change_item);
        button_isbn = findViewById(R.id.button_isbn);
        button_isbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddChangeItemActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 2022);
            }
        });
        editTextTitle=findViewById(R.id.edittext_book_item_title);
        editTextAuthor=findViewById(R.id.edittext_book_item_author);
        editTextTranslator=findViewById(R.id.edittext_book_item_translator);
        editTextPublisher=findViewById(R.id.edittext_book_item_publisher);
        editTextPubYear = findViewById(R.id.edittext_book_item_pubYear);
        editTextPubMonth = findViewById(R.id.edittext_book_item_pubMonth);
        editTextISBN=findViewById(R.id.edittext_book_item_isbn);
        editTextNotes = findViewById(R.id.edittext_book_item_note);
        editTextHyperlink = findViewById(R.id.edittext_book_item_hyperlink);
        position= this.getIntent().getIntExtra("position",0);
        loadCount = this.getIntent().getIntExtra("loadCount",0);
        String title=this.getIntent().getStringExtra("title");
        String author = this.getIntent().getStringExtra("author");
        String translator = this.getIntent().getStringExtra("translator");
        String publisher = this.getIntent().getStringExtra("publisher");
        int pubYear = this.getIntent().getIntExtra("pubYear",0);
        int pubMonth = this.getIntent().getIntExtra("pubMonth",0);
        ISBN = this.getIntent().getStringExtra("ISBN");
        status = this.getIntent().getStringExtra("status");
        bookShelf = this.getIntent().getStringExtra("bookShelf");
        String notes = this.getIntent().getStringExtra("notes");
        String hyperlink = this.getIntent().getStringExtra("hyperlink");
        String content = this.getIntent().getStringExtra("content");
        int mark = this.getIntent().getIntExtra("mark",-1);
        Spinner spinner_one = findViewById(R.id.Spinner_one);
        Spinner spinner_two = findViewById(R.id.Spinner_two);
        Spinner spinner_three = findViewById(R.id.Spinner_three);
        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String content = adapterView.getItemAtPosition(i).toString();
                if(!content.equals("")) {
                    status = content;
                }
                if(status == null)
                {
                    status = "Idle";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String content = adapterView.getItemAtPosition(i).toString();
                if(!content.equals("")) {
                    bookShelf = content;
                }
                if(bookShelf == null)
                {
                    bookShelf = "The First BookShelf";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_three.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String content = adapterView.getItemAtPosition(i).toString();
                if(!content.equals("")) {
                    law = content;
                }
                if(law == null)
                {
                    law = "Law";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(mark == 0)
        {
            button_isbn.setVisibility(View.INVISIBLE);
        }

        if(null!=title)
        {
            editTextTitle.setText(title);
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
            editTextHyperlink.setText(hyperlink);
            ImageView imageView = findViewById(R.id.imageView);
            URL url = null;
            try {
                //url = new URL(localDataSet.get(position).getHyperlink());
                url = new URL(hyperlink);
                requestImg(url,imageView);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

//这里在Activity的onCreat()方法里写
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);  //添加返回的图标
        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putInt("position",position);
                bundle.putString("translator",editTextTranslator.getText().toString());
                bundle.putString("publisher",editTextPublisher.getText().toString());
                int pubYear = Integer.parseInt(editTextPubYear.getText().toString());
                bundle.putInt("pubYear",pubYear);
                bundle.putInt("loadCount",loadCount);
                int pubMonth = Integer.parseInt(editTextPubMonth.getText().toString());
                bundle.putInt("pubMonth",pubMonth);
                String ISBN = editTextISBN.getText().toString();
                bundle.putString("ISBN",ISBN);
                bundle.putString("status",status);
                bundle.putString("bookShelf",bookShelf);
                bundle.putString("notes",editTextNotes.getText().toString());
                bundle.putString("law",law);
                bundle.putString("hyperlink",editTextHyperlink.getText().toString());
                bundle.putString("content",content);
                bundle.putInt("resultCode",AddChangeItemActivity.RESULT_CODE_SUCCESS);
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2022) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(resultCode, intent);
                final String qrContent = scanResult.getContents();
                //Toast.makeText(this, qrContent, Toast.LENGTH_SHORT).show();
                ISBN = qrContent;
                EditText editTextISBN = findViewById(R.id.edittext_book_item_isbn);
                editTextISBN.setText(ISBN);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpDataLoader httpDataLoader = new HttpDataLoader();
                        StringBuffer resultBuffer = new StringBuffer();
                        String path = "https://api.jike.xyz/situ/book/isbn/" + ISBN + "?apikey=14493.5bc9462d2f6a0e8a941a00ec33990c24.b3ed28de21963bacd411125142c46c81";
                        try {
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(100000000);
                            conn.setReadTimeout(500000000);
                            conn.setRequestMethod("GET");
                            conn.setUseCaches(false);
                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                                BufferedReader reader = new BufferedReader(inputStreamReader);
                                String tempLine = null;
                                resultBuffer = new StringBuffer();
                                while ((tempLine = reader.readLine()) != null) {
                                    resultBuffer.append(tempLine);
                                    resultBuffer.append("\n");
                                }
                                //return resultBuffer.toString();
                                Log.i("text", resultBuffer.toString());
                            }
                        } catch (Exception exception) {
                            Log.e("text", exception.getMessage());
                        }
                        temp = httpDataLoader.ParseJsonData(resultBuffer.toString());
                        if(!temp.getTitle().equals(" ")) {
                              ImageView imageView = findViewById(R.id.imageView);
                              imageView.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      URL url = null;
                                      try {
                                          //url = new URL(localDataSet.get(position).getHyperlink());
                                          url = new URL(temp.getHyperlink());
                                          requestImg(url,imageView);
                                      } catch (MalformedURLException e) {
                                          e.printStackTrace();
                                      }
                                  }
                              });
                              editTextTitle.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      editTextTitle.setText(temp.getTitle());
                                  }
                              });
                              editTextAuthor.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      editTextAuthor.setText(temp.getAuthor());
                                  }
                              });
                              editTextPublisher.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      editTextPublisher.setText(temp.getPublisher());
                                  }
                              });
                                editTextPubYear.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        editTextPubYear.setText(Integer.toString(temp.getPubYear()));
                                    }
                                });
                                editTextPubMonth.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int pubMonth = temp.getPubMonth();
                                        if (pubMonth < 10) {
                                            editTextPubMonth.setText('0' + Integer.toString(pubMonth));
                                        } else {
                                            editTextPubMonth.setText(Integer.toString(pubMonth));
                                        }
                                    }
                                });
                                editTextTranslator.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        editTextTranslator.setText(temp.getTranslator());
                                    }
                                });
                                editTextHyperlink.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        editTextHyperlink.setText(temp.getHyperlink());
                                    }
                                });
                                editTextNotes.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        editTextNotes.setText(temp.getNotes());
                                    }
                                });
                                button_isbn.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(AddChangeItemActivity.this, "没有这本书!/没有开启网络！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }

                }).start();
            }
            super.onActivityResult(requestCode, resultCode, intent);
        }
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
    private void requestImg(final URL imgUrl, ImageView imageView)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(imgUrl.openStream());

                    showImg(bitmap,imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showImg(final Bitmap bitmap,ImageView imageView){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}