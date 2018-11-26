package com.skogen.coin.api;

import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import com.skogen.coin.App;
import com.skogen.coin.R;
import com.skogen.coin.skeleton.presentation.BaseView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public abstract class DefaultCallback<T> implements Callback<T> {

    private WeakReference<BaseView> mViewRef;

    public DefaultCallback() {
    }

    public DefaultCallback(BaseView mViewRef) {
        this.mViewRef = new WeakReference<>(mViewRef);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        handleServerError(response);
        onResponse(response);
    }

    public abstract void onResponse(Response<T> response);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (mViewRef != null && mViewRef.get() != null) {
            mViewRef.get().hideProgressView();
        }
        //output in dialog
        String message;
        if (t instanceof MalformedJsonException)
            message = getString(R.string.errorApi500_503);
        else if (t instanceof IOException)
            message = getString(R.string.errorApiNoInternet);
        else message = t.getMessage();
        showDialog(message);
        //log output
        try {
            Timber.e(t, "onFailure: %s", call.request().url().toString());
        } catch (Exception e) {
            Timber.e(t, "onFailure: ");
        }
    }

    private void handleServerError(Response<T> response) {
        Timber.d("handleServerError -> %s", response.code());
        switch (response.code()) {
            case HttpURLConnection.HTTP_UNAVAILABLE:
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                showDialog(getString(R.string.errorApi500_503));
                break;
            default: {
                String errorMessage = generateMeaningfulMessage(response);
                showDialog(errorMessage);
                Timber.i("handleServerError: %s", errorMessage);
            }
        }
    }

    private void showDialog(String message) {
        if (mViewRef == null || mViewRef.get() == null) {
            Timber.i("showDialog: mViewRef == NULL");
            return;
        }
        if (TextUtils.isEmpty(message)) {
            Timber.i("showDialog: message is EMPTY");
            return;
        }
        try {
            mViewRef.get().hideProgressView();
            Toast.makeText(App.Companion.getContext(), message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateMeaningfulMessage(Response<T> response) {
        try {
            String message = response.errorBody() != null ? response.errorBody().string() : "";
            if (!TextUtils.isEmpty(message)) {
                BaseError error = new Gson().fromJson(message, BaseError.class);
                if (error != null) return error.getMessage();
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getString(@StringRes int id) {
        return App.Companion.getContext().getString(id);
    }
}
