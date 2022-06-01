/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jjump.java.textdetector;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.jjump.java.CameraXLivePreviewActivity;
import com.jjump.java.GraphicOverlay;
import com.jjump.java.GraphicOverlay.Graphic;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.Text.Element;
import com.google.mlkit.vision.text.Text.Line;
import com.google.mlkit.vision.text.Text.TextBlock;
import com.jjump.java.HomeActivity;
import com.jjump.java.data.RequestDto;
import com.jjump.java.data.ResponseDto;
import com.jjump.java.network.ApiInterface;
import com.jjump.java.network.HttpClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class TextGraphic extends Graphic {

  private static final String TAG = "TextGraphic";
  private static final String TEXT_WITH_LANGUAGE_TAG_FORMAT = "%s:%s";

  private static final int TEXT_COLOR = Color.BLACK;
  private static final int MARKER_COLOR = Color.WHITE;
  private static final float TEXT_SIZE = 54.0f;
  private static final float STROKE_WIDTH = 4.0f;

  private final Paint rectPaint;
  private final Paint textPaint;
  private final Paint labelPaint;
  private final Text text;
  private final Boolean shouldGroupTextInBlocks;
  private final Boolean showLanguageTag;

  public final int maxWordNum = 15;                 // Set maximum word list number shown in screen


  private ApiInterface api;
  String new_word;
  String email;


  TextGraphic(
          GraphicOverlay overlay, Text text, boolean shouldGroupTextInBlocks, boolean showLanguageTag) {
    super(overlay);

    this.text = text;
    this.shouldGroupTextInBlocks = shouldGroupTextInBlocks;

    this.showLanguageTag = showLanguageTag;

    rectPaint = new Paint();
    rectPaint.setColor(MARKER_COLOR);
    rectPaint.setStyle(Paint.Style.STROKE);
    rectPaint.setStrokeWidth(STROKE_WIDTH);

    textPaint = new Paint();
    textPaint.setColor(TEXT_COLOR);
    textPaint.setTextSize(TEXT_SIZE);

    labelPaint = new Paint();
    labelPaint.setColor(MARKER_COLOR);
    labelPaint.setStyle(Paint.Style.FILL);
    // Redraw the overlay, as this graphic has been added.
    postInvalidate();
  }

  /**
   * Draws the text block annotations for position, size, and raw value on the supplied canvas.
   */
  @Override
  public void draw(Canvas canvas) {
    Log.d(TAG, "Text is: " + text.getText());

    HomeActivity.endTime = System.currentTimeMillis();
    if (HomeActivity.endTime - HomeActivity.startTime >= 2000) {
      HomeActivity.startTime = HomeActivity.endTime;
      for (TextBlock textBlock : text.getTextBlocks()) {
        // Renders the text at the bottom of the box.
//      Log.d(TAG, "TextBlock text is: " + textBlock.getText());
//      Log.d(TAG, "TextBlock boundingbox is: " + textBlock.getBoundingBox());
//      Log.d(TAG, "TextBlock cornerpoint is: " + Arrays.toString(textBlock.getCornerPoints()));
        if (shouldGroupTextInBlocks) {
          String text =
                  showLanguageTag
                          ? String.format(
                          TEXT_WITH_LANGUAGE_TAG_FORMAT,
                          textBlock.getRecognizedLanguage(),
                          textBlock.getText())
                          : textBlock.getText();
          drawText(
                  text,
                  new RectF(textBlock.getBoundingBox()),
                  TEXT_SIZE * textBlock.getLines().size() + 2 * STROKE_WIDTH,
                  canvas);
        } else {
          for (Line line : textBlock.getLines()) {
//          Log.d(TAG, "Line text is: " + line.getText());
//          Log.d(TAG, "Line boundingbox is: " + line.getBoundingBox());
//          Log.d(TAG, "Line cornerpoint is: " + Arrays.toString(line.getCornerPoints()));
            String text =
                    showLanguageTag
                            ? String.format(
                            TEXT_WITH_LANGUAGE_TAG_FORMAT, line.getRecognizedLanguage(), line.getText())
                            : line.getText();
            drawText(text, new RectF(line.getBoundingBox()), TEXT_SIZE + 2 * STROKE_WIDTH, canvas);

            for (Element element : line.getElements()) {
              Log.d(TAG, "Element text is: " + element.getText());
//            Log.d(TAG, "Element boundingbox is: " + element.getBoundingBox());
//            Log.d(TAG, "Element cornerpoint is: " + Arrays.toString(element.getCornerPoints()));
//            Log.d(TAG, "Element language is: " + element.getRecognizedLanguage());

              //overlay.overlayarrayList.add(element.getText());

              String temp=element.getText();
              temp.replace(",","");
              temp.replace(" ","");
              temp.replace(".","");
              temp.replace("'","");
              temp.replace("\"","");
              temp.replace("?","");
              temp.replace("!","");

              temp=temp.substring(0,1).toUpperCase()+temp.substring(1,temp.length());

              ///////////// insert text element in text container/////////////
              if (HomeActivity.tempDB.contains(temp)) {
                if (!HomeActivity.textContainer.contains(temp)) {     //only for the new word
                  if (HomeActivity.textContainer.size() >= maxWordNum)               //if the place for new word not exists
                    HomeActivity.textContainer.remove(maxWordNum - 1);
                  HomeActivity.textContainer.add(0, temp);
                  new_word = temp;
                }
              }

            }
          }
        }
      }
      CameraXLivePreviewActivity.textAdapter.notifyDataSetChanged();
      Log.d("Test Container", HomeActivity.textContainer.toString());
    }


    //overlay.adapter.notifyDataSetChanged();
  }



  private void drawText(String text, RectF rect, float textHeight, Canvas canvas) {
    // If the image is flipped, the left will be translated to right, and the right to left.
    float x0 = translateX(rect.left);
    float x1 = translateX(rect.right);
    rect.left = min(x0, x1);
    rect.right = max(x0, x1);
    rect.top = translateY(rect.top);
    rect.bottom = translateY(rect.bottom);
    canvas.drawRect(rect, rectPaint);
//    float textWidth = textPaint.measureText(text);
//    canvas.drawRect(
//        rect.left - STROKE_WIDTH,
//        rect.top - textHeight,
//        rect.left + textWidth + 2 * STROKE_WIDTH,
//        rect.top,
//        labelPaint);
    // Renders the text at the bottom of the box.
//    canvas.drawText(text, rect.left, rect.top - STROKE_WIDTH, textPaint);
  }
}
