package com.example.paintapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.slider.RangeSlider;
import com.mtjin.library.DrawView;

import dev.sasikanth.colorsheet.ColorSheet;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout container;
    MaterialCardView color_picker;

    private Integer selectedColor = ColorSheet.NO_COLOR;
    private Boolean noColorOption = false;

    Function1 listener = null;
    DrawView drawView;

    MaterialCardView clear;
    MaterialCardView undo;
    MaterialCardView redo;

    RangeSlider rangeSlider;
    MaterialCardView brush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ColorSheet colorSheet = new ColorSheet();
        container = findViewById(R.id.layout_container);
        color_picker = findViewById(R.id.color_picker);
        final int[]  colors = getResources().getIntArray(R.array.colors);

        drawView = findViewById(R.id.drawView);
        clear = findViewById(R.id.clearCanvas);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);

        rangeSlider = findViewById(R.id.rangeSlider);
        brush = findViewById(R.id.brush);

        brush.setOnClickListener(v -> {
            if(rangeSlider.getVisibility() == View.VISIBLE){
                rangeSlider.setVisibility(View.GONE);

            }else{
                rangeSlider.setVisibility(View.VISIBLE);
            }


        });
        listener = (new Function1() {
            public Object invoke(Object var1) {
                this.invoke(((Number)var1).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int color) {
                container.setBackgroundColor(color);
                selectedColor = color;
                drawView.setPenColor(color);
                Toast.makeText(MainActivity.this, "Color: " + color, Toast.LENGTH_SHORT).show();
            }
        });

        color_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSheet.cornerRadius(8);
                colorSheet.colorPicker(colors, selectedColor, noColorOption, listener).show(getSupportFragmentManager());
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.clear();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.undo();
            }
        });

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.redo();
            }
        });

    }

}