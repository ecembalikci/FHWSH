package de.fhws.international.fhwsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class LoginActivity extends AppCompatActivity {
    Button logInButton;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        client = new OkHttpClient();

        logInButton = findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout userNameTextInputLayout = findViewById(R.id.userNameLogin);
                final String userName = userNameTextInputLayout.getEditText().getText().toString();

                TextInputLayout passwordTextInputLayout = findViewById(R.id.passwordLogin);
                final String password = passwordTextInputLayout.getEditText().getText().toString();

                client = new OkHttpClient.Builder()
                        .authenticator(new Authenticator() {
                            @Override
                            public Request authenticate(Route route, Response response) throws IOException {
                                if (response.request().header("Authorization") != null) {
                                    return null; // Give up, we've already attempted to authenticate.
                                }

                                System.out.println("Authenticating for response: " + response);
                                System.out.println("Challenges: " + response.challenges());
                                String credential = Credentials.basic(userName, password);
                                return response.request().newBuilder()
                                        .header("Authorization", credential)
                                        .build();
                            }
                        })
                        .build();

                Request request = new Request.Builder()
                        .url("https://api.fiw.fhws.de/auth/api/users/me")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                        if (response.isSuccessful()) {
                            final JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response.body().string());
                                LoginActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.refName), 0);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("userName", userName);
                                        try {
                                            editor.putString("email", jsonObject.getString("emailAddress"));
                                            editor.putString("city", jsonObject.getString("city"));
                                            editor.putString("firstName", jsonObject.getString("firstName"));
                                            editor.putString("lastName", jsonObject.getString("lastName"));
                                            editor.putString("semester", jsonObject.getString("semester"));
                                            editor.putString("telephone", jsonObject.getString("telephoneNumber"));
                                            editor.putString("faculty", jsonObject.getString("facultyName"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        editor.commit(); // commit changes
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}

