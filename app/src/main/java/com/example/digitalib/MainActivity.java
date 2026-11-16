
package com.example.digitalib;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BookRepository repo;
    private BookAdapter adapter;
    private EditText search;
    private ListView list;
    private TextView collectionCount;
    private LinearLayout chipContainer;

    private String selectedCategory = "All Categories";

    private final String[] categories = {
            "All Categories",
            "Programming",
            "Fiction",
            "Science",
            "Business"
    };

    private final int PINK = Color.rgb(157, 25, 67);
    private final int DARK = Color.rgb(65, 35, 48);
    private final int MUTED = Color.rgb(145, 105, 120);
    private final int BACKGROUND = Color.rgb(255, 246, 249);

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        repo = ((DigiLibApplication) getApplication()).getRepository();

        buildScreen();
        refreshBooks();
    }

    private void buildScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), 0);
        root.setBackgroundColor(BACKGROUND);

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout brandSection = new LinearLayout(this);
        brandSection.setOrientation(LinearLayout.VERTICAL);

        TextView brand = new TextView(this);
        brand.setText("DigiLib");
        brand.setTextSize(32);
        brand.setTypeface(null, Typeface.BOLD);
        brand.setTextColor(PINK);

        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR LITTLE WORLD OF BOOKS");
        subtitle.setTextSize(10);
        subtitle.setLetterSpacing(0.08f);
        subtitle.setTextColor(MUTED);

        brandSection.addView(brand);
        brandSection.addView(subtitle);

        header.addView(brandSection,
                new LinearLayout.LayoutParams(0, -2, 1));

        // THREE DOT MENU
        TextView menuButton = new TextView(this);
        menuButton.setText("⋮");
        menuButton.setTextSize(32);
        menuButton.setTextColor(PINK);
        menuButton.setGravity(Gravity.CENTER);
        menuButton.setPadding(dp(15), 0, dp(8), 0);
        menuButton.setOnClickListener(v -> showAppMenu(menuButton));

        header.addView(menuButton);
        root.addView(header);

        // LOGIN SHORTCUT
        TextView loginShortcut = new TextView(this);
        loginShortcut.setText("♡   Login / My Account");
        loginShortcut.setTextSize(13);
        loginShortcut.setTextColor(PINK);
        loginShortcut.setGravity(Gravity.CENTER_VERTICAL);
        loginShortcut.setPadding(dp(14), 0, dp(14), 0);

        GradientDrawable loginBg = new GradientDrawable();
        loginBg.setColor(Color.WHITE);
        loginBg.setCornerRadius(dp(14));
        loginBg.setStroke(dp(1), Color.rgb(240, 220, 230));
        loginShortcut.setBackground(loginBg);

        LinearLayout.LayoutParams loginParams =
                new LinearLayout.LayoutParams(-1, dp(45));

        loginParams.topMargin = dp(12);
        root.addView(loginShortcut, loginParams);

        loginShortcut.setOnClickListener(v -> {
            startActivity(new Intent(
                    MainActivity.this,
                    LoginActivity.class
            ));
        });

        // WELCOME MESSAGE
        TextView welcome = new TextView(this);
        welcome.setText("Find your next");
        welcome.setTextSize(24);
        welcome.setTypeface(null, Typeface.BOLD);
        welcome.setTextColor(DARK);
        welcome.setPadding(0, dp(22), 0, 0);

        root.addView(welcome);

        TextView welcome2 = new TextView(this);
        welcome2.setText("favourite read. ♡");
        welcome2.setTextSize(24);
        welcome2.setTypeface(null, Typeface.BOLD);
        welcome2.setTextColor(PINK);

        root.addView(welcome2);

        // COLLECTION COUNTER
        collectionCount = new TextView(this);
        collectionCount.setTextSize(13);
        collectionCount.setTextColor(MUTED);
        collectionCount.setPadding(0, dp(9), 0, dp(18));

        root.addView(collectionCount);

        // SEARCH BOX
        LinearLayout searchBox = new LinearLayout(this);
        searchBox.setOrientation(LinearLayout.HORIZONTAL);
        searchBox.setGravity(Gravity.CENTER_VERTICAL);
        searchBox.setPadding(dp(15), 0, dp(12), 0);

        GradientDrawable searchBg = new GradientDrawable();
        searchBg.setColor(Color.WHITE);
        searchBg.setCornerRadius(dp(16));
        searchBg.setStroke(dp(1), Color.rgb(240, 220, 230));

        searchBox.setBackground(searchBg);

        TextView searchIcon = new TextView(this);
        searchIcon.setText("⌕");
        searchIcon.setTextSize(27);
        searchIcon.setTextColor(PINK);

        searchBox.addView(searchIcon);

        search = new EditText(this);
        search.setSingleLine(true);
        search.setTextSize(14);
        search.setHint("Search books, authors...");
        search.setHintTextColor(Color.rgb(165, 140, 150));
        search.setTextColor(DARK);
        search.setBackgroundColor(Color.TRANSPARENT);
        search.setPadding(dp(10), 0, 0, 0);

        searchBox.addView(search,
                new LinearLayout.LayoutParams(0, dp(52), 1));

        LinearLayout.LayoutParams searchParams =
                new LinearLayout.LayoutParams(-1, dp(54));

        searchParams.bottomMargin = dp(20);
        root.addView(searchBox, searchParams);

        // CATEGORY HEADING
        TextView categoryHeading = new TextView(this);
        categoryHeading.setText("Browse Categories");
        categoryHeading.setTextSize(17);
        categoryHeading.setTypeface(null, Typeface.BOLD);
        categoryHeading.setTextColor(DARK);
        categoryHeading.setPadding(0, 0, 0, dp(12));

        root.addView(categoryHeading);

        // CATEGORY CHIPS
        HorizontalScrollView horizontalScroll =
                new HorizontalScrollView(this);

        horizontalScroll.setHorizontalScrollBarEnabled(false);

        chipContainer = new LinearLayout(this);
        chipContainer.setOrientation(LinearLayout.HORIZONTAL);

        horizontalScroll.addView(chipContainer);

        createCategoryChips();

        LinearLayout.LayoutParams chipParams =
                new LinearLayout.LayoutParams(-1, dp(45));

        chipParams.bottomMargin = dp(16);
        root.addView(horizontalScroll, chipParams);

        // BOOK HEADING
        TextView heading = new TextView(this);
        heading.setText("Explore the collection");
        heading.setTextSize(19);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(DARK);
        heading.setPadding(0, dp(5), 0, dp(10));

        root.addView(heading);

        // BOOK LIST
        list = new ListView(this);
        list.setDivider(null);
        list.setDividerHeight(0);
        list.setSelector(android.R.color.transparent);
        list.setCacheColorHint(Color.TRANSPARENT);

        root.addView(list,
                new LinearLayout.LayoutParams(-1, 0, 1));

        setContentView(root);

        // ADAPTER
        adapter = new BookAdapter(this, new ArrayList<>());
        list.setAdapter(adapter);

        // BOOK CLICK
        list.setOnItemClickListener((parent, view, position, id) -> {

            Book book = adapter.getItem(position);

            Intent intent = new Intent(
                    MainActivity.this,
                    BookDetailsActivity.class
            );

            intent.putExtra("BOOK_ID", book.getId());
            startActivity(intent);
        });

        // SEARCH LISTENER
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                refreshBooks();
            }

            @Override
            public void afterTextChanged(Editable e) {
            }
        });
    }

    // CATEGORY CHIPS
    private void createCategoryChips() {

        chipContainer.removeAllViews();

        for (String item : categories) {

            TextView chip = new TextView(this);
            chip.setText(item);
            chip.setTextSize(12);
            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(16), 0, dp(16), 0);

            boolean selected = item.equals(selectedCategory);

            GradientDrawable chipBg = new GradientDrawable();
            chipBg.setCornerRadius(dp(25));

            if (selected) {
                chipBg.setColor(PINK);
                chip.setTextColor(Color.WHITE);
                chip.setTypeface(null, Typeface.BOLD);
            } else {
                chipBg.setColor(Color.WHITE);
                chipBg.setStroke(dp(1), Color.rgb(240, 220, 230));
                chip.setTextColor(DARK);
            }

            chip.setBackground(chipBg);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(-2, dp(40));

            params.rightMargin = dp(9);
            chipContainer.addView(chip, params);

            chip.setOnClickListener(v -> {
                selectedCategory = item;
                createCategoryChips();
                refreshBooks();
            });
        }
    }

    // APP MENU
    private void showAppMenu(View anchor) {

        PopupMenu popup = new PopupMenu(this, anchor);

        popup.getMenu().add("My Borrowed Books");
        popup.getMenu().add("About");
        popup.getMenu().add("Exit");

        popup.setOnMenuItemClickListener(item -> {

            String selected = item.getTitle().toString();

            if (selected.equals("My Borrowed Books")) {

                startActivity(new Intent(
                        MainActivity.this,
                        BorrowedBooksActivity.class
                ));

            } else if (selected.equals("About")) {

                new AlertDialog.Builder(this)
                        .setTitle("About DigiLib")
                        .setMessage(
                                "DigiLib is a digital library where students can explore, borrow and return books."
                        )
                        .setPositiveButton("OK", null)
                        .show();

            } else if (selected.equals("Exit")) {

                new AlertDialog.Builder(this)
                        .setTitle("Exit DigiLib")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Exit",
                                (dialog, which) -> finish())
                        .show();
            }

            return true;
        });

        popup.show();
    }

    // FILTER BOOKS
    private void refreshBooks() {

        if (adapter == null || search == null) {
            return;
        }

        String query = search.getText().toString()
                .toLowerCase(Locale.ROOT).trim();

        List<Book> allBooks = repo.getAllBooks();
        List<Book> filtered = new ArrayList<>();

        for (Book book : allBooks) {

            boolean matchesTitle = book.getTitle()
                    .toLowerCase(Locale.ROOT)
                    .contains(query);

            boolean matchesAuthor = book.getAuthor()
                    .toLowerCase(Locale.ROOT)
                    .contains(query);

            boolean matchesCategory =
                    selectedCategory.equals("All Categories")
                            || book.getCategory().equals(selectedCategory);

            if ((matchesTitle || matchesAuthor) && matchesCategory) {
                filtered.add(book);
            }
        }

        adapter.update(filtered);

        collectionCount.setText(
                allBooks.size() + " Books  •  4 Categories"
                        + "  •  " + filtered.size() + " Showing"
        );
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            refreshBooks();
        }
    }
}
