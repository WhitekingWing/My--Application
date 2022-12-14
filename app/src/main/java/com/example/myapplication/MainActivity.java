package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.myapplication.data.DataSaver;
import com.example.myapplication.data.BookDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,DownFragment.ChangeListener {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookDetails> bookDetails;
    private ArrayList<BookDetails> bookCopy;
    private ArrayList<BookDetails> bookCopyFirst;
    private ArrayList<BookDetails> bookCopySecond;
    private ArrayList<BookDetails> bookCopyThird;
    private ArrayList<BookDetails> bookCopyFourth;
    private MainRecycleViewAdapter mainRecycleViewAdapter;



    private DrawerLayout mDraw;
    private NavigationView navView;//????????????
    String content = "Default BookShelf";
    String search;
    boolean click = true;
    DownFragment loadedFragment = null;
    int loadedCount = -1;
    MenuItem menuItem;
    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== AddChangeItemActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        //double price=bundle.getDouble("price");
                        int position=bundle.getInt("position");
                        String author = bundle.getString("author");
                        String translator = bundle.getString("translator");
                        String publisher = bundle.getString("publisher");
                        int pubYear = bundle.getInt("pubYear");
                        int pubMonth = bundle.getInt("pubMonth");
                        String ISBN = bundle.getString("ISBN");
                        String status = bundle.getString("status");
                        String bookShelf = bundle.getString("bookShelf");
                        String notes = bundle.getString("notes");
                        String law = bundle.getString("law");
                        String hyperlink = bundle.getString("hyperlink");
                        content = bundle.getString("content");
                        bookDetails.add(position, new BookDetails(title,R.drawable.ic_launcher_background,author,translator,publisher,pubYear,pubMonth,ISBN,status,bookShelf,notes,law,hyperlink,position) );
                        bookCopy.add(0, new BookDetails(title,R.drawable.ic_launcher_background,author,translator,publisher,pubYear,pubMonth,ISBN,status,bookShelf,notes,law,hyperlink,position) );
                        for(int i = 0;i < bookDetails.size();i++)
                        {
                            bookDetails.get(i).setPosition(i);
                        }
                        new DataSaver().Save(this,bookDetails);
                        if(bookShelf.equals(content) || content.equals("Default BookShelf")) {
                            for (int i = 0;i < bookCopy.size();i++)
                            {
                                bookCopy.get(i).setPosition(i);
                            }
                            mainRecycleViewAdapter.notifyItemInserted(0);
                        }
                    }
                }
            });
    private ActivityResultLauncher<Intent> updateDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== AddChangeItemActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        //double price=bundle.getDouble("price");
                        int position=bundle.getInt("position");
                        String author = bundle.getString("author");
                        String translator = bundle.getString("translator");
                        String publisher = bundle.getString("publisher");
                        int pubYear = bundle.getInt("pubYear");
                        int pubMonth = bundle.getInt("pubMonth");
                        String ISBN = bundle.getString("ISBN");
                        String status = bundle.getString("status");
                        String bookShelf = bundle.getString("bookShelf");
                        String notes = bundle.getString("notes");
                        String law = bundle.getString("law");
                        String hyperlink = bundle.getString("hyperlink");
                        content = bundle.getString("content");
                        bookDetails.get(position).setTitle(title);
                        bookDetails.get(position).setAuthor(author);
                        bookDetails.get(position).setTranslator(translator);
                        bookDetails.get(position).setPublisher(publisher);
                        bookDetails.get(position).setPubYear(pubYear);
                        bookDetails.get(position).setPubMonth(pubMonth);
                        bookDetails.get(position).setISBN(ISBN);
                        bookDetails.get(position).setStatus(status);
                        bookDetails.get(position).setBookShelf(bookShelf);
                        bookDetails.get(position).setNotes(notes);
                        bookDetails.get(position).setLaw(law);
                        bookDetails.get(position).setHyperlink(hyperlink);
                        //bookDetails.get(position).setPrice(price);
                        new DataSaver().Save(this,bookDetails);
                        //mainRecycleViewAdapter.notifyItemChanged(position);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.remove(loadedFragment);
                        loadedFragment = new DownFragment();
                        loadedCount = bundle.getInt("loadCount");
                        bundle = new Bundle();
                        bundle.putString("title",bookCopy.get(loadedCount).getTitle());
                        bundle.putString("author",bookCopy.get(loadedCount).getAuthor());
                        bundle.putInt("position",bookCopy.get(loadedCount).getPosition());
                        bundle.putString("translator",bookCopy.get(loadedCount).getTranslator());
                        bundle.putString("publisher",bookCopy.get(loadedCount).getPublisher());
                        bundle.putInt("pubYear",bookCopy.get(loadedCount).getPubYear());
                        bundle.putInt("pubMonth",bookCopy.get(loadedCount).getPubMonth());
                        bundle.putString("ISBN",bookCopy.get(loadedCount).getISBN());
                        bundle.putString("status",bookCopy.get(loadedCount).getStatus());
                        bundle.putString("bookShelf",bookCopy.get(loadedCount).getBookShelf());
                        bundle.putString("notes",bookCopy.get(loadedCount).getNotes());
                        bundle.putString("law",bookCopy.get(loadedCount).getLaw());
                        bundle.putString("hyperlink",bookCopy.get(loadedCount).getHyperlink());
                        bundle.putString("content",content);
                        bundle.putInt("loadCount",loadedCount);
                        loadedFragment.setArguments(bundle);
                        transaction.add(R.id.layout_container, loadedFragment);
                        transaction.show(loadedFragment);
                        transaction.commit();
                        if(!bookDetails.get(position).getBookShelf().equals(content))
                        {
                            mainRecycleViewAdapter.notifyItemRemoved(loadedCount);
                            bookCopy.remove(loadedCount);
                        }
                    }
                }
            });


    void updateData(Intent intent)
    {
        Bundle bundle=intent.getExtras();
        String title= bundle.getString("title");
        //double price=bundle.getDouble("price");
        int position=bundle.getInt("position");
        String author = bundle.getString("author");
        String translator = bundle.getString("translator");
        String publisher = bundle.getString("publisher");
        int pubYear = bundle.getInt("pubYear");
        int pubMonth = bundle.getInt("pubMonth");
        String ISBN = bundle.getString("ISBN");
        String status = bundle.getString("status");
        String bookShelf = bundle.getString("bookShelf");
        String notes = bundle.getString("notes");
        String law = bundle.getString("law");
        String hyperlink = bundle.getString("hyperlink");
        content = bundle.getString("content");
        bookDetails.get(position).setTitle(title);
        bookDetails.get(position).setAuthor(author);
        bookDetails.get(position).setTranslator(translator);
        bookDetails.get(position).setPublisher(publisher);
        bookDetails.get(position).setPubYear(pubYear);
        bookDetails.get(position).setPubMonth(pubMonth);
        bookDetails.get(position).setISBN(ISBN);
        bookDetails.get(position).setStatus(status);
        bookDetails.get(position).setBookShelf(bookShelf);
        bookDetails.get(position).setNotes(notes);
        bookDetails.get(position).setLaw(law);
        bookDetails.get(position).setHyperlink(hyperlink);
        //bookDetails.get(position).setPrice(price);
        new DataSaver().Save(this,bookDetails);
        //mainRecycleViewAdapter.notifyItemChanged(position);
        finish();
        Intent intent1 = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent1);
    }
    @Override
    public void setData(String text) {
        String []get = text.split(",");
        if(get[0].equals("delete"))
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(loadedFragment);
            transaction.commit();
            loadedFragment = null;
            loadedCount = -1;
            bookDetails.remove(Integer.parseInt(get[1]));
            bookCopy.remove(Integer.parseInt(get[2]));
            for(int i = 0;i < bookCopy.size();i++)
            {
                bookCopy.get(i).setPosition(i);
                bookDetails.get(i).setPosition(i);
            }
            new DataSaver().Save(MainActivity.this,bookDetails);
            mainRecycleViewAdapter.notifyItemRemoved(Integer.parseInt(get[2]));
        }
        else if(get[0].equals("update"))
        {
            Intent intentUpdate=new Intent(this, AddChangeItemActivity.class);
            intentUpdate.putExtra("position",Integer.parseInt(get[1]));
            intentUpdate.putExtra("title",bookDetails.get(Integer.parseInt(get[1])).getTitle());
            intentUpdate.putExtra("price",bookDetails.get(Integer.parseInt(get[1])).getPrice());
            intentUpdate.putExtra("author",bookDetails.get(Integer.parseInt(get[1])).getAuthor());
            intentUpdate.putExtra("translator",bookDetails.get(Integer.parseInt(get[1])).getTranslator());
            intentUpdate.putExtra("publisher",bookDetails.get(Integer.parseInt(get[1])).getPublisher());
            intentUpdate.putExtra("pubYear",bookDetails.get(Integer.parseInt(get[1])).getPubYear());
            intentUpdate.putExtra("pubMonth",bookDetails.get(Integer.parseInt(get[1])).getPubMonth());
            intentUpdate.putExtra("ISBN",bookDetails.get(Integer.parseInt(get[1])).getISBN());
            intentUpdate.putExtra("status",bookDetails.get(Integer.parseInt(get[1])).getStatus());
            intentUpdate.putExtra("bookShelf",bookDetails.get(Integer.parseInt(get[1])).getBookShelf());
            intentUpdate.putExtra("notes",bookDetails.get(Integer.parseInt(get[1])).getNotes());
            intentUpdate.putExtra("law",bookDetails.get(Integer.parseInt(get[1])).getLaw());
            intentUpdate.putExtra("hyperlink",bookDetails.get(Integer.parseInt(get[1])).getHyperlink());
            intentUpdate.putExtra("content",content);
            intentUpdate.putExtra("loadCount",Integer.parseInt(get[1]));
            intentUpdate.putExtra("mark",1);
            updateDataLauncher.launch(intentUpdate);
        }
        if(text.equals("hide"))
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(loadedFragment);
            transaction.commit();
            loadedFragment = null;
            loadedCount = -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.hide();
        }
        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);
        mDraw = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.imageButtonMenu).setOnClickListener(v -> {
            //??????????????????  ????????????
            mDraw.openDrawer(GravityCompat.START);
        });
        navView = findViewById(R.id.nav_view);
        navView.getMenu().findItem(R.id.item_label_first).setVisible(false);
        navView.getMenu().findItem(R.id.item_label_second).setVisible(false);
        navView.getMenu().findItem(R.id.item_label_third).setVisible(false);
        navView.getMenu().findItem(R.id.item_label_fourth).setVisible(false);
        navView.getMenu().findItem(R.id.item_label_fifth).setVisible(false);
        navView.getMenu().findItem(R.id.item_label_six).setVisible(false);
        navView.getMenu().findItem(R.id.item_label).setTitle("Label(?????????");
        //??????????????????
        navView.setNavigationItemSelectedListener(item -> {
            int count;
            switch (item.getItemId()) {
                case R.id.item_books:
                    //??????????????????
                    mDraw.closeDrawer(GravityCompat.START);
                    break;
                case R.id.item_search:
                    mDraw.closeDrawer(GravityCompat.START);
                    showDialog("????????????????????????");
                    break;
                case R.id.item_label:
                    if(!click) {
                        navView.getMenu().findItem(R.id.item_label_first).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label_second).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label_third).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label_fourth).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label_fifth).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label_six).setVisible(false);
                        navView.getMenu().findItem(R.id.item_label).setTitle("Label(?????????");
                    }
                    else
                    {
                        navView.getMenu().findItem(R.id.item_label_first).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label_second).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label_third).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label_fourth).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label_fifth).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label_six).setVisible(true);
                        navView.getMenu().findItem(R.id.item_label).setTitle("Label");
                    }
                    click = !click;
                    break;
                case R.id.item_label_first:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_first);
                    break;
                case R.id.item_label_second:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_second);
                    break;
                case R.id.item_label_third:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_third);
                    break;
                case R.id.item_label_fourth:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_fourth);
                    break;
                case R.id.item_label_fifth:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_fifth);
                    break;
                case R.id.item_label_six:
                    mDraw.closeDrawer(GravityCompat.START);
                    getLabel(R.string.law_sixth);
                    break;
                case R.id.item_setting:
                    Intent intent = new Intent(this,SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.item_about:
                    break;
                default:
                    break;
            }
            return true;
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        DataSaver dataSaver=new DataSaver();
        bookDetails=dataSaver.Load(this);
        bookCopy=dataSaver.Load(this);
        for (int i = 0;i < bookDetails.size();i++)
        {
            bookDetails.get(i).setPosition(i);
            bookCopy.get(i).setPosition(i);
        }
        FloatingActionButton addBotton = findViewById(R.id.floatingActionButton_add);
        addBotton.setOnClickListener(this);
        ImageButton imageButtonSearch = findViewById(R.id.imageButtonSearch);
        imageButtonSearch.setOnClickListener(this);
        if(content == null)
        {
            content = "Default BookShelf";
        }
        Spinner spinner = findViewById(R.id.toolbarSpinner);
        spinner.setOnItemSelectedListener(this);
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookCopy);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
    }
    public void getLabel(int resId)
    {
        int count;
        search = getString(resId);
        count = 0;
        for(int j = 0;j < bookDetails.size();j++)
        {
            if(bookCopy.size() > 0) {
                bookCopy.remove(count);
                mainRecycleViewAdapter.notifyItemRemoved(count);
            }
        }
        for(int j = 0;j < bookDetails.size();j++)
        {
            if(bookDetails.get(j).getBookShelf().equals(content) || content.equals("Default BookShelf") || content.equals("")) {
                if(search != null && (bookDetails.get(j).getTitle().contains(search) || bookDetails.get(j).getLaw().contains(search))) {
                    bookCopy.add(bookDetails.get(j));
                    mainRecycleViewAdapter.notifyItemInserted(count);
                    count++;
                }
                else if(search == null || search.equals(" ")){
                    bookCopy.add(bookDetails.get(j));
                    mainRecycleViewAdapter.notifyItemInserted(count);
                    count++;
                }
            }
        }
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.imageButtonSearch:
                showDialog("????????????????????????");
                break;
            case R.id.floatingActionButton_add:
                Intent intent = new Intent();
                intent.putExtra("position",0);
                intent.putExtra("content",content);
                intent.putExtra("mark",1);
                if(!content.equals("Default BookShelf")) {
                    intent.putExtra("bookShelf", content);
                }
                intent.setClass(MainActivity.this, AddChangeItemActivity.class);
                addDataLauncher.launch(intent);
                break;
        }
    }
    public void showDialog(String check)
    {
        final EditText input = new EditText(this);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_search).setView(input).setNegativeButton("??????",null);
        builder.setTitle("?????????");
        builder.setMessage("?????????????????????:" + check);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String temp = input.getText().toString();
                search = temp;
                int count = 0;
                for(int j = 0;j < bookDetails.size();j++)
                {
                    if(bookCopy.size() > 0) {
                        bookCopy.remove(count);
                        mainRecycleViewAdapter.notifyItemRemoved(count);
                    }
                }
                for(int j = 0;j < bookDetails.size();j++)
                {
                    if(bookDetails.get(j).getBookShelf().equals(content) || content.equals("Default BookShelf") || content.equals("")) {
                        if(search != null && (bookDetails.get(j).getTitle().contains(search) || bookDetails.get(j).getLaw().contains(search))) {
                            bookCopy.add(bookDetails.get(j));
                            mainRecycleViewAdapter.notifyItemInserted(count);
                            count++;
                        }
                        else if(search == null || search.equals(" ")){
                            bookCopy.add(bookDetails.get(j));
                            mainRecycleViewAdapter.notifyItemInserted(count);
                            count++;
                        }
                    }
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_UPDATE:
                Intent intentUpdate=new Intent(this, AddChangeItemActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",bookDetails.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("price",bookDetails.get(item.getOrder()).getPrice());
                intentUpdate.putExtra("author",bookDetails.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("translator",bookDetails.get(item.getOrder()).getTranslator());
                intentUpdate.putExtra("publisher",bookDetails.get(item.getOrder()).getPublisher());
                intentUpdate.putExtra("pubYear",bookDetails.get(item.getOrder()).getPubYear());
                intentUpdate.putExtra("pubMonth",bookDetails.get(item.getOrder()).getPubMonth());
                intentUpdate.putExtra("ISBN",bookDetails.get(item.getOrder()).getISBN());
                intentUpdate.putExtra("status",bookDetails.get(item.getOrder()).getStatus());
                intentUpdate.putExtra("bookShelf",bookDetails.get(item.getOrder()).getBookShelf());
                intentUpdate.putExtra("notes",bookDetails.get(item.getOrder()).getNotes());
                intentUpdate.putExtra("law",bookDetails.get(item.getOrder()).getLaw());
                intentUpdate.putExtra("hyperlink",bookDetails.get(item.getOrder()).getHyperlink());
                intentUpdate.putExtra("content",content);
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookDetails.remove(bookCopy.get(item.getOrder()).getPosition());
                                new DataSaver().Save(MainActivity.this,bookDetails);
                                finish();
                                Intent intent1 = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent1);
                                //mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        content = adapterView.getItemAtPosition(i).toString();
        if(!content.equals("")) {
            int count = 0;
            for (int j = 0; j < bookDetails.size(); j++) {
                if (bookCopy.size() > 0) {
                    bookCopy.remove(count);
                    mainRecycleViewAdapter.notifyItemRemoved(count);
                }
            }
            for (int j = 0; j < bookDetails.size(); j++) {
                if (bookDetails.get(j).getBookShelf().equals(content) || content.equals("Default BookShelf")) {
                    if (search != null && (bookDetails.get(j).getTitle().contains(search) || bookDetails.get(j).getLaw().contains(search))) {
                        bookCopy.add(bookDetails.get(j));
                        mainRecycleViewAdapter.notifyItemInserted(count);
                        count++;
                    } else if (search == null) {
                        bookCopy.add(bookDetails.get(j));
                        mainRecycleViewAdapter.notifyItemInserted(count);
                        count++;
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        private ArrayList<BookDetails> localDataSet;
        //private ImageView imageView;
        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewTitle;
            private final ImageView imageViewImage;
            private final TextView textViewAuthor;
            private final TextView textViewPublisher;
            private final TextView textViewPubYear;
            private final TextView textViewPubMonth;
            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textViewTitle = view.findViewById(R.id.textview_item_caption);
                textViewAuthor = view.findViewById(R.id.textview_item_author);
                textViewPublisher= view.findViewById(R.id.textview_item_publisher);
                textViewPubYear= view.findViewById(R.id.textview_item_pubYear);
                textViewPubMonth= view.findViewById(R.id.textview_item_pubMonth);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        loadedFragment = new DownFragment();
                        Bundle bundle = new Bundle();
                        loadedCount = getAdapterPosition();
                        bundle.putString("title",localDataSet.get(loadedCount).getTitle());
                        bundle.putString("author",localDataSet.get(loadedCount).getAuthor());
                        bundle.putInt("position",localDataSet.get(loadedCount).getPosition());
                        bundle.putString("translator",localDataSet.get(loadedCount).getTranslator());
                        bundle.putString("publisher",localDataSet.get(loadedCount).getPublisher());
                        bundle.putInt("pubYear",localDataSet.get(loadedCount).getPubYear());
                        bundle.putInt("pubMonth",localDataSet.get(loadedCount).getPubMonth());
                        bundle.putString("ISBN",localDataSet.get(loadedCount).getISBN());
                        bundle.putString("status",localDataSet.get(loadedCount).getStatus());
                        bundle.putString("bookShelf",localDataSet.get(loadedCount).getBookShelf());
                        bundle.putString("notes",localDataSet.get(loadedCount).getNotes());
                        bundle.putString("law",localDataSet.get(loadedCount).getLaw());
                        bundle.putString("hyperlink",localDataSet.get(loadedCount).getHyperlink());
                        bundle.putString("content",content);
                        bundle.putInt("loadCount",loadedCount);
                        loadedFragment.setArguments(bundle);
                        transaction.add(R.id.layout_container, loadedFragment);
                        transaction.show(loadedFragment);
                        transaction.commit();
                    }
                });
                view.setOnCreateContextMenuListener(this);
            }
            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            public TextView getTextViewAuthor() {
                return textViewAuthor;
            }

            public TextView getTextViewPublisher() {
                return textViewPublisher;
            }

            public TextView getTextViewPubMonth() {
                return textViewPubMonth;
            }

            public TextView getTextViewPubYear() {
                return textViewPubYear;
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete "+getAdapterPosition());
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public MainRecycleViewAdapter(ArrayList<BookDetails> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main, viewGroup, false);

            return new ViewHolder(view);
        }

//        private void changeFragment(androidx.fragment.app.Fragment fragment) {
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = manager.beginTransaction();
//            fragmentTransaction.replace(R.id.rl_fragment_container, fragment);
//            fragmentTransaction.commit();
//            fragmentTransaction.show(fragment);
//        }
//        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            localDataSet = bookCopy;
            if(!localDataSet.isEmpty()) {
//                if (localDataSet.get(position).getBookShelf().equals(content) || content.equals("Default BookShelf")) {
                    viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
                    //viewHolder.getTextViewPrice().setText(localDataSet.get(position).getPrice().toString());
                        //viewHolder.getImageViewImage().setImageResource(R.drawable.home);
                    URL url = null;
                    try {
                        //url = new URL(localDataSet.get(position).getHyperlink());
                        url = new URL(localDataSet.get(position).getHyperlink());
                        requestImg(url,viewHolder.getImageViewImage());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor() + " ???");
                    viewHolder.getTextViewPublisher().setText(localDataSet.get(position).getPublisher());
                    viewHolder.getTextViewPubYear().setText(Integer.toString(localDataSet.get(position).getPubYear()));
                    viewHolder.getTextViewPubMonth().setText(Integer.toString(localDataSet.get(position).getPubMonth()));
//                }

            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        private void requestImg(final URL imgUrl,ImageView imageView)
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
}
