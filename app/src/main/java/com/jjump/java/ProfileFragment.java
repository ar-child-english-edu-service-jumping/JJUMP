package com.jjump.java;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.jjump.R;


public class ProfileFragment extends Fragment implements CircleProgressBar.ProgressFormatter{

    ImageButton btn_contact;
    CircleProgressBar circleProgressBar1;
    TextView total_word_number;
    TextView study_rate_number;
    TextView tv_child_name;
    ImageView iv_profile_picture;
    String child_name;
    int pass_character_id = 1;
    private static final String DEFAULT_PATTERN = "%d%%";
    private int studyRate = 0;
    private int totalWordCount = 12;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        btn_contact = rootView.findViewById(R.id.btn_contact);
        circleProgressBar1 = rootView.findViewById(R.id.circle_bar1);
        total_word_number = rootView.findViewById(R.id.total_word_number);
        study_rate_number = rootView.findViewById(R.id.study_rate_number);
        tv_child_name = rootView.findViewById(R.id.tv_child_name);
        iv_profile_picture = rootView.findViewById(R.id.iv_profile_picture);


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

        //get 총 저장한 단어 개수, save to totalWordCount and settext
        total_word_number.setText("총 " + totalWordCount + "개의 단어를 배웠어요!");

        // get 학습률, save to studyRate and update progress bar
        studyRate = 15;
        circleProgressBar1.setProgress(studyRate);

        // studyRate에 대해 지정된 메세지 출력
        if (studyRate <= 20){
            study_rate_number.setText("함께 꾸준히 독서해 보아요 🤓");
        } else if(20 < studyRate && studyRate <= 50 ) {
            study_rate_number.setText("잘하고 있어요 🥳");
        } else if(50 < studyRate && studyRate < 80 ) {
            study_rate_number.setText("학습률이 높아요 🤩");
        } else if(80 < studyRate && studyRate < 100 ) {
            study_rate_number.setText("학습률이 매우 높아요 😍");
        }
        // open dialog to enter child name
        tv_child_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildNameDialog dialog = new ChildNameDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setDialogListener(new ChildNameDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(String childName) {
                        child_name = childName;
                        tv_child_name.setText(child_name);
                        tv_child_name.setTextColor(Color.parseColor("#181818"));
                    }
                    @Override
                    public void onNegativeClicked() {
                    }
                });
                dialog.show();
            }
        });

        //open dialog to choose character
        iv_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharacterDialog dialog = new CharacterDialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setDialogListener(new CharacterDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(int character_id) {
                        pass_character_id = character_id;
                        switch (pass_character_id){
                            case 0:
                                iv_profile_picture.setImageResource(R.drawable.ch_lion);
                                iv_profile_picture.setBackground(null);
                                break;
                            case 1 :
                                iv_profile_picture.setImageResource(R.drawable.ch_elephant);
                                iv_profile_picture.setBackground(null);
                                break;
                            case 2 :
                                iv_profile_picture.setImageResource(R.drawable.ch_cat);
                                iv_profile_picture.setBackground(null);
                                break;
                            case 3 :
                                Toast.makeText(getContext(), "퀴즈를 10번 풀어 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                                break;
                            case 4 :
                                Toast.makeText(getContext(), "퀴즈를 100점 맞아서 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                                break;
                            case 5 :
                                Toast.makeText(getContext(), "단어를 20개 추가해 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                                break;
                            case 6 :
                                Toast.makeText(getContext(), "나무를 2단계로 성장시켜 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                                break;
                            case 7 :
                                Toast.makeText(getContext(), "나무를 3단계로 성장시켜 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                                break;
                            case 8 :
                                Toast.makeText(getContext(), "잠겨있는 동물을 획득하려면 단어를 50개 추가해요!", Toast.LENGTH_SHORT).show();
                                break;


                        }
                    }
                    @Override
                    public void onNegativeClicked() {
                    }
                });
                dialog.show();

            }
        });


        return rootView;
    }


    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}