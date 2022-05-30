package com.jjump.java;

import static com.jjump.java.HomeActivity.quiz_taken_int;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jjump.R;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    // 문제 번호용 변수들
    private Button quiz_num1;
    private Button quiz_num2;
    private Button quiz_num3;
    private Button quiz_num4;
    private Button quiz_num5;
    private ArrayList<Button> quiz_nums;

    private int quizBtnSize_normal;
    private int quizBtnSize_current;

    //문제용 변수들
    private View quizView;
    private View previousQuestion;
    private TextView txtQuiz;
    private ImageView gifQuiz;
    private ImageView imgQuiz;
    private Button btnQuiz;
    private Animation quiz_out;
    private String[] quiz_set={"Jellyfish", "지구" , "endemic", "altercation" , "capricious",""};


    // 4지 선다용 변수들
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private Button targetBtn;

    private String[][] questions = { {"Earth", "Month", "Date", "Ocean"}, {"Rocket", "Moon", "Sign", "Train"}, {"Avocado", "Apple", "Orange", "Cucumber"}, {"Dog", "Pig", "Frog", "Cat"}, {"Dog", "Pig", "Frog", "Cat"}};

    private int[] answers = {1, 0, 0, 0, 3, -1};

    private int state = 0;
    private int max_question_num = 5;

    private Typeface normalFont;
    private Typeface boldFont;

    private int entered_input = -1;
    private int correct_answer = 0;
    private int score = 0; //100점 만점으로 점수 계산

    //퀴즈 결과 다이얼로그 텍스트뷰
    TextView quiz_result_here;
    TextView quiz_score_here;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);

        quiz_num1 = rootView.findViewById(R.id.question_1);
        quiz_num2 = rootView.findViewById(R.id.question_2);
        quiz_num3 = rootView.findViewById(R.id.question_3);
        quiz_num4 = rootView.findViewById(R.id.question_4);
        quiz_num5 = rootView.findViewById(R.id.question_5);
        quiz_nums = new ArrayList<>();
        quiz_nums.add(quiz_num1);
        quiz_nums.add(quiz_num2);
        quiz_nums.add(quiz_num3);
        quiz_nums.add(quiz_num4);
        quiz_nums.add(quiz_num5);

        // Dynamic size allocation by screen size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        quizBtnSize_normal = metrics.widthPixels * 12 / 100;
        quizBtnSize_current = metrics.widthPixels * 15 / 100;

        txtQuiz=rootView.findViewById(R.id.tv_question);
        gifQuiz=rootView.findViewById(R.id.gif_question);
        imgQuiz=rootView.findViewById(R.id.img_question);
        btnQuiz=rootView.findViewById(R.id.btn_question);
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mediaPlayer=MediaPlayer.create(getContext(),R.raw.cat_sound);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                });
            }
        });
        quizView=txtQuiz;
        quiz_out= AnimationUtils.loadAnimation(getContext(),R.anim.quiz_out);

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
                //오답 시 선택한 박스 진동
                if(entered_input!=answers[state]){
                    Animation shake =AnimationUtils.loadAnimation(getContext(),R.anim.shake);
                    answer1.startAnimation(shake);
                }
                changeQuestion();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 1;
                if(entered_input!=answers[state]){
                    Animation shake =AnimationUtils.loadAnimation(getContext(),R.anim.shake);
                    answer2.startAnimation(shake);
                }
                changeQuestion();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 2;
                if(entered_input!=answers[state]){
                    Animation shake =AnimationUtils.loadAnimation(getContext(),R.anim.shake);
                    answer3.startAnimation(shake);
                }
                changeQuestion();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_input = 3;
                if(entered_input!=answers[state]){
                    Animation shake =AnimationUtils.loadAnimation(getContext(),R.anim.shake);
                    answer4.startAnimation(shake);
                }
                changeQuestion();
            }
        });

        return rootView;
    }

    private void changeQuestion() {

        if(entered_input==answers[state]){
            correct_answer++;
            score += 20;
        }

        // 문제 교체
        quizView.startAnimation(quiz_out);
        previousQuestion=quizView;

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

        MediaPlayer mediaPlayer;
        if (answers[state] == entered_input) {
            // sound correct
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound_correct);
        } else {
            // sound wrong
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound_wrong);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        });

        //1초뒤 다음 문제로
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(state==0){
                    txtQuiz.setText(quiz_set[state+1]);
                }else if(state==1){
                    quizView.setVisibility(View.INVISIBLE);
                    quizView=gifQuiz;
                    GlideDrawableImageViewTarget gif_rocket=new GlideDrawableImageViewTarget(gifQuiz);
                    Glide.with(getActivity()).load(R.drawable.gif_rocket).into(gif_rocket);
                    quizView.setVisibility(View.VISIBLE);
                }else if(state==2){
                    quizView.setVisibility(View.INVISIBLE);
                    quizView=imgQuiz;
                    quizView.setVisibility(View.VISIBLE);
                }else if(state==3){
                    quizView.setVisibility(View.INVISIBLE);
                    quizView=btnQuiz;
                    quizView.setVisibility(View.VISIBLE);
                }

                // Quiz number changes
                quiz_nums.get(state).setBackgroundResource(R.drawable.btn_quiz_number);     // background
                quiz_nums.get(state).setTypeface(normalFont);                               //font
                ViewGroup.LayoutParams lp = quiz_nums.get(state).getLayoutParams();         //btn size
                lp.width = quizBtnSize_normal;
                lp.height = quizBtnSize_normal;
                quiz_nums.get(state).setLayoutParams(lp);

                if (state < max_question_num-1) {
                    quiz_nums.get(state + 1).setBackgroundResource(R.drawable.btn_quiz_number_current);
                    quiz_nums.get(state + 1).setTypeface(boldFont);
                    ViewGroup.LayoutParams lp2 = quiz_nums.get(state + 1).getLayoutParams();
                    lp2.width = quizBtnSize_current;
                    lp2.height = quizBtnSize_current;
                    quiz_nums.get(state + 1).setLayoutParams(lp2);
                }

                // Quiz questions change
                targetBtn.setBackgroundResource(R.drawable.btn_quiz_answer);
                targetBtn.setTypeface(normalFont);

                answer1.setText(questions[state][0]);
                answer2.setText(questions[state][1]);
                answer3.setText(questions[state][2]);
                answer4.setText(questions[state][3]);

                state++;
                if (state == max_question_num) {
                    //퀴즈 보고 나서 나무 성장 시키기 위한 변수
                    quiz_taken_int = 1;
                    Log.d("quiz taken", String.valueOf(quiz_taken_int));
                    //퀴즈 결과 다이얼로그
                    QuizResultDialog dialog = new QuizResultDialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_quiz_result);
                    dialog.show();
                    //점수와 맞춘 문제 개수 반영
                    quiz_result_here = (TextView)dialog.findViewById(R.id.quiz_result_here);
                    quiz_score_here = dialog.findViewById(R.id.quiz_score_here);
                    quiz_result_here.setText(correct_answer + "문제를 맞췄어요!");
                    quiz_score_here.setText(score + "점");
                    dialog.setDialogListener(new QuizResultDialog.CustomDialogListener() {
                        @Override
                        public void onPositiveClicked() {
                        }
                        @Override
                        public void onNegativeClicked() {
                        }
                    });

                }
            }
        }, 1000);
    }
}