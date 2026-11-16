
package com.example.digitalib;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BorrowedBooksActivity extends AppCompatActivity {

    private BookRepository repo;
    private LinearLayout list;
    private TextView count;

    private final int PINK = Color.rgb(157, 25, 67);
    private final int DARK = Color.rgb(65, 35, 48);
    private final int MUTED = Color.rgb(145, 105, 120);
    private final int BACKGROUND = Color.rgb(255, 246, 249);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        repo = ((DigiLibApplication) getApplication()).getRepository();

        render();
    }

    private void render() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(22), dp(18), dp(15));
        root.setBackgroundColor(BACKGROUND);

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        TextView back = new TextView(this);
        back.setText("←");
        back.setTextSize(30);
        back.setTextColor(PINK);
        back.setPadding(0, 0, dp(15), 0);
        back.setOnClickListener(v -> finish());

        header.addView(back);

        TextView brand = new TextView(this);
        brand.setText("DigiLib");
        brand.setTextSize(28);
        brand.setTypeface(null, Typeface.BOLD);
        brand.setTextColor(PINK);

        header.addView(brand);
        root.addView(header);

        // TITLE
        TextView title = new TextView(this);
        title.setText("My Borrowed Books");
        title.setTextSize(25);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(DARK);
        title.setPadding(0, dp(28), 0, dp(5));

        root.addView(title);

        // SUBTITLE
        TextView subtitle = new TextView(this);
        subtitle.setText("Your personal reading collection ♡");
        subtitle.setTextSize(14);
        subtitle.setTextColor(MUTED);

        root.addView(subtitle);

        // COUNTER CARD
        LinearLayout countCard = new LinearLayout(this);
        countCard.setOrientation(LinearLayout.VERTICAL);
        countCard.setGravity(Gravity.CENTER_VERTICAL);
        countCard.setPadding(dp(18), dp(15), dp(18), dp(15));

        GradientDrawable countBg = new GradientDrawable();
        countBg.setColor(Color.rgb(252, 232, 239));
        countBg.setCornerRadius(dp(16));
        countBg.setStroke(dp(1), Color.rgb(245, 210, 225));

        countCard.setBackground(countBg);

        count = new TextView(this);
        count.setText("♡  0 BOOKS BORROWED");
        count.setTextSize(16);
        count.setTypeface(null, Typeface.BOLD);
        count.setTextColor(PINK);
        count.setGravity(Gravity.CENTER_VERTICAL);

        countCard.addView(count);

        LinearLayout.LayoutParams countParams =
                new LinearLayout.LayoutParams(-1, dp(58));

        countParams.topMargin = dp(20);
        countParams.bottomMargin = dp(18);

        root.addView(countCard, countParams);

        // SCROLL VIEW
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);

        scroll.addView(list);

        root.addView(scroll,
                new LinearLayout.LayoutParams(-1, 0, 1));

        setContentView(root);

        refreshList();
    }

    // REFRESH BOOK LIST AND COUNTER
    private void refreshList() {

        list.removeAllViews();

        List<Book> borrowedBooks = repo.getBorrowedBooks();

        int total = borrowedBooks.size();

        if (total == 1) {
            count.setText("♡  1 BOOK BORROWED");
        } else {
            count.setText("♡  " + total + " BOOKS BORROWED");
        }

        // EMPTY STATE
        if (borrowedBooks.isEmpty()) {

            LinearLayout empty = new LinearLayout(this);
            empty.setOrientation(LinearLayout.VERTICAL);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(dp(15), dp(35), dp(15), dp(35));

            TextView icon = new TextView(this);
            icon.setText("📚");
            icon.setTextSize(65);
            icon.setGravity(Gravity.CENTER);

            empty.addView(icon);

            TextView message = new TextView(this);
            message.setText("Your shelf is waiting!");
            message.setTextSize(21);
            message.setTypeface(null, Typeface.BOLD);
            message.setTextColor(DARK);
            message.setGravity(Gravity.CENTER);
            message.setPadding(0, dp(15), 0, dp(8));

            empty.addView(message);

            TextView sub = new TextView(this);
            sub.setText(
                    "You haven't borrowed any books yet.\n"
                            + "Explore DigiLib and find your next read!"
            );
            sub.setTextSize(14);
            sub.setTextColor(MUTED);
            sub.setGravity(Gravity.CENTER);

            empty.addView(sub);

            Button explore = new Button(this);
            explore.setText("Explore Library");
            explore.setTextColor(Color.WHITE);
            explore.setAllCaps(false);
            explore.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(PINK)
            );

            LinearLayout.LayoutParams exploreParams =
                    new LinearLayout.LayoutParams(-2, dp(48));

            exploreParams.topMargin = dp(20);

            empty.addView(explore, exploreParams);

            explore.setOnClickListener(v -> finish());

            list.addView(empty,
                    new LinearLayout.LayoutParams(-1, -2));

            return;
        }

        // BOOK CARDS
        for (Book book : borrowedBooks) {

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(18), dp(18), dp(18), dp(18));

            GradientDrawable cardBg = new GradientDrawable();
            cardBg.setColor(Color.WHITE);
            cardBg.setCornerRadius(dp(20));
            cardBg.setStroke(dp(1), Color.rgb(240, 220, 230));

            card.setBackground(cardBg);

            // CATEGORY
            TextView category = new TextView(this);
            category.setText(book.getCategory().toUpperCase());
            category.setTextSize(11);
            category.setTextColor(PINK);
            category.setTypeface(null, Typeface.BOLD);

            card.addView(category);

            // TITLE
            TextView name = new TextView(this);
            name.setText(book.getTitle());
            name.setTextSize(20);
            name.setTypeface(null, Typeface.BOLD);
            name.setTextColor(DARK);
            name.setPadding(0, dp(10), 0, dp(5));

            card.addView(name);

            // AUTHOR
            TextView author = new TextView(this);
            author.setText("By " + book.getAuthor());
            author.setTextSize(14);
            author.setTextColor(MUTED);

            card.addView(author);

            // STATUS
            TextView status = new TextView(this);
            status.setText("●  CURRENTLY BORROWED");
            status.setTextSize(11);
            status.setTextColor(Color.rgb(180, 45, 65));
            status.setTypeface(null, Typeface.BOLD);
            status.setPadding(0, dp(14), 0, dp(12));

            card.addView(status);

            // RETURN BUTTON
            Button returnButton = new Button(this);
            returnButton.setText("Return Book");
            returnButton.setTextColor(Color.WHITE);
            returnButton.setAllCaps(false);
            returnButton.setTypeface(null, Typeface.BOLD);
            returnButton.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(PINK)
            );

            card.addView(returnButton);

            returnButton.setOnClickListener(v -> {

                new AlertDialog.Builder(this)
                        .setTitle("Confirm Return")
                        .setMessage(
                                "Do you want to return "
                                        + book.getTitle() + "?"
                        )
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Return", (dialog, which) -> {

                            boolean success =
                                    repo.returnBook(book.getId());

                            if (success) {

                                refreshList();

                                new AlertDialog.Builder(this)
                                        .setMessage(
                                                "Book returned successfully!"
                                        )
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        })
                        .show();
            });

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(-1, -2);

            cardParams.bottomMargin = dp(15);

            list.addView(card, cardParams);
        }
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }
}
