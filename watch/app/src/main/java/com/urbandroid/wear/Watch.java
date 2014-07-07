package com.urbandroid.wear;

import android.os.Bundle;
import android.support.wearable.activity.InsetActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.*;

import java.util.List;

public class Watch  extends InsetActivity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks  {


    private GoogleApiClient client;

    @Override
    public void onReadyForContent() {
        setContentView(wear.urbandroid.com.messagewatch.R.layout.activity_watch);

        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.i(Watch.class.getSimpleName(), "Connection failed");
                    }
                })
                .addApi(Wearable.API)
                .build();

        client.connect();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sendMessage(final String message, final byte[] payload) {
        Log.i(Watch.class.getSimpleName(), "WEAR Sending message " + message);
        Wearable.NodeApi.getConnectedNodes(client).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                List<Node> nodes = getConnectedNodesResult.getNodes();
                for (Node node : nodes) {
                    Log.i(Watch.class.getSimpleName(), "WEAR sending " + message + " to " + node);
                    Wearable.MessageApi.sendMessage(client, node.getId(), message, payload).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.i(Watch.class.getSimpleName(), "WEAR Result " + sendMessageResult.getStatus());
                        }
                    });
                }

            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(client, this);
        sendMessage("/start", null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(Watch.class.getSimpleName(), "Connection failed");
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Wearable.MessageApi.removeListener(client, this);
        client.disconnect();
    }

}