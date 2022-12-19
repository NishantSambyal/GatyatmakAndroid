package com.gatyatmakjyotish.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gatyatmakjyotish.R;
import com.gatyatmakjyotish.ui.activity.WaitingForLetter;

public class VerifyOTP extends Fragment {


    TextView text;
    EditText edit, qwerty, editext, pen;
    Button proceed;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.verify_otp, container, false);
        proceed = view.findViewById(R.id.btn_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_proceed) {

                }
              /*  getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, new UnderProcess()).addToBackStack(null).commit();*/
              startActivity(new Intent(getActivity(), WaitingForLetter.class));
            }
        });

        return view;
    }

}
