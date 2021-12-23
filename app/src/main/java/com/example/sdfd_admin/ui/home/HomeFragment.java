package com.example.sdfd_admin.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sdfd_admin.Diabetes.DiabeteActivity;
import com.example.sdfd_admin.Hear.HearActivity;
import com.example.sdfd_admin.Popular.PopularActivity;
import com.example.sdfd_admin.R;
import com.example.sdfd_admin.databinding.FragmentHomeBinding;
import com.example.sdfd_admin.weightloss.WeightLossActivity;

public class HomeFragment extends Fragment {

   CardView btn1,btn2,btn3,btn4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
      btn1=root.findViewById(R.id.btn_popular);
      btn2=root.findViewById(R.id.btn_hear);
      btn3=root.findViewById(R.id.btn_weightloss);
      btn4=root.findViewById(R.id.btn_diabetes);

      btn1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(getActivity(), PopularActivity.class);
              startActivity(intent);
          }
      });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), HearActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), WeightLossActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), DiabeteActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


}