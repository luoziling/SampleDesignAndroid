package com.wzb.sampledesign.ui.modeldetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModelDetailViewModel extends ViewModel {

	private MutableLiveData<String> mText;

	public ModelDetailViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is ModelDetail fragment");
	}

	public LiveData<String> getText() {
		return mText;
	}
}