package com.jjump.java;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import com.jjump.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        //camera button
        ImageButton cameraBtn =  rootView.findViewById(R.id.btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CameraXLivePreviewActivity.class);
                startActivity(intent);
            }
        });

        // 나무 성장 애니메이션 gif
        ImageView ic_tree1 = rootView.findViewById(R.id.ic_tree1);
        ImageView ic_tree2 = rootView.findViewById(R.id.ic_tree2);
        ImageView ic_tree3 = rootView.findViewById(R.id.ic_tree3);
        // 기준 별 visibility 설정하기
        ic_tree1.setVisibility(View.INVISIBLE);
        ic_tree2.setVisibility(View.INVISIBLE);
        ic_tree3.setVisibility(View.VISIBLE);
        // Glide 이용하여 gif 띄우기
        GlideDrawableImageViewTarget gif_tree1 = new GlideDrawableImageViewTarget(ic_tree1);
        GlideDrawableImageViewTarget gif_tree2 = new GlideDrawableImageViewTarget(ic_tree2);
        GlideDrawableImageViewTarget gif_tree3 = new GlideDrawableImageViewTarget(ic_tree3);
        Glide.with(getActivity()).load(R.drawable.gif_tree1).into(gif_tree1);
        Glide.with(getActivity()).load(R.drawable.gif_tree2).into(gif_tree2);
        Glide.with(getActivity()).load(R.drawable.gif_tree3).into(gif_tree3);

        return rootView;
    }
}