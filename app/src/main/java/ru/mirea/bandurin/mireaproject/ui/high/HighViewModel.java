package ru.mirea.bandurin.mireaproject.ui.high;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HighViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HighViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is high fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}