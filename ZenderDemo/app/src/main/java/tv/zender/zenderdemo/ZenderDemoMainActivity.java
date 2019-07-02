package tv.zender.zenderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;

import tv.zender.ZenderAuthentication;
import tv.zender.player.ZenderPlayerConfig;
import tv.zender.player.ZenderPlayerListener;
import tv.zender.player.ZenderPlayerView;
import tv.zender.player.video.ZenderMediaPlayerView;
import tv.zender.player.video.ZenderPhenixVideoView;

public class ZenderDemoMainActivity extends AppCompatActivity implements ZenderPlayerListener {

    private ZenderPlayerView zenderPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lock to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Bind the view to the layout
        setContentView(R.layout.activity_zender_demo_main);
        zenderPlayerView = findViewById(R.id.zender_view);

        // Configure the player
        // Configure this before trying
        String targetId = "tttttttt-tttt-tttt-tttt-tttttttttttt";
        String channelId = "cccccccc-cccc-cccc-cccc-cccccccccccc";

        ZenderPlayerConfig playerConfig = new ZenderPlayerConfig(targetId, channelId);

        zenderPlayerView.setConfig(playerConfig );

        // Set black as a default background color
        zenderPlayerView.setBackgroundColor(Color.BLACK);

        // Register the video players
        ZenderPhenixVideoView phenixVideoView = new ZenderPhenixVideoView(getApplicationContext());
        playerConfig.registerVideoView(phenixVideoView);

        ZenderMediaPlayerView mediaPlayerView = new ZenderMediaPlayerView(getApplicationContext());
        playerConfig.registerVideoView(mediaPlayerView);

        // Prepare authentication
        /* Sample Device Authentication

        String userName= "demo user";
        String authToken = "<some unique deviceId>";
        String providerType = "device";

        HashMap<String, Object> authenticationMap = new HashMap<>();
        authenticationMap.put("name",userName);
        authenticationMap.put("token",authToken);

        ZenderAuthentication authentication = new ZenderAuthentication(authenticationMap, providerType);
        this.zenderPlayerView.setAuthentication(authentication);
        */

        // Registering as listener for player events
        this.zenderPlayerView.registerZenderPlayerListener(this);

        // start the player
        this.zenderPlayerView.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        this.zenderPlayerView.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.zenderPlayerView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.zenderPlayerView.stop();
        this.zenderPlayerView.unregisterZenderPlayerListener(this);
        this.zenderPlayerView.release();
    }


    // Zender Listeners
    @Override
    public void onZenderPlayerClose(LinkedTreeMap linkedTreeMap) {
        this.zenderPlayerView.stop();
        this.zenderPlayerView.unregisterZenderPlayerListener(this);
        this.zenderPlayerView.release();
    }

    @Override
    public void onZenderQuizShareCode(LinkedTreeMap linkedTreeMap) {
        String shareCode = null;
        String shareText = null;

        if (linkedTreeMap.containsKey("shareCode")) {
            shareCode = (String) linkedTreeMap.get("shareCode");
        }

        if (linkedTreeMap.containsKey("shareText")) {
            shareText = (String) linkedTreeMap.get("shareText");
        }

        if (linkedTreeMap.containsKey("text")) {
            shareText = (String) linkedTreeMap.get("text");
        }

    }

    @Override
    public void onZenderReady(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderFail(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPlayerFail(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPlayerReady(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPlayerLobbyEnter(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPlayerLobbyLeave(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAuthenticationInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAuthenticationRequired(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAuthenticationClear(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAuthenticationFail(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAuthenticationSuccess(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderTargetsInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderChannelsStreamsInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderChannelsStreamsPublish(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderChannelsStreamsUnpublish(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderStreamsInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderStreamsUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderStreamsDelete(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderStreamsStats(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderMediaInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderMediaUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderMediaDelete(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderMediaPlay(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxReplies(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxShout(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxShouts(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxShoutsDelete(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxShoutSent(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxDisable(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderShoutboxEnable(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderEmojisInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderEmojisUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderEmojisStats(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderEmojisTrigger(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAvatarsStats(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAvatarsTrigger(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsDelete(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsReset(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsVote(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsResults(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderPollsResultsAnimate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAppActivate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAppDeactivate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizInit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizUpdate(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizStart(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizStop(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizReset(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizDelete(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizQuestion(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizAnswer(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizAnswerTimeout(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizAnswerSubmit(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizAnswerCorrect(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizAnswerIncorrect(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizExtralifeUse(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizExtralifeIgnore(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizEliminated(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizEliminatedContinue(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizWinner(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizLoser(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizQuestionResults(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderQuizResults(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderUiStreamsOverview(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderOpenUrl(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderAdsShow(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderLoaderShow(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderLoaderHide(LinkedTreeMap linkedTreeMap) {

    }

    @Override
    public void onZenderLoaderTimeout(LinkedTreeMap linkedTreeMap) {

    }

}
