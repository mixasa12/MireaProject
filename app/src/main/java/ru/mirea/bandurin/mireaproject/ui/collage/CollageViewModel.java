package ru.mirea.bandurin.mireaproject.ui.collage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CollageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CollageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is collage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}