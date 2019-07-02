# Advanced

# Streams
To initialize a specific stream:
```
String targetId = "tttttttt-tttt-tttt-tttt-tttttttttttt";
String channelId = "cccccccc-cccc-cccc-cccc-cccccccccccc";
String streamId =  "ssssssss-ssss-ssss-ssss-ssssssssssss";

ZenderPlayerConfig playerConfig = new ZenderPlayerConfig(targetId, channelId, streamId);

// Temporary override endpong for new styling
String playerEndpointPrefix = "https://player2-native.zender.tv";
playerConfig.overridePlayerEndpointPrefix(playerEndpointPrefix);

zenderPlayerView.setConfig(playerConfig );
```

# Events/Listener
Events happening in the ZenderPlayer are exposed and can be handled by registering a class that implements `ZenderPlayerListener`.
The methods are optional , so you have to add them yourselves.

The payload provided is currently in LinkedTreeMap, this might changed to HashMap in the future to remove the gson dependency.

Register your listeners as:

```
this.zenderPlayerView.registerZenderPlayerListener(this);
this.zenderPlayerView.unregisterZenderPlayerListener(this);
```

Available events:
```
    default void onZenderReady(LinkedTreeMap payload) {
    default void onZenderUpdate(LinkedTreeMap payload) {
    default void onZenderFail(LinkedTreeMap payload) {
    default void onZenderPlayerFail(LinkedTreeMap payload) {
    default void onZenderPlayerReady(LinkedTreeMap payload) {
    default void onZenderPlayerClose(LinkedTreeMap payload) {
    default void onZenderPlayerLobbyEnter(LinkedTreeMap payload) {
    default void onZenderPlayerLobbyLeave(LinkedTreeMap payload) {
    default void onZenderAuthenticationInit(LinkedTreeMap payload) {
    default void onZenderAuthenticationRequired(LinkedTreeMap payload) {
    default void onZenderAuthenticationClear(LinkedTreeMap payload) {
    default void onZenderAuthenticationFail(LinkedTreeMap payload) {
    default void onZenderAuthenticationSuccess(LinkedTreeMap payload) {
    default void onZenderTargetsInit(LinkedTreeMap payload) {
    default void onZenderChannelsStreamsInit(LinkedTreeMap payload) {
    default void onZenderChannelsStreamsPublish(LinkedTreeMap payload) {
    default void onZenderChannelsStreamsUnpublish(LinkedTreeMap payload) {
    default void onZenderStreamsInit(LinkedTreeMap payload) {
    default void onZenderStreamsUpdate(LinkedTreeMap payload) {
    default void onZenderStreamsDelete(LinkedTreeMap payload) {
    default void onZenderStreamsStats(LinkedTreeMap payload) {
    default void onZenderMediaInit(LinkedTreeMap payload) {
    default void onZenderMediaUpdate(LinkedTreeMap payload) {
    default void onZenderMediaDelete(LinkedTreeMap payload) {
    default void onZenderMediaPlay(LinkedTreeMap payload) {
    default void onZenderShoutboxInit(LinkedTreeMap payload) {
    default void onZenderShoutboxUpdate(LinkedTreeMap payload) {
    default void onZenderShoutboxReplies(LinkedTreeMap payload) {
    default void onZenderShoutboxShout(LinkedTreeMap payload) {
    default void onZenderShoutboxShouts(LinkedTreeMap payload) {
    default void onZenderShoutboxShoutsDelete(LinkedTreeMap payload) {
    default void onZenderShoutboxShoutSent(LinkedTreeMap payload) {
    default void onZenderShoutboxDisable(LinkedTreeMap payload) {
    default void onZenderShoutboxEnable(LinkedTreeMap payload) {
    default void onZenderEmojisInit(LinkedTreeMap payload) {
    default void onZenderEmojisUpdate(LinkedTreeMap payload) {
    default void onZenderEmojisStats(LinkedTreeMap payload) {
    default void onZenderEmojisTrigger(LinkedTreeMap payload) {
    default void onZenderAvatarsStats(LinkedTreeMap payload) {
    default void onZenderAvatarsTrigger(LinkedTreeMap payload) {
    default void onZenderPollsInit(LinkedTreeMap payload) {
    default void onZenderPollsUpdate(LinkedTreeMap payload) {
    default void onZenderPollsDelete(LinkedTreeMap payload) {
    default void onZenderPollsReset(LinkedTreeMap payload) {
    default void onZenderPollsVote(LinkedTreeMap payload) {
    default void onZenderPollsResults(LinkedTreeMap payload) {
    default void onZenderPollsResultsAnimate(LinkedTreeMap payload) {
    default void onZenderAppActivate(LinkedTreeMap payload) {
    default void onZenderAppDeactivate(LinkedTreeMap payload) {
    default void onZenderQuizInit(LinkedTreeMap payload) {
    default void onZenderQuizUpdate(LinkedTreeMap payload) {
    default void onZenderQuizStart(LinkedTreeMap payload) {
    default void onZenderQuizStop(LinkedTreeMap payload) {
    default void onZenderQuizReset(LinkedTreeMap payload) {
    default void onZenderQuizDelete(LinkedTreeMap payload) {
    default void onZenderQuizQuestion(LinkedTreeMap payload) {
    default void onZenderQuizAnswer(LinkedTreeMap payload) {
    default void onZenderQuizAnswerTimeout(LinkedTreeMap payload) {
    default void onZenderQuizAnswerSubmit(LinkedTreeMap payload) {
    default void onZenderQuizAnswerCorrect(LinkedTreeMap payload) {
    default void onZenderQuizAnswerIncorrect(LinkedTreeMap payload) {
    default void onZenderQuizExtralifeUse(LinkedTreeMap payload) {
    default void onZenderQuizExtralifeIgnore(LinkedTreeMap payload) {
    default void onZenderQuizEliminated(LinkedTreeMap payload) {
    default void onZenderQuizEliminatedContinue(LinkedTreeMap payload) {
    default void onZenderQuizWinner(LinkedTreeMap payload) {
    default void onZenderQuizLoser(LinkedTreeMap payload) {
    default void onZenderQuizQuestionResults(LinkedTreeMap payload) {
    default void onZenderQuizShareCode(LinkedTreeMap payload) {
    default void onZenderQuizResults(LinkedTreeMap payload) {
    default void onZenderUiStreamsOverview(LinkedTreeMap payload) {
    default void onZenderOpenUrl(LinkedTreeMap payload) {
    default void onZenderAdsShow(LinkedTreeMap payload) {
    default void onZenderLoaderShow(LinkedTreeMap payload) {
    default void onZenderLoaderHide(LinkedTreeMap payload) {
    default void onZenderLoaderTimeout(LinkedTreeMap payload) {
```

The loading of the ZenderPlayer can take some time. Use the event `onZenderLoaderHide` to know when to remove your own loaderview.

# Debugging
To inspect the webview , you can set `config.debugEnabled=true`.
This will allow the webview/chrome view to be inspected from remote.

