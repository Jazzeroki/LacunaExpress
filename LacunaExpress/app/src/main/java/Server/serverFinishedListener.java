package Server;

import java.util.EventListener;

public interface serverFinishedListener extends EventListener {
    public void onResponseRecieved(String reply);
}
