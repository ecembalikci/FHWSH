package de.fhws.international.fhwsh.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.fhws.international.fhwsh.LoginActivity;
import de.fhws.international.fhwsh.MainActivity;
import de.fhws.international.fhwsh.R;

public class AccountFragment extends Fragment implements View.OnClickListener {
    Button logUot;
    SharedPreferences sharedpreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        logUot = view.findViewById(R.id.logOut);
        logUot.setOnClickListener(this);

        sharedpreferences = getActivity().getSharedPreferences(getString(R.string.refName),
                Context.MODE_PRIVATE);
        ((TextView) view.findViewById(R.id.cityAcc)).setText(sharedpreferences.getString("city", ""));
        ((TextView) view.findViewById(R.id.nameAcc)).setText(sharedpreferences.getString("firstName", "") + " " + sharedpreferences.getString("lastName", ""));
        ((TextView) view.findViewById(R.id.semesterAcc)).setText(sharedpreferences.getString("semester", ""));
        ((TextView) view.findViewById(R.id.telephoneAcc)).setText(sharedpreferences.getString("telephone", ""));
        ((TextView) view.findViewById(R.id.facultyAcc)).setText(sharedpreferences.getString("faculty", ""));


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOut:
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
