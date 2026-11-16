
package com.example.digitalib;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    EditText emailInput, passwordInput;

    int pink = Color.rgb(249, 203, 214);
    int darkPink = Color.rgb(158, 24, 43);
    int darkText = Color.rgb(55, 40, 45);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(pink);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(28), dp(30), dp(28), dp(30));

        scrollView.addView(root);

        TextView logo = new TextView(this);
        logo.setText("📚 DigiLib");
        logo.setTextSize(34);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setTextColor(darkPink);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);

        TextView heading = new TextView(this);
        heading.setText("Welcome Back!");
        heading.setTextSize(28);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkText);
        heading.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams headingParams =
                new LinearLayout.LayoutParams(
                        -1, -2);
        headingParams.topMargin = dp(35);
        root.addView(heading, headingParams);

        TextView subtitle = new TextView(this);
        subtitle.setText("Login to continue your reading journey");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams subtitleParams =
                new LinearLayout.LayoutParams(-1, -2);
        subtitleParams.topMargin = dp(10);
        root.addView(subtitle, subtitleParams);

        emailInput = createInput("Enter your email");
        emailInput.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT |
                        android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        LinearLayout.LayoutParams emailParams =
                new LinearLayout.LayoutParams(-1, dp(55));
        emailParams.topMargin = dp(35);
        root.addView(emailInput, emailParams);

        passwordInput = createInput("Enter your password");
        passwordInput.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT |
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        LinearLayout.LayoutParams passwordParams =
                new LinearLayout.LayoutParams(-1, dp(55));
        passwordParams.topMargin = dp(18);
        root.addView(passwordInput, passwordParams);

        Button loginButton = new Button(this);
        loginButton.setText("LOGIN");
        loginButton.setTextSize(16);
        loginButton.setTypeface(null, Typeface.BOLD);
        loginButton.setTextColor(Color.WHITE);
        loginButton.setAllCaps(false);
        loginButton.setBackground(makeBackground(darkPink, 16));

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(-1, dp(55));
        buttonParams.topMargin = dp(28);
        root.addView(loginButton, buttonParams);

        loginButton.setOnClickListener(v -> loginUser());

        TextView signupText = new TextView(this);
        signupText.setText("Don't have an account? Sign Up");
        signupText.setTextSize(15);
        signupText.setTextColor(darkPink);
        signupText.setTypeface(null, Typeface.BOLD);
        signupText.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams signupParams =
                new LinearLayout.LayoutParams(-1, -2);
        signupParams.topMargin = dp(25);
        root.addView(signupText, signupParams);

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    SignupActivity.class
            );
            startActivity(intent);
        });

        setContentView(scrollView);
    }

    private EditText createInput(String hint) {

        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setTextSize(15);
        editText.setSingleLine(true);
        editText.setTextColor(darkText);
        editText.setHintTextColor(Color.GRAY);
        editText.setPadding(dp(18), 0, dp(18), 0);
        editText.setBackground(makeBackground(Color.WHITE, 14));

        return editText;
    }

    private GradientDrawable makeBackground(int color, int radius) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));

        return drawable;
    }

    private void loginUser() {

        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Toast.makeText(
                this,
                "Welcome to DigiLib!",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(
                LoginActivity.this,
                MainActivity.class
        );

        startActivity(intent);
        finish();
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density);
    }
}
