package com.huriati.project.tvkabel.ui.gaji;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GajiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GajiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Gaji Bulan Lalu");
    }

    public LiveData<String> getText() {
        return mText;
    }
}