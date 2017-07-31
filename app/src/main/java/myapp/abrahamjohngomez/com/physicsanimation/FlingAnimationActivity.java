package myapp.abrahamjohngomez.com.physicsanimation;

import android.os.Build;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by ryuhyoko on 7/31/2017.
 */
 //push
public class FlingAnimationActivity extends AppCompatActivity {
    private static final String TAG = FlingAnimationActivity.class.getSimpleName();

    private ImageView mViewToBeFlung;
    private TextView mTvFlingDistance;
    private RelativeLayout mMainLayout;
    private int maxTranslationX;
    private int maxTranslationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fling_animation_activity);

        mViewToBeFlung = (ImageView) findViewById(R.id.iv_translate_fling);
        mTvFlingDistance = (TextView) findViewById(R.id.tv_fling_distance);
        mMainLayout = (RelativeLayout) findViewById(R.id.main_layout);

        final GestureDetector gestureDetector = new GestureDetector(this, mGestureListener);

        mViewToBeFlung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        mMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                maxTranslationX = mMainLayout.getWidth() - mViewToBeFlung.getWidth();
                maxTranslationY = mMainLayout.getHeight() - mViewToBeFlung.getHeight();
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    mMainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    mMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        private static final int MIN_DISTANCE_MOVED = 50;
        private static final float MIN_TRANSLATION = 0;
        private static final float FRICTION = 1.1f;

        @Override
        public boolean onDown(MotionEvent arg0) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
            //downEvent : when user puts finger down on view
            //moveEvent : when user lifts finger at end of movement
            float distanceInX = Math.abs(moveEvent.getRawX() - downEvent.getRawX());
            float distanceInY = Math.abs(moveEvent.getRawY() - downEvent.getRawY());

            mTvFlingDistance.setText("distanceInX : " + distanceInX + "\n" + "distanceInY : " + distanceInY);

            if (distanceInX > MIN_DISTANCE_MOVED) {
                //Fling Right/Left
                FlingAnimation flingX = new FlingAnimation(mViewToBeFlung, DynamicAnimation.TRANSLATION_X);
                flingX.setStartVelocity(velocityX)
                        .setMinValue(MIN_TRANSLATION)
                        .setMaxValue(maxTranslationX)
                        .setFriction(FRICTION)
                        .start();

            } else if (distanceInY > MIN_DISTANCE_MOVED) {
                //Fling Up/Down
                FlingAnimation flingY = new FlingAnimation(mViewToBeFlung, DynamicAnimation.TRANSLATION_Y);
                flingY.setStartVelocity(velocityY)
                        .setMinValue(MIN_TRANSLATION)
                        .setMaxValue(maxTranslationY)
                        .setFriction(FRICTION)
                        .start();
            }
            return true;
        }
    };
}
