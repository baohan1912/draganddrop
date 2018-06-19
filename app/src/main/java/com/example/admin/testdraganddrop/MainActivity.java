package com.example.admin.testdraganddrop;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admin.testdraganddrop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        activityMainBinding.img.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ClipData item = ClipData.newPlainText("","");
                    String[] type = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(item,myShadow,v,0);
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        activityMainBinding.layout2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_ENTERED:{
                        v.setBackgroundColor(Color.BLUE);
                    }break;
                    case DragEvent.ACTION_DRAG_EXITED:{
                        v.setBackgroundColor(Color.RED);
                    }break;
                    case DragEvent.ACTION_DROP:{
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        LinearLayout container = (LinearLayout) v;
                        container.addView(view);
                    }break;
                }
                return true;
            }
        });
        activityMainBinding.layout1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_ENTERED:{
                        v.setBackgroundColor(Color.BLUE);
                    }break;
                    case DragEvent.ACTION_DRAG_EXITED:{
                        v.setBackgroundColor(Color.RED);
                    }break;
                    case DragEvent.ACTION_DROP:{
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        LinearLayout container = (LinearLayout) v;
                        container.addView(view);
                    }break;
                }
                return true;
            }
        });
    }
}
