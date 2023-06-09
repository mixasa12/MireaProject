package ru.mirea.bandurin.mireaproject.ui.micro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.bandurin.mireaproject.databinding.FragmentMicroBinding;
import ru.mirea.bandurin.mireaproject.databinding.FragmentSlideshowBinding;
import ru.mirea.bandurin.mireaproject.ui.slideshow.SlideshowViewModel;

public class MicroFragment extends Fragment {

    private FragmentMicroBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MicroViewModel slideshowViewModel =
                new ViewModelProvider(this).get(MicroViewModel.class);

        binding = FragmentMicroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMicro;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}