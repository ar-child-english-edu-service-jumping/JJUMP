package com.jjump.java;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.jjump.R;


public class ProfileFragment extends Fragment implements CircleProgressBar.ProgressFormatter{

    ImageButton btn_contact;
    CircleProgressBar circleProgressBar1;
    private static final String DEFAULT_PATTERN = "%d%%";
    private int studyRate = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        btn_contact = rootView.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"admin.JJUMP@gmail.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT,"JJUMP 팀에게 문의하기");
                email.putExtra(Intent.EXTRA_TEXT,"\n\n\n문의주셔서 감사합니다. 빠른 시일 내에 답변 드리겠습니다 :)");
                startActivity(email);
            }
        });
        // get 학습률 to studyRate and update progress bar
        studyRate = 68;
        circleProgressBar1 = rootView.findViewById(R.id.circle_bar1);
        circleProgressBar1.setProgress(studyRate);

        return rootView;
    }


    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}