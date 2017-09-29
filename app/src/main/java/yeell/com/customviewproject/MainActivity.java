package yeell.com.customviewproject;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ButtonCustomView custom = (ButtonCustomView) findViewById(R.id.custom);

                final ObjectAnimator object = ObjectAnimator.ofInt(custom, "Radius", 0, 100);
                object.setDuration(1500);
                ObjectAnimator translateAnimate = ObjectAnimator.ofFloat(custom, "translationY", 0, -300);
                translateAnimate.setDuration(1500);
                ObjectAnimator alphaAnimate = ObjectAnimator.ofFloat(custom, "TextAlpha", 1, 0);
                alphaAnimate.setDuration(1500);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(object).with(alphaAnimate).before(translateAnimate);
                animatorSet.start();

                translateAnimate.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        custom.setStartDrawPath(true);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(custom, "OkWidthProgress", 0, 100);
                        objectAnimator.setInterpolator(new AnticipateOvershootInterpolator());
                        objectAnimator.setDuration(1500);
                        objectAnimator.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }
}
