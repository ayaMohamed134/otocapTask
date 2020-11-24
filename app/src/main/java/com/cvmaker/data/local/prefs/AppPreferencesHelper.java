package com.cvmaker.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.cvmaker.di.PreferenceInfo;
import com.cvmaker.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by aya mohamed on 08/02/18.
 */
@Singleton


public class AppPreferencesHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;
    private final String KEY_IS_LANG_SELECTED = "is_lang_selected";
    private final String KEY_DEVICE_ID = "device_id";
    private final String KEY_DEVICE_MODEL = "device_model";
    private final String PREF_KEY_LANG = "lang";
    private final String PREF_KEY_FCM_TOKEN = "fcm_token";
    private final Context context;


    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        this.context = context;
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getFCMToken() {
        String fcmToken = mPrefs.getString(PREF_KEY_FCM_TOKEN, AppConstants.STRING_NULL_INDEX);
        return fcmToken;
    }

    @Override
    public void setFCMToken(String fcmToken) {
        mPrefs.edit().putString(PREF_KEY_FCM_TOKEN, fcmToken).apply();
    }

    @Override
    public void setIsLangSelected(boolean isLangSelected) {
        mPrefs.edit().putBoolean(KEY_IS_LANG_SELECTED, isLangSelected).apply();
    }

    @Override
    public boolean getIsLangSelected() {
        boolean isLangSelected = mPrefs.getBoolean(KEY_IS_LANG_SELECTED, AppConstants.BOOLEAN_NULL_INDEX);
        return isLangSelected;
    }

    @Override
    public void setLang(String lang) {
        mPrefs.edit().putString(PREF_KEY_LANG, lang).apply();
    }

    @Override
    public String getLang() {
        String lang = mPrefs.getString(PREF_KEY_LANG, AppConstants.AR);
        return lang;
    }

    @Override
    public String getDeviceId() {
        String device_id = mPrefs.getString(KEY_DEVICE_ID, AppConstants.STRING_NULL_INDEX);
        return mPrefs.getString(KEY_DEVICE_ID, device_id);
    }

    @Override
    public void setDeviceId(String deviceId) {
        mPrefs.edit().putString(KEY_DEVICE_ID, deviceId).apply();
    }

    @Override
    public String getDeviceModel() {
        String deviceModel = mPrefs.getString(KEY_DEVICE_MODEL, AppConstants.STRING_NULL_INDEX);
        return mPrefs.getString(KEY_DEVICE_MODEL, deviceModel);
    }

    @Override
    public void setDeviceModel(String deviceModel) {
        mPrefs.edit().putString(KEY_DEVICE_MODEL, deviceModel).apply();
    }

}
