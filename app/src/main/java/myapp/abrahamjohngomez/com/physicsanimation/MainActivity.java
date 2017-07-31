package myapp.abrahamjohngomez.com.physicsanimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button translateSpringAnimBtn = (Button) findViewById(R.id.btn_translate_spring);
        translateSpringAnimBtn.setOnClickListener(this);

        Button flingAnimationBtn = (Button) findViewById(R.id.btn_translate_fling);
        flingAnimationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        if(id == R.id.btn_translate_spring) {
            intent = new Intent(this, SpringAnimationActivity.class);
            startActivity(intent);
        }
        else {
            intent = new Intent(this, FlingAnimationActivity.class);
            startActivity(intent);
        }

    }
}
