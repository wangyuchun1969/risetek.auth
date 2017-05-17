package com.risetek.auth.client.application;

import com.gwtplatform.mvp.client.UiHandlers;

interface ApplicationUiHandlers extends UiHandlers {
    void UpdateSocketStatus(String status);
    void setTotalUsers(int number);
}
