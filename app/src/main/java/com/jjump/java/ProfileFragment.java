package com.jjump.java;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jjump.R;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements CircleProgressBar.ProgressFormatter{

    ImageButton btn_contact;
    CircleProgressBar circleProgressBar1;
    BarChart bar_chart;
    TextView total_word_number;
    TextView tv_child_name;
    TextView tv_greeting;
    ImageView iv_profile_picture;
    String child_name;
    String name;
    int pass_character_id = 1;
    private static final String DEFAULT_PATTERN = "%d%%";
    private int studyRate = 0;
    private int totalWordCount = 12;

    // bar chart data
    ArrayList<Integer> wordcountList = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList = new ArrayList<>(); // ArrayList 선언


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        btn_contact = rootView.findViewById(R.id.btn_contact);
        circleProgressBar1 = rootView.findViewById(R.id.circle_bar1);
        bar_chart = rootView.findViewById(R.id.bar_chart);
        total_word_number = rootView.findViewById(R.id.total_word_number);
        tv_child_name = rootView.findViewById(R.id.tv_child_name);
        tv_greeting = rootView.findViewById(R.id.tv_greeting);
        iv_profile_picture = rootView.findViewById(R.id.iv_profile_picture);

        //bar chart setting
        graphInitSetting();       //그래프 기본 세팅

        BarChartGraph(labelList, wordcountList);
        bar_chart.getAxisLeft().setDrawAxisLine(false);
        bar_chart.getAxisLeft().setDrawLabels(false);
        bar_chart.getAxisLeft().setDrawGridLines(false);

        bar_chart.getAxisRight().setDrawAxisLine(false);
        bar_chart.getAxisRight().setDrawLabels(false);
        bar_chart.getAxisRight().setDrawGridLines(false);

        bar_chart.getXAxis().setDrawAxisLine(false);
        bar_chart.getXAxis().setDrawGridLines(false);
        bar_chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            name = acct.getDisplayName();
        }
        tv_child_name.setText(name + "님, \n아이의 이름을 알려주세요!");
        //문의하기
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

        // get 학습률, save to studyRate and update progress bar
        studyRate = 65;
        circleProgressBar1.setProgress(studyRate);

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
                        tv_child_name.setText(child_name +",");
                        tv_child_name.setTextSize(20);
                        tv_greeting.setVisibility(View.VISIBLE);
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

    public void graphInitSetting(){

        labelList.add("일");
        labelList.add("월");
        labelList.add("화");
        labelList.add("수");
        labelList.add("목");
        labelList.add("금");
        labelList.add("토");

        wordcountList.add(4);
        wordcountList.add(9);
        wordcountList.add(7);
        wordcountList.add(10);
        wordcountList.add(13);
        wordcountList.add(6);
        wordcountList.add(3);

        BarChartGraph(labelList, wordcountList);
    }

    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {
        // BarChart 메소드
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < valList.size(); i++) {
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "학습한 단어 수"); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        depenses.setValueTextSize(11f);
        depenses.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (String.valueOf((int)value)) + "개";
            }
        });
        bar_chart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < labelList.size(); i++) {
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels, depenses);
        depenses.setColors(ColorTemplate.JOYFUL_COLORS);

        bar_chart.setData(data);
        bar_chart.animateXY(1000, 1000);
        bar_chart.invalidate();
    }

    @Override
    public CharSequence format(int progress, int max) {
        return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
    }
}