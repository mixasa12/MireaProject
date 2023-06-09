package ru.mirea.bandurin.mireaproject.ui.zaved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZavedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ZavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Zaved fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}