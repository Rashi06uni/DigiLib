
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

public class BookDetailsActivity extends AppCompatActivity {

    private Book book;
    private BookRepository repo;
    private Button action;
    private TextView status;

    private final int PINK = Color.rgb(157, 25, 67);
    private final int BACKGROUND = Color.rgb(255, 246, 249);
    private final int DARK = Color.rgb(65, 35, 48);
    private final int MUTED = Color.rgb(130, 95, 110);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        repo = ((DigiLibApplication) getApplication()).getRepository();

        String bookId = getIntent().getStringExtra("BOOK_ID");
        book = repo.getBookById(bookId);

        if (book == null) {
            finish();
            return;
        }

        render();
    }

    private void render() {

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BACKGROUND);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(24), dp(22), dp(28));

        scroll.addView(root);

        // TOP BAR
        TextView back = new TextView(this);
        back.setText("←  Back to Library");
        back.setTextSize(15);
        back.setTextColor(PINK);
        back.setTypeface(null, Typeface.BOLD);
        back.setPadding(0, 0, 0, dp(25));

        back.setOnClickListener(v -> finish());

        root.addView(back);

        // BOOK ICON
        TextView icon = new TextView(this);
        icon.setText("📖");
        icon.setTextSize(65);
        icon.setGravity(Gravity.CENTER);
        icon.setPadding(0, dp(20), 0, dp(20));

        GradientDrawable iconBg = new GradientDrawable();
        iconBg.setColor(Color.rgb(250, 226, 235));
        iconBg.setCornerRadius(dp(22));
        icon.setBackground(iconBg);

        root.addView(icon);

        // CATEGORY
        TextView tag = new TextView(this);
        tag.setText(book.getCategory().toUpperCase());
        tag.setTextColor(PINK);
        tag.setTextSize(12);
        tag.setTypeface(null, Typeface.BOLD);
        tag.setPadding(0, dp(25), 0, dp(10));

        root.addView(tag);

        // TITLE
        TextView title = new TextView(this);
        title.setText(book.getTitle());
        title.setTextSize(29);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(DARK);

        root.addView(title);

        // AUTHOR
        TextView author = new TextView(this);
        author.setText("Written by " + book.getAuthor());
        author.setTextSize(15);
        author.setTextColor(MUTED);
        author.setPadding(0, dp(10), 0, dp(25));

        root.addView(author);

        // DETAILS CARD
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(20), dp(20), dp(20), dp(20));

        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setColor(Color.WHITE);
        cardBg.setCornerRadius(dp(20));
        cardBg.setStroke(dp(1), Color.rgb(242, 224, 232));

        card.setBackground(cardBg);

        TextView about = new TextView(this);
        about.setText("About this book");
        about.setTextSize(19);
        about.setTypeface(null, Typeface.BOLD);
        about.setTextColor(DARK);

        card.addView(about);

        TextView desc = new TextView(this);
        desc.setText(book.getDescription());
        desc.setTextSize(15);
        desc.setTextColor(MUTED);
        desc.setLineSpacing(dp(6), 1);
        desc.setPadding(0, dp(14), 0, dp(20));

        card.addView(desc);

        // AVAILABILITY LABEL
        TextView availabilityTitle = new TextView(this);
        availabilityTitle.setText("BOOK STATUS");
        availabilityTitle.setTextSize(11);
        availabilityTitle.setTypeface(null, Typeface.BOLD);
        availabilityTitle.setTextColor(MUTED);

        card.addView(availabilityTitle);

        status = new TextView(this);
        status.setTextSize(14);
        status.setTypeface(null, Typeface.BOLD);
        status.setPadding(dp(12), dp(10), dp(12), dp(10));

        LinearLayout.LayoutParams statusParams =
                new LinearLayout.LayoutParams(-2, -2);
        statusParams.topMargin = dp(8);

        card.addView(status, statusParams);

        root.addView(card);

        // BORROW / RETURN BUTTON
        action = new Button(this);
        action.setTextColor(Color.WHITE);
        action.setTextSize(15);
        action.setAllCaps(false);
        action.setTypeface(null, Typeface.BOLD);

        action.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(PINK)
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(-1, dp(56));

        buttonParams.topMargin = dp(25);

        root.addView(action, buttonParams);

        action.setOnClickListener(v -> confirmAction());

        setContentView(scroll);

        updateStatus();
    }

    private void updateStatus() {

        if (book.isAvailable()) {

            status.setText("✓  Available to borrow");
            status.setTextColor(Color.rgb(30, 125, 75));

            status.setBackgroundColor(Color.rgb(226, 246, 234));

            action.setText("Borrow This Book");

        } else {

            status.setText("●  Currently borrowed");
            status.setTextColor(Color.rgb(180, 45, 65));

            status.setBackgroundColor(Color.rgb(255, 232, 237));

            action.setText("Return This Book");
        }
    }

    private void confirmAction() {

        boolean borrowing = book.isAvailable();

        new AlertDialog.Builder(this)
                .setTitle(borrowing
                        ? "Confirm Borrowing"
                        : "Confirm Return")

                .setMessage(borrowing
                        ? "Do you want to borrow \"" +
                        book.getTitle() + "\"?"
                        : "Do you want to return \"" +
                        book.getTitle() + "\"?")

                .setNegativeButton("Cancel", null)

                .setPositiveButton("Confirm", (dialog, which) -> {

                    boolean success;

                    if (borrowing) {
                        success = repo.borrowBook(book.getId());
                    } else {
                        success = repo.returnBook(book.getId());
                    }

                    if (success) {

                        updateStatus();

                        new AlertDialog.Builder(this)
                                .setTitle("Success!")
                                .setMessage(borrowing
                                        ? "Book borrowed successfully!"
                                        : "Book returned successfully!")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                })
                .show();
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }
}
