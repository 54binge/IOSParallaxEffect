package example.binge.com.iosparallaxeffect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity implements IGyroEventListener {
    public static final float TRANSFORM_FACTOR = 0.02f;
    private float mTransformFactor = TRANSFORM_FACTOR;
    private int mDefaultOffset;
    private ViewGroup.LayoutParams mLayoutParams;
    private int height;
    private int width;
    private View mView;
    private float mCurrentOffsetX;
    private float mCurrentOffsetY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ParallaxEffectHelper.getInstance().init(this);
        ParallaxEffectHelper.getInstance().setGyroEventListener(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParallaxEffectHelper.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ParallaxEffectHelper.getInstance().onPause();
    }

    private void initView() {
        mView = findViewById(R.id.target_iv);
        mDefaultOffset = getResources().getDimensionPixelSize(R.dimen.default_img_offset);
        mView.setX(-mDefaultOffset);
        mView.setY(-mDefaultOffset);
        mLayoutParams = mView.getLayoutParams();
        mView.post(new Runnable() {
            @Override
            public void run() {
                height = mView.getHeight();
                width = mView.getWidth();

                mLayoutParams.height = height + mDefaultOffset * 2;
                mLayoutParams.width = width + mDefaultOffset * 2;
                mView.setLayoutParams(mLayoutParams);
            }
        });
    }

    @Override
    public void onGyroScopeChange(float x, float y) {

        mCurrentOffsetX += mDefaultOffset * x * mTransformFactor;
        mCurrentOffsetY += mDefaultOffset * y * mTransformFactor;

        if (Math.abs(mCurrentOffsetX) > mDefaultOffset){
            mCurrentOffsetX = mCurrentOffsetX < 0 ? -mDefaultOffset : mDefaultOffset;
        }

        if (Math.abs(mCurrentOffsetY) > mDefaultOffset){
            mCurrentOffsetY = mCurrentOffsetY < 0 ? -mDefaultOffset : mDefaultOffset;
        }

        mView.setX((int) mCurrentOffsetX - mDefaultOffset);
        mView.setY((int) mCurrentOffsetY - mDefaultOffset);
    }


}
