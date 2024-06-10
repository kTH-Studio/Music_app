package ru.kth.studio.music;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.KeyEvent;
import androidx.annotation.Nullable;

public class MediaSessionService extends Service {
    public static final int NOTIFICATION_ID = 888;
    private MediaNotificationManager mMediaNotificationManager;
    private MediaSessionCompat mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaNotificationManager = new MediaNotificationManager(this);
        mediaSession = new MediaSessionCompat(this, "SOME_TAG");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() { MusicActivity.raone.start();}

            @Override
            public void onPause() { MusicActivity.raone.pause();
            stopForeground(true);}});
        Notification notification = mMediaNotificationManager.getNotification(getMetadata(), getState(), mediaSession.getSessionToken());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) { startForeground(NOTIFICATION_ID, notification);
        } else { startForeground(NOTIFICATION_ID, notification, FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);}}

    public MediaMetadataCompat getMetadata() {
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, MusicActivity.artist);
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, MusicActivity.song_name);
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, MusicActivity.album);
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, MusicActivity.img);
        builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, MusicActivity.dur);
        return builder.build();}

    private PlaybackStateCompat getState() {
        long actions = MusicActivity.raone.isPlaying() ? PlaybackStateCompat.ACTION_PAUSE : PlaybackStateCompat.ACTION_PLAY;
        int state = MusicActivity.raone.isPlaying() ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED;
        final PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder();
        stateBuilder.setActions(actions);
        stateBuilder.setState(state, MusicActivity.raone.getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime());
        return stateBuilder.build();}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ("android.intent.action.MEDIA_BUTTON".equals(intent.getAction())) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get("android.intent.extra.KEY_EVENT");
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PAUSE) { MusicActivity.raone.pause();
                MusicActivity.imageview4.setImageResource(R.drawable.ic_play_arrow);
                MusicActivity.t.cancel();
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PLAY){ MusicActivity.raone.start();
                MusicActivity.imageview4.setImageResource(R.drawable.ic_pause);}}
        return super.onStartCommand(intent, flags, startId);}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null;}
}