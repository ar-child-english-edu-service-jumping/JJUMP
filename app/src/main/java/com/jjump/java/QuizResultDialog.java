package com.jjump.java;

import androidx.annotation.NonNull;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.jjump.R;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class QuizResultDialog extends Dialog implements View.OnClickListener{

    private ImageButton btn_close;
    private QuizResultDialog.CustomDialogListener customDialogListener;
    Context mContext;

    interface CustomDialogListener{
        void onPositiveClicked();
        void onNegativeClicked();
    }
    public void setDialogListener(QuizResultDialog.CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }


    public QuizResultDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quiz_result);
        KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfettiView.build()
                        .addColors(Color.parseColor("#ec7b73"), Color.parseColor("#ae8dea"), Color.parseColor("#f7d35c"),Color.parseColor("#f0628d"))
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(1000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12,4f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .stream(300, 1000L);
            }
        });

        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close:
                dismiss();
                Intent intent = new Intent(mContext,QuizActivity.class);
                ((QuizActivity)mContext).finish();
                break;
        }

    }
}