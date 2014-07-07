package wear.urbandroid.com.messagewear;

import android.util.Log;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

public class WearService extends WearableListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(WearService.class.getSimpleName(), "WEAR create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(WearService.class.getSimpleName(), "WEAR destroy");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.i(WearService.class.getSimpleName(), "WEAR Data changed " );
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.i(WearService.class.getSimpleName(), "WEAR Message " + messageEvent.getPath());
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.i(WearService.class.getSimpleName(), "WEAR Connected ");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        Log.i(WearService.class.getSimpleName(), "WEAR Disconnected");
    }
}
