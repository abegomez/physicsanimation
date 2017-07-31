package myapp.abrahamjohngomez.com.physicsanimation;

import android.os.Bundle;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ryuhyoko on 7/31/2017.
 */

public class SpringAnimationActivity extends AppCompatActivity {
    private static final String TAG = SpringAnimationActivity.class.getSimpleName();

    private SpringAnimation mSpringTranslationXAnimation;
    private SpringAnimation mSpringTranslationYAnimation;

    private ImageView mViewToBeAnimated;
    private float mXDiffInTouchPointAndViewTopLeftCorner;
    private float mYDiffInTouchPointAndViewTopLeftCorner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spring_animation_activity);

        mViewToBeAnimated = (ImageView) findViewById(R.id.iv_translate_spring);
        mViewToBeAnimated.setOnTouchListener(onTouchListener);

        mSpringTranslationXAnimation = new SpringAnimation(mViewToBeAnimated, new FloatPropertyCompat<View>("translationX") {
            @Override
            public float getValue(View view) {
                return view.getTranslationX();
            }

            @Override
            public void setValue(View view, float value) {
                view.setTranslationX(value);
            }
        });

        SpringForce springForceX = new SpringForce(0f);
        springForceX.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        mSpringTranslationXAnimation.setSpring(springForceX);

        mSpringTranslationYAnimation = new SpringAnimation(mViewToBeAnimated, new FloatPropertyCompat<View>("translationY") {
            @Override
            public float getValue(View view) {
                return view.getTranslationY();
            }

            @Override
            public void setValue(View view, float value) {
                view.setTranslationY(value);
            }
        });

        SpringForce springForceY = new SpringForce(0f);
        springForceY.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForceY.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        mSpringTranslationYAnimation.setSpring(springForceY);
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mXDiffInTouchPointAndViewTopLeftCorner = event.getRawX() - v.getX();
                    mYDiffInTouchPointAndViewTopLeftCorner = event.getRawY() - v.getY();
                    mSpringTranslationXAnimation.cancel();
                    mSpringTranslationYAnimation.cancel();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newTopLeftX = event.getRawX() - mXDiffInTouchPointAndViewTopLeftCorner;
                    float newTopLeftY = event.getRawY() - mYDiffInTouchPointAndViewTopLeftCorner;
                    mViewToBeAnimated.setX(newTopLeftX);
                    mViewToBeAnimated.setY(newTopLeftY);
                    break;
                case MotionEvent.ACTION_UP:
                    mSpringTranslationXAnimation.start();
                    mSpringTranslationYAnimation.start();
                    break;
            }
            return true;
        }
    };
}
