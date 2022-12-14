package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.AddChangeItemActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DownFragment extends androidx.fragment.app.Fragment {
    String readingStatus;
    String bookShelf;
    String notes;
    TextView textViewBookshelf;
    TextView textViewStatus;
    TextView textViewNotes;
    TextView contentTextView;
    String expand = "[展开]";
    String collapse = "[收起]";
    private ChangeListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.down_fragment, null);
            Bundle bundle = getArguments();
            view.setClickable(true);
            readingStatus = bundle.getString("status");
            bookShelf = bundle.getString("bookShelf");
            notes = bundle.getString("notes");
            textViewStatus = (TextView) view.findViewById(R.id.textViewReadingStatus);
            textViewBookshelf = (TextView) view.findViewById(R.id.textViewBookShelf);
            textViewNotes = (TextView)view.findViewById(R.id.bookinfo);
            textViewNotes.setText(notes);
            textViewStatus.setText(readingStatus);
            textViewBookshelf.setText(bookShelf);
            textViewNotes.getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            contentTextView = textViewNotes;
                            textViewNotes.getViewTreeObserver().removeOnPreDrawListener(this);

                            expandText();
                            return false;
                        }
                    });
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
                    intentUpdate.putExtra("notes",notes);
                    intentUpdate.putExtra("law",bundle.getString("law"));
                    intentUpdate.putExtra("hyperlink",bundle.getString("hyperlink"));
                    intentUpdate.putExtra("content",bundle.getString("content"));
                    intentUpdate.putExtra("mark",0);
                    startActivity(intentUpdate);
                }
            });
            FloatingActionButton floatingActionDeleteButton = view.findViewById(R.id.floatingActionDeleteButton);
            floatingActionDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View delete_view) {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.confirmation)
                            .setMessage(R.string.sure_to_delete)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listener.setData("delete," + bundle.getInt("position") + ","+ bundle.getInt("loadCount"));
                                }
                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).create();
                    alertDialog.show();
                }
            });
            Button button = view.findViewById(R.id.button_hide);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.setData("hide");
                }
            });
            return view;
    }
    public interface ChangeListener
    {
        void setData(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChangeListener) context;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void expandText() {

        CharSequence text = contentTextView.getText();

        int width = contentTextView.getWidth();
        TextPaint paint = contentTextView.getPaint();
        Layout layout = contentTextView.getLayout();
        int line = layout.getLineCount();
        if (line > 4) {
            int start = layout.getLineStart(3);
            int end = layout.getLineVisibleEnd(3);
            CharSequence lastLine = text.subSequence(start, end);

            float expandWidth = paint.measureText(expand);
            float remain = width - expandWidth;

            CharSequence ellipsize =
                    TextUtils.ellipsize(lastLine,
                            paint,
                            remain,
                            TextUtils.TruncateAt.END);

            ClickableSpan clickableSpan = new ClickableSpan() {

                @Override
                public void onClick(@NonNull View widget) {

                    collapseText();
                }
            };

            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(text.subSequence(0, start));
            ssb.append(ellipsize);
            ssb.append(expand);
            ssb.setSpan(new ForegroundColorSpan(0xFF7369F8),
                    ssb.length() - expand.length(), ssb.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ssb.setSpan(clickableSpan,
                    ssb.length() - expand.length(), ssb.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
            contentTextView.setText(ssb);
        }
    }

    private void collapseText() {

        // 默认此时文本肯定超过行数了，直接在最后拼接文本
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void onClick(@NonNull View widget) {

                expandText();
            }
        };

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(notes);
        ssb.append(collapse);
        ssb.setSpan(new ForegroundColorSpan(0xFF7369F8),
                ssb.length() - collapse.length(), ssb.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.setSpan(clickableSpan,
                ssb.length() - collapse.length(), ssb.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        contentTextView.setText(ssb);
    }
}
