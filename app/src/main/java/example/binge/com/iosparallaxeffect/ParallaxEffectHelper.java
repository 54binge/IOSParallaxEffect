package example.binge.com.iosparallaxeffect;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Administrator on 2017/7/5.
 */

public class ParallaxEffectHelper implements SensorEventListener {
    private static ParallaxEffectHelper mEffectHelper;
    private SensorManager mSensorManager;
    private Sensor mGyroscopeSensor;
    private IGyroEventListener mGyroEventListener;

    public static ParallaxEffectHelper getInstance() {
        if (mEffectHelper == null) {
            mEffectHelper = new ParallaxEffectHelper();
        }
        return mEffectHelper;
    }

    protected void init(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager == null) {
            return;
        }
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    protected void onResume() {
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mGyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    protected void onPause() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mGyroscopeSensor);
        }
    }

    protected void setGyroEventListener(IGyroEventListener gyroEventListener){
        mGyroEventListener = gyroEventListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(mGyroEventListener != null){
            mGyroEventListener.onGyroScopeChange(sensorEvent.values[SensorManager.DATA_Y], sensorEvent.values[SensorManager.DATA_X]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
