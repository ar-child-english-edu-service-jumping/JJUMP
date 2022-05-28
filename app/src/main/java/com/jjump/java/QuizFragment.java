package com.jjump.java;

import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjump.R;

public class QuizFragment extends Fragment {

    // 문제 번호용 변수들
    private Button quiz_num1;
    private Button quiz_num2;
    private Button quiz_num3;
    private Button quiz_num4;
    private Button quiz_num5;

    // 4지 선다용 변수들
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private Button targetBtn;

    private String[][] questions = {{"a", "b", "c", "d"}, {"e", "f", "g", "h"}, {"a", "b", "c", "d"}, {"f", "r", "se", "x"}, {"a", "b", "c", "d"}};

    private int[] answers = {0, 1, 2, 3, 0,-1};

    private int state = 0;
    private int max_question_num = 5;

    private Typeface normalFont;
    private Typeface boldFont;

    private int entered_input = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);

        quiz_num1=rootView.findViewById(R.id.question_1);
        quiz_num2=rootView.findViewById(R.id.question_2);
        quiz_num3=rootView.findViewById(R.id.question_3);
        quiz_num4=rootView.findViewById(R.id.question_4);
        quiz_num5=rootView.findViewById(R.id.question_5);

        normalFont = Typeface.createFromAsset(getActivity().getAssets(), "notosnaskr_medium.otf");
        boldFont = Typeface.createFromAsset(getActivity().getAssets(), "notosanskr_bold.otf");

        answer1 = rootView.findViewById(R.id.answer_1);
        answer2 = rootView.findViewById(R.id.answer_2);
        answer3 = rootView.findViewById(R.id.answer_3);
        answer4 = rootView.findViewById(R.id.answer_4);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 0;
                changeQuestion();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 1;
                changeQuestion();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 2;
                changeQuestion();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 3;
                changeQuestion();
            }
        });

        return rootView;
    }

    private void changeQuestion() {
        
        // 정답 표시
        switch (answers[state]) {
            case 0:
                targetBtn = answer1;
                break;
            case 1:
                targetBtn = answer2;
                break;
            case 2:
                targetBtn = answer3;
                break;
            case 3:
                targetBtn = answer4;
                break;
            default:
                break;
        }
        targetBtn.setBackgroundResource(R.drawable.btn_quiz_answer_correct);
        targetBtn.setTypeface(boldFont);

        if(answers[state]==entered_input){
            // sound correct
        }else{
            // sound wrong
        }

        //1초뒤 다음 문제로
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(state==max_question_num){
                    return;
                }

                targetBtn.setBackgroundResource(R.drawable.btn_quiz_answer);
                targetBtn.setTypeface(normalFont);

                answer1.setText(questions[state][0]);
                answer2.setText(questions[state][1]);
                answer3.setText(questions[state][2]);
                answer4.setText(questions[state][3]);

                state++;
            }
        }, 1000);

    }
}