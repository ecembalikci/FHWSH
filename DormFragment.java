package de.fhws.international.fhwsh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.fhws.international.fhwsh.MainActivity;
import de.fhws.international.fhwsh.R;

public class DormFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dorm, container, false);
    }
    TextView textView=findViewById(R.id.text_view);
    String text="Click on THIS to find your dorm./ Click on HERE to read the news on FHWS' website.";
    SpannableString ss=new SpannableString(text);
    ClickableSpan clickableSpan1=new ClickableSpan(){
        @Override
        public void onClick(View widget){
            android.widget.Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
        }


    ss.setSpan(clickableSpan1,9,13,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


    textView.setText(ss);
    textView.setMovementMethod(LinkMovementMethod.getInstance());


    }
