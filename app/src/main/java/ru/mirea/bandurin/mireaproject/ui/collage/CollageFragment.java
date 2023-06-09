package ru.mirea.bandurin.mireaproject.ui.collage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.bandurin.mireaproject.databinding.FragmentCollageBinding;
import ru.mirea.bandurin.mireaproject.databinding.FragmentSlideshowBinding;
import ru.mirea.bandurin.mireaproject.ui.slideshow.SlideshowViewModel;

public class CollageFragment extends Fragment {

    private FragmentCollageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CollageViewModel collageViewModel =
                new ViewModelProvider(this).get(CollageViewModel.class);

        binding = FragmentCollageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCollage;
        collageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}