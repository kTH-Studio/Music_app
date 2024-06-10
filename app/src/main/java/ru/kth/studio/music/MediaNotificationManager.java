package ru.kth.studio.music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import androidx.media.session.MediaButtonReceiver;

public class MediaNotificationManager {

    private static final String CHANNEL_ID = "ru.kartofan.theme.music.app.channel";
    private static final int REQUEST_CODE = 501;
    private final MediaSessionService mService;
    private final NotificationCompat.Action mPlayAction;
    private final NotificationCompat.Action mPauseAction;
    private final NotificationCompat.Action mNextAction;
    private final NotificationCompat.Action mPreviousAction;
    private final NotificationManager mNotificationManager;


    public MediaNotificationManager(MediaSessionService musicContext) {
        mService = musicContext;
        mNotificationManager = (NotificationManager) mService.getSystemService(Service.NOTIFICATION_SERVICE);
        mPlayAction = new NotificationCompat.Action(R.drawable.ic_play_arrow, "play", MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_PLAY));
        mPauseAction = new NotificationCompat.Action(R.drawable.ic_pause, "pause", MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_PAUSE));
        mNextAction = new NotificationCompat.Action(R.drawable.ic_fast_forward, "next", MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_SKIP_TO_NEXT));
        mPreviousAction = new NotificationCompat.Action(R.drawable.ic_fast_rewind, "previous", MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));
        mNotificationManager.cancelAll();}

    public NotificationManager getNotificationManager() { return mNotificationManager;}

    public Notification getNotification(MediaMetadataCompat metadata, @NonNull PlaybackStateCompat state, MediaSessionCompat.Token token) {
        boolean isPlaying = state.getState() == PlaybackStateCompat.STATE_PLAYING;
        MediaDescriptionCompat description = metadata.getDescription();
        NotificationCompat.Builder builder = buildNotification(state, token, isPlaying, description);
        return builder.build();}

    private NotificationCompat.Builder buildNotification(@NonNull PlaybackStateCompat state, MediaSessionCompat.Token token, boolean isPlaying, MediaDescriptionCompat description) {
        if (isAndroidOOrHigher()) { createChannel();}
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mService, CHANNEL_ID);
        builder.setStyle(new MediaStyle()
                .setMediaSession(token)
                .setShowActionsInCompactView(0,1,2)
                .setShowCancelButton(true)
                .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_STOP)))
                .setColor(ContextCompat.getColor(mService, R.color.black))
                .setColorized(true)
                .setLargeIcon(MusicActivity.btm)
                .setContentTitle(MusicActivity.song_name)
                .setContentText(MusicActivity.artist)
                .setSubText(MusicActivity.album)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSound(Uri.parse(MusicActivity.currentfile))
                .setContentIntent(createContentIntent())
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mService, PlaybackStateCompat.ACTION_PAUSE));
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) { builder.addAction(new NotificationCompat.Action(R.drawable.ic_pause, "Pause", mPauseAction.getActionIntent()));
        } else { builder.addAction(new NotificationCompat.Action(R.drawable.ic_play_arrow, "Play", mPlayAction.getActionIntent()));}
        builder.addAction(mPreviousAction)
                .addAction(isPlaying ? mPauseAction : mPlayAction)
                .addAction(mNextAction);
        return builder;}

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            CharSequence name = "Player";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);}}

    private boolean isAndroidOOrHigher() { return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;}

    private PendingIntent createContentIntent() {
        Intent openUI = new Intent(mService, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        return PendingIntent.getActivity(mService, REQUEST_CODE, openUI, PendingIntent.FLAG_IMMUTABLE);}}