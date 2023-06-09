package ru.mirea.bandurin.mireaproject.ui.filechanger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FileChangerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FileChangerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FileChanger fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}