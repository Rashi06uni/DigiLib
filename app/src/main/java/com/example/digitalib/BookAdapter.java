
package com.example.digitalib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private final Context context;
    private final List<Book> books;

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        this.context = context;
        this.books = new ArrayList<>(books);
    }

    public void update(List<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_book, parent, false);
        }

        Book book = getItem(position);

        TextView title = view.findViewById(R.id.tvBookTitle);
        TextView author = view.findViewById(R.id.tvBookAuthor);
        TextView category = view.findViewById(R.id.tvBookCategory);
        TextView status = view.findViewById(R.id.tvBookStatus);

        title.setText(book.getTitle());
        author.setText("by " + book.getAuthor());
        category.setText(book.getCategory());

        // Category styling
        category.setTypeface(null, Typeface.BOLD);

        // Status badge styling
        GradientDrawable badge = new GradientDrawable();
        badge.setCornerRadius(dp(20));

        if (book.isAvailable()) {

            status.setText("✓  AVAILABLE");
            status.setTextColor(Color.rgb(35, 125, 80));
            badge.setColor(Color.rgb(228, 246, 235));

        } else {

            status.setText("●  BORROWED");
            status.setTextColor(Color.rgb(180, 45, 65));
            badge.setColor(Color.rgb(255, 232, 237));
        }

        status.setBackground(badge);
        status.setTypeface(null, Typeface.BOLD);

        return view;
    }

    private int dp(int value) {
        return (int) (value *
                context.getResources().getDisplayMetrics().density);
    }
}
