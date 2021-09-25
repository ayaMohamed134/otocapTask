package com.otocabTask.data.local.prefs;


/**
 * Created by aya mohamed on 08/02/18.
 */

public interface PreferencesHelper {

    String getFCMToken();
    void setFCMToken(String fcm_token);


    void setIsLangSelected(boolean isLangSelected);
    boolean getIsLangSelected();

    void setLang(String lang);
    String getLang();

    String getDeviceId();
    void setDeviceId(String deviceId);

    String getDeviceModel();
    void setDeviceModel(String deviceModel);

}
