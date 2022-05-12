package com.jjump.java;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jjump.R;

public class CharacterDialog extends Dialog implements View.OnClickListener {


    private Button btn_confirm_character;
    private Button btn_lion,btn_elephant,btn_cat;
    private Context context;
    int character_id = 0;
    int pass_character_id = 0;

    private CharacterDialog.CustomDialogListener customDialogListener;

    Button[] btnNums = new Button[9];
    int[] btnNumsIds = {R.id.btn_lion,R.id.btn_elephant,R.id.btn_cat,R.id.btn_bear,R.id.btn_rabbit,
            R.id.btn_penguin,R.id.btn_deer,R.id.btn_racoon, R.id.btn_pig };

    public CharacterDialog(@NonNull Context context) {
        super(context);
        //this.context = context;
    }

    interface CustomDialogListener{
        void onPositiveClicked(int character_id);
        void onNegativeClicked();
    }
    public void setDialogListener(CharacterDialog.CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_dialog);

        for(int i=0; i < btnNumsIds.length; i++){
            btnNums[i] = findViewById(btnNumsIds[i]);
        }
        for(int i = 0; i < btnNumsIds.length; i++){
            final int index = i;

            btnNums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    character_id = index;
                    //그리드 위치 별 인덱스 저장됨 확인 완료
                    Log.d("id", String.valueOf(character_id));
                    ImageView ic_finger = findViewById(R.id.ic_finger);
                    GlideDrawableImageViewTarget gif_finger = new GlideDrawableImageViewTarget(ic_finger);
                    Glide.with(getContext()).load(R.drawable.gif_finger).into(gif_finger);

                    if (character_id == 0){
                        ic_finger.setVisibility(View.VISIBLE);
                    }else if (character_id == 1) {
                        ic_finger.setVisibility(View.VISIBLE);
                    }else if (character_id == 2) {
                        ic_finger.setVisibility(View.VISIBLE);
                    }else if (character_id == 3) {
                        Toast.makeText(getContext(), "퀴즈를 10번 풀어 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }else if(character_id == 4){
                        Toast.makeText(getContext(), "퀴즈를 100점 맞아서 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }else if(character_id == 5){
                        Toast.makeText(getContext(), "단어를 20개 추가해 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }else if(character_id == 6){
                        Toast.makeText(getContext(), "나무를 2단계로 성장시켜 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }else if(character_id == 7){
                        Toast.makeText(getContext(), "나무를 3단계로 성장시켜 잠겨있는 동물을 획득해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }else if(character_id == 8){
                        Toast.makeText(getContext(), "잠겨있는 동물을 획득하려면 단어를 50개 추가해요!", Toast.LENGTH_SHORT).show();
                        ic_finger.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

        btn_confirm_character = findViewById(R.id.btn_confirm_character);
        btn_lion = findViewById(R.id.btn_lion);
        btn_elephant = findViewById(R.id.btn_elephant);
        btn_cat = findViewById(R.id.btn_cat);
        //버튼 클릭 리스너 등록
        btn_confirm_character.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_character:
                pass_character_id = character_id;
                customDialogListener.onPositiveClicked(pass_character_id);
                dismiss();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }

    }


}