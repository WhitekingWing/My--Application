package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.myapplication.data.DataSaver;
import com.example.myapplication.data.BookDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookDetails> bookDetails;
    private MainRecycleViewAdapter mainRecycleViewAdapter;


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
                       bookDetails.add(position, new BookDetails(title,R.drawable.ic_launcher_background,author,translator,publisher,pubYear,pubMonth,ISBN,status,bookShelf,notes,law,hyperlink) );
                        new DataSaver().Save(this,bookDetails);
                        mainRecycleViewAdapter.notifyItemInserted(position);
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
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver=new DataSaver();
        bookDetails=dataSaver.Load(this);

        /*if(bookDetails.size()==0) {
            bookDetails.add(new BookDetails("item 0", R.drawable.folder));
        }*/
        FloatingActionButton addBotton = findViewById(R.id.floatingActionButton_add);
        addBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("position",0);
                intent.setClass(MainActivity.this, AddChangeItemActivity.class);
                addDataLauncher.launch(intent);
            }
        });
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookDetails);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Intent intent=new Intent(this, AddChangeItemActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);
                break;
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
                                bookDetails.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this,bookDetails);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
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
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<BookDetails> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete "+getAdapterPosition());
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

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            //viewHolder.getTextViewPrice().setText(localDataSet.get(position).getPrice().toString());
            if(localDataSet.get(position).getResourceId() == R.drawable.folder) {
                viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
            }
            else
            {
                viewHolder.getImageViewImage().setImageResource(R.drawable.ic_launcher_background);
            }
            viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor()+" 著");
            viewHolder.getTextViewPublisher().setText(" , "+localDataSet.get(position).getPublisher());
            viewHolder.getTextViewPubYear().setText(Integer.toString(localDataSet.get(position).getPubYear()));
            viewHolder.getTextViewPubMonth().setText(Integer.toString(localDataSet.get(position).getPubMonth()));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}
