package com.cvmaker.ui.base;

/**
 * Created by Aya Mohamed on 11/17/2019.
 */
public interface BaseNavigator {

    interface UIChanges {
        void doHideKeyboard();
    }

    interface ShowAlert{
        void showAlertDialog(String message, String message_type);
    }

}
