package ru.mirea.bandurin.mireaproject.ui.gallery;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import ru.mirea.bandurin.mireaproject.MainActivity;
import ru.mirea.bandurin.mireaproject.R;
import ru.mirea.bandurin.mireaproject.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private TextView tv;
    final private int PermissionCode = 200;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv = binding.textView;
        binding.imageView.setImageResource(R.drawable.quu);
        if (ContextCompat.checkSelfPermission(getContext(), POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity.class.getSimpleName().toString(), "Разрешения получены"); }
        else {
            Log.d(MainActivity.class.getSimpleName().toString(), "Нет разрешений!");
            ActivityCompat.requestPermissions(getActivity(), new String[]{POST_NOTIFICATIONS, FOREGROUND_SERVICE}, PermissionCode);
        }
        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "start");
                Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
                ContextCompat.startForegroundService(getContext(), serviceIntent); }
        });
        binding.buttonStop.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v){
           getActivity().stopService(
                    new Intent(getActivity(), PlayerService.class));
        } });

        galleryViewModel.getText().observe(getViewLifecycleOwner(), tv::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}