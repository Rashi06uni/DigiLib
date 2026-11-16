
package com.example.digitalib;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(255, 232, 240));
        getWindow().setNavigationBarColor(Color.rgb(255, 246, 249));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(25), dp(30), dp(25), dp(30));

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(255, 232, 240),
                        Color.rgb(255, 246, 249),
                        Color.rgb(250, 220, 231)
                }
        );

        root.setBackground(bg);

        TextView sparkle = new TextView(this);
        sparkle.setText("✦       ✧       ✦");
        sparkle.setTextSize(23);
        sparkle.setTextColor(Color.rgb(174, 35, 78));
        sparkle.setGravity(Gravity.CENTER);

        TextView book = new TextView(this);
        book.setText("📖");
        book.setTextSize(85);
        book.setGravity(Gravity.CENTER);

        TextView logo = new TextView(this);
        logo.setText("DigiLib");
        logo.setTextSize(43);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setTextColor(Color.rgb(157, 25, 67));
        logo.setGravity(Gravity.CENTER);
        logo.setPadding(0, dp(18), 0, dp(5));

        TextView tagline = new TextView(this);
        tagline.setText("YOUR LITTLE WORLD OF BOOKS");
        tagline.setTextSize(11);
        tagline.setLetterSpacing(0.16f);
        tagline.setTextColor(Color.rgb(130, 95, 110));
        tagline.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);
        progress.setIndeterminateTintList(
                android.content.res.ColorStateList.valueOf(
                        Color.rgb(157, 25, 67)
                )
        );

        LinearLayout.LayoutParams progressParams =
                new LinearLayout.LayoutParams(dp(35), dp(35));
        progressParams.topMargin = dp(55);

        TextView footer = new TextView(this);
        footer.setText("A little escape into the world of stories");
        footer.setTextSize(12);
        footer.setTextColor(Color.rgb(150, 110, 125));
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, dp(25), 0, 0);

        root.addView(sparkle);
        root.addView(book);
        root.addView(logo);
        root.addView(tagline);
        root.addView(progress, progressParams);
        root.addView(footer);

        setContentView(root);

        book.setAlpha(0f);
        book.setScaleX(0.5f);
        book.setScaleY(0.5f);

        logo.setAlpha(0f);
        tagline.setAlpha(0f);
        sparkle.setAlpha(0f);

        ObjectAnimator bookAlpha =
                ObjectAnimator.ofFloat(book, "alpha", 0f, 1f);

        ObjectAnimator bookX =
                ObjectAnimator.ofFloat(book, "scaleX", 0.5f, 1f);

        ObjectAnimator bookY =
                ObjectAnimator.ofFloat(book, "scaleY", 0.5f, 1f);

        ObjectAnimator logoAlpha =
                ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);

        ObjectAnimator taglineAlpha =
                ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);

        ObjectAnimator sparkleAlpha =
                ObjectAnimator.ofFloat(sparkle, "alpha", 0f, 1f);

        AnimatorSet animation = new AnimatorSet();

        animation.playTogether(
                bookAlpha, bookX, bookY,
                logoAlpha, taglineAlpha, sparkleAlpha
        );

        animation.setDuration(1300);
        animation.setInterpolator(
                new AccelerateDecelerateInterpolator()
        );
        animation.start();

        ObjectAnimator floatBook =
                ObjectAnimator.ofFloat(
                        book, "translationY",
                        0f, -dp(10), 0f
                );

        floatBook.setDuration(1800);
        floatBook.setRepeatCount(ValueAnimator.INFINITE);
        floatBook.start();

        // OPEN HOME SCREEN AFTER 3 SECONDS
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            Intent intent = new Intent(
                    SplashActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();

            overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );

        }, 3000);
    }
}
