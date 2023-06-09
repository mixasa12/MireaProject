package ru.mirea.bandurin.mireaproject.ui.micro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MicroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MicroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Micro fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}