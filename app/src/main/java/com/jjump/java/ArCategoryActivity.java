package com.jjump.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.jjump.R;
import com.jjump.java.adapter.TextAdapter;

import java.util.ArrayList;

// Shows multiple Ar models included in selected category
public class ArCategoryActivity extends AppCompatActivity {

    private static final String TAG = "arError";
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable modelWolf;
    private ModelRenderable modelBird;
    private ModelRenderable modelDog;
    private ModelRenderable modelCat;
    private ModelRenderable modelDeer;
    private ModelRenderable modelFrog;

    private ModelRenderable selectedModel;      // selected model

    private MediaPlayer mediaPlayer;            // model sound

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_category);

        if (!checkIsSupportedDeviceOrFinish(ArCategoryActivity.this)) {
            return;
        }

        // Shows model candidates with recycler view
        // set text adapter
        RecyclerView recyclerView=findViewById(R.id.textContainer_ar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<String> animal_category=new ArrayList<>();
        animal_category.add("Dolphin"); animal_category.add("Dog"); animal_category.add("Cat"); animal_category.add("Bird"); animal_category.add("Wolf"); animal_category.add("Deer"); animal_category.add("Elephant");
        animal_category.add("Rabbit"); animal_category.add("Kangaroo"); animal_category.add("Frog");
        TextAdapter textAdapter=new TextAdapter(animal_category);
        recyclerView.setAdapter(textAdapter);

        // math up each model when selected
        textAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String animal=animal_category.get(position);
                if(animal.equals("Dog")){
                    selectedModel=modelDog;
                    mediaPlayer=MediaPlayer.create(ArCategoryActivity.this,R.raw.dog_sound);
                }
                else if(animal.equals("Cat")){
                    selectedModel=modelCat;
                }
                else if(animal.equals("Bird")){
                    selectedModel=modelBird;
                }
                else if(animal.equals("Deer")) {
                    selectedModel = modelDeer;
                    mediaPlayer=MediaPlayer.create(ArCategoryActivity.this,R.raw.deer_sound);
                }
                else if(animal.equals("Frog")) {
                    selectedModel = modelFrog;
                    mediaPlayer=MediaPlayer.create(ArCategoryActivity.this,R.raw.frog_sound);
                }
                else{
                    selectedModel=modelWolf;
                    mediaPlayer=MediaPlayer.create(ArCategoryActivity.this,R.raw.wolf_sound);
                }
            }
        });

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        //Build models

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.bird)

                .build()
                .thenAccept(renderable -> modelBird = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.wolf)

                .build()
                .thenAccept(renderable -> modelWolf = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.dog)

                .build()
                .thenAccept(renderable -> modelDog = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.cat)

                .build()
                .thenAccept(renderable -> modelCat = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.deer)

                .build()
                .thenAccept(renderable -> modelDeer = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(getApplicationContext(), R.raw.frog)

                .build()
                .thenAccept(renderable -> modelFrog = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(), "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        //when anchor ready, screen touched
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (modelFrog == null || modelDeer == null || modelDog == null) {
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                    if(selectedModel.equals(modelWolf)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelWolf);
                    }
                    else if(selectedModel.equals(modelBird)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelBird);
                    }
                    else if(selectedModel.equals(modelCat)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelCat);
                    }
                    else if(selectedModel.equals(modelDog)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelDog);
                    }
                    else if(selectedModel.equals(modelDeer)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelDeer);
                    }
                    else if(selectedModel.equals(modelFrog)){
                        andy.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                        andy.setParent(anchorNode);
                        andy.setRenderable(modelFrog);
                    }
                    andy.select();

                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                    });
                });
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, openGlVersionString);
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}