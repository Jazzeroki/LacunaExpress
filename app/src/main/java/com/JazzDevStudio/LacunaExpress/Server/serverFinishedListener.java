package com.JazzDevStudio.LacunaExpress.Server;

import java.util.EventListener;

public interface serverFinishedListener extends EventListener {
    public void onResponseReceived(String reply);
}
