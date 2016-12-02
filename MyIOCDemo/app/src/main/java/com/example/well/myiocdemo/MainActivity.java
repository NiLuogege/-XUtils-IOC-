package com.example.well.myiocdemo;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.well.myiocdemo.annotation.ContentView;
import com.example.well.myiocdemo.annotation.OnClick;
import com.example.well.myiocdemo.annotation.ViewById;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.btn)
    Button mButton;


    @OnClick(R.id.btn)
    public void btn(View view){
        Toast.makeText(this,"dddddd",Toast.LENGTH_LONG).show();
    }

    @Override
    public void initAfterView() {
    }
}
