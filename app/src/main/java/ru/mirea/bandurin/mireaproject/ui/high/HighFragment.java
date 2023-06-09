package ru.mirea.bandurin.mireaproject.ui.high;

import static android.content.ContentValues.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.mirea.bandurin.mireaproject.R;
import ru.mirea.bandurin.mireaproject.databinding.FragmentHighBinding;
import ru.mirea.bandurin.mireaproject.databinding.FragmentProfileBinding;
import ru.mirea.bandurin.mireaproject.ui.home.HomeViewModel;
import ru.mirea.bandurin.mireaproject.ui.profile.ProfileViewModel;

public class HighFragment extends Fragment implements SensorEventListener {

    private FragmentHighBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private TextView tv;
    private float pres;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HighViewModel highViewModel =
                new ViewModelProvider(this).get(HighViewModel.class);

        binding = FragmentHighBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv = binding.textHigh;
        highViewModel.getText().observe(getViewLifecycleOwner(), tv::setText);
        sensorManager =
                (SensorManager)requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_PRESSURE);
        return root;
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: ");
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pres = event.values[0];
            tv.setText(String.valueOf(12 *(1013-pres)/1.33));
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}