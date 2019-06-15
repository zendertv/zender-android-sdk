# Installation
The Zender android sdk is delivered as a zip file following the convention 
zender-android-sdk-<version>.zip . The current version is 2.0.0 

## Requirements
### ZenderCore library
- Zender is supported from Android >= 4.4
- It currently depends on the following libraries:

```
 implementation 'com.android.support:appcompat-v7:28.0.0'
 implementation 'com.google.code.gson:gson:2.3.1'
 implementation 'com.squareup.picasso:picasso:2.5.2'
```
- It requires INTERNET permission

Add the `zender_core` module/component to your project to use it.

### Extension: Zender Phenix
The Zender Core component contains support for the standard Android MediaPlayer.

If you want to make use of the Phenix RTS Low latency you need:
1. to add the extension `zender_phenix` as a module to your project
2. add the phenix library (jar) to your application

- The official documentation of the Phenix Android sdk is available at <https://phenixrts.com/docs/android>
- The release `phenix-sdk-android-v15-10-2018.zip` is an upcoming release. It delivers the library in aar format and also supports in simulator support.
- Read the github notes on in the zipfile on how to integrate
  
  
# Example Use
## Initialize ZenderPlayer
Add the following to your layout
```
  <tv.zender.player.ZenderPlayerView
      android:id="@+id/zender_view"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      />
```

Now get bind the view to zenderPlayerView
```
@BindView(R.id.zender_view)
ZenderPlayerView zenderPlayerView;
```

Note: we do not support "Multi-Window" support, be sure to disable that support on this view.

## Configuration ZenderPlayer
To initialize a ZenderPlayer you need to provide it with:
- a targetId: this is a unique identifier for your organization
- a channelId: this is a unique identifier of the channel within your organization (f.i. a specific show)

These values will be provided to you for demo purposes and can be found the in the admin console.

To initialize a channel and have the player autoswitch between public streams

```
String targetId = "tttttttt-tttt-tttt-tttt-tttttttttttt";
String channelId = "cccccccc-cccc-cccc-cccc-cccccccccccc";


ZenderPlayerConfig playerConfig = new ZenderPlayerConfig(targetId, channelId);

// Temporary override endpong for new styling
String playerEndpointPrefix = "https://player2-native.zender.tv";
playerConfig.overridePlayerEndpointPrefix(playerEndpointPrefix);

zenderPlayerView.setConfig(playerConfig );
```

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


## Setup Authentication
Zender support several authentication mechanisms out of the box: Facebook, Google, Instagram. These require configuration in the admin console to setup the correct applicationId and secrets. An alternative for demo purposes is to use the "device" authentication. This is a loose for of authentication where the app can set a unique identifier to identify a user.

```
ZenderAuthentication authentication = new ZenderAuthentication(authenticationMap, providerType);
zenderPlayerView.setAuthentication(authentication);
```

*Device*
This acts as anonymous login based on a randomly created deviceId
```
String userName= "demo user";
String authToken = "<some unique deviceId>";
String providerType = "device";

HashMap<String, Object> authenticationMap = new HashMap<>();
authenticationMap.put("name",userName);
authenticationMap.put("token",authToken);
```

*SignedProvider*
This provider allows you to provide the information from your own authentication system and sign it
```
HashMap authPayload = new HashMap<String, String>();
authPayload.put("token","<signedToken>");
authPayload.put("avatar","<avatar url>");
authPayload.put("firstname","<firstname>");
authPayload.put("lastname","<lastname>");

ZenderAuthentication authentication = new ZenderAuthentication(authPayload,"signedProvider");
```

*Facebook*
```
HashMap authPayload = new HashMap<String, String>();
authPayload.put("token", facebookToken.getToken());
ZenderAuthentication authentication = new ZenderAuthentication(authPayload,"facebook");
```

*Google*
```
HashMap authPayload = new HashMap<String, String>();
authPayload.put("token", "<googletoken>")
ZenderAuthentication authentication = new ZenderAuthentication(authPayload,"google");
```


## Setup the video player(s)
Zender offers the unique ability to dynamically change video players depending on the media Url provided. This allows you to easily add your own video player, switch between the native ,commercial video players and social players like youtube.

The order that you add the players matters: it will use the first player that matches the url/media type. Therefore it is good to add the MediaPlayer as a catch all after all other players. Also MediaPlayer allows you trigger local media bundled inside the app (like an error or placeholder video)


Phenix Video Player: (supports urls phenix: <channel-name>)

```
ZenderPhenixVideoView phenixVideoView = new ZenderPhenixVideoView(getApplicationContext());
playerConfig.registerVideoView(phenixVideoView);
```

Native MediaPlayer: (catchall)

```
ZenderMediaPlayerView mediaPlayerView = new ZenderMediaPlayerView(getApplicationContext());
playerConfig.registerVideoView(mediaPlayerView);
```

## Setup Zender in your activity

Zender can autorotate but needs to have the activity configured to listen to configuration changes & sensor
```
 <activity
            android:name=".ZenderExamplePlayerActivity"
            android:windowSoftInputMode="adjustPan"
            android:screenOrientation="fullSensor"
            android:configChanges="keyboardHidden|orientation|screenSize" />
```

AdjustPan is required to make text input work correctly.

ZenderPlayerView has the methods: `start, pause, resume, stop and release`.

```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  
  super.onCreate(savedInstanceState);

  // Lock to Portrait
  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

  // Prepare Zender Player Config
  ZenderPlayerConfig playerConfig = new ZenderPlayerConfig(targetId, channelId);
  playerConfig.overridePlayerEndpointPrefix(playerEndpointPrefix);
  // playerConfig.overrideApiEndpoint(apiEndpoint);
  // playerConfig.debugEnabled = debugEnabled;

  // Prepare Device Token
  ZenderUserDevice zenderUserDevice = new ZenderUserDevice();
  zenderUserDevice.token = deviceToken;
  playerConfig.setUserDevice(zenderUserDevice);

  this.zenderPlayerView.setConfig(playerConfig);

  // Prepare authentication
  HashMap<String, Object> authenticationMap = new HashMap<>();
  authenticationMap.put("name",userName);
  authenticationMap.put("token",authToken);
  providerType = providerType;

  ZenderAuthentication authentication = new ZenderAuthentication(authenticationMap, providerType);
  this.zenderPlayerView.setAuthentication(authentication);

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
```

## Mute/Unmute
```
this.zenderPlayerView.mute();
this.zenderPlayerView.unmute();
```

## Pause/Resume a video
```
this.zenderPlayerView.videoPause();
this.zenderPlayerView.videoResume();
```

## Lock Orientation
Currently zender player only supports portrait mode. Be sure to lock your activity/view

## Disposing a player
```
  this.zenderPlayerView.stop();
  this.zenderPlayerView.unregisterZenderPlayerListener(this);
  this.zenderPlayerView.release();
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

# Analytics
The Zender Admin console has an overview of different analytics. We can additionally support Google Analytics out the box. For this the GA trackedId needs to be added to the admin console.

For further analytics or integration in your own system you can listen to the events exposed by the ZenderPlayerListener.


# Push notifications
The Zender Admin console provides a way to send push notifications to users to notifiy them when new streams are available. This requires push notification certificate setup to match the bundleId of your app and allowing us to send the push notifications.

## Creating a userDevice object
```
String deviceToken = "<gcm-token>"
ZenderUserDevice device = new ZenderUserDevice();
device.token = deviceToken;
```
## Configure the ZenderPlayer with the correct Device
In the settings menu of the Zender player you can enable/disable push notifications. 
To match the correct device token , you need to specify the correct deviceToken in the configuration.

```
playerConfig.setUserDevice(device);

```

## Using ZenderApiClient

```
String deviceToken = "gcm-token"
String targetId = "tttttttt-tttt-tttt-tttt-tttttttttttt";
String channelId = "cccccccc-cccc-cccc-cccc-cccccccccccc";

// Use the correct authentication mechanism
HashMap authPayload = new HashMap<String, String>();
authPayload.put("token","an authentication token matching the provider");
authPayload.put("name","My name is nobody");
ZenderAuthentication authentication = new ZenderAuthentication(authPayload,"device");

ZenderUserDevice device = new ZenderUserDevice();
device.token = deviceToken;

ZenderApiClient apiClient = new ZenderApiClient(context, targetId, channelId);
apiClient.authentication = authentication;

apiClient.login(new ZenderApiLoginCallback(){
    @Override
    public void completionHandler(ZenderError error, ZenderSession session) {

    if (error == null ) {
    System.out.println("Session login"+session.token);
    apiClient.registerDevice(device, new ZenderApiRegisterDeviceCallback() {
        @Override
        public void completionHandler(ZenderError error) {
        if (error != null) {
        System.out.println("Register Device Error occured"+ error.description);
        }

        }
        });
    } else {
    System.out.println("Login error occured" + error.description);
    }

    };
    });
```

## Quiz codes / Extra life codes
### Share Code

When a user want to share his code `onZenderQuizShareCode` will be triggered with shareCode in the payload.

### Redeem Code

Use `zenderPlayerView.redeemCode("yourcode")` to redeem a code.

Note: This needs to be done before the player has started.
A user that redeems a code that did not yet have a code will receive a message in the player.

## Deeplinking
The push notification can contain the targetId, channelId and/or streamId allowing you to directly start a stream. This needs to be implemented in your app to capture and handle the push notifications.

The zender notification is located in the data part, not in the notification part.

```
    public void onMessageReceived(RemoteMessage remoteMessage) {
      Map<String, String> data = remoteMessage.getData();
      if (data != null) {
                 Log.d("ZenderNotification","data: "+ data.toString());
      }
    }
```

```
data:
{
  zender={
    "targetId":"<targetId>",
    "channelId":"<channelId>"
  }, 
  message="a message from zender"
}
```

# Debugging
To inspect the webview , you can set `config.debugEnabled=true`.
This will allow the webview/chrome view to be inspected from remote.


# Releases
We adhere to the Semantic Versions scheme to indiciate releases. Versions starting with 0.x should be considered beta versions and not yet ready for production use.


# Privacy
Before using people their name, avatar and other information, they should give a consent to use their data. It is up the implementor of the SDK to get the agreement of the user before exposing him in the Zender Player.

The data is securely stored on the Zender platform but there needs to be an agreement.

# License
All Zender Code is copyrighted and owned by Small Town Heroes BVBA. Extensions have their own license based on the license of the supplier.
This code does not grant you ownership or a license to use it. This needs to be arranged by a specific license agreement.
All code provided should be treated as confidential until official cleared.

# TODO
## Functional
- add spinner Timeout callback
- add helper to detect if it is a Zender Push notification
- improve mediaError handling
- add authentication on AuthenticationInit
- check handling of resizeableActivity

## Tech
- Allow to register different video Providers
- Relax dependencies
- remove dependency on LinkTreeMapper (google.gson)
- add remote logging option

# Changelog
2.0.0:
- removed requiment for JDK 1.8 at compile time: this requires you to implement all the ZenderPlayerListener classes
- phenix video provider support has been moved from core to it's own module/component and needs to registered separatedly
- `zender_component` has been renamed to `zender_core`
- added support for VRTPlayer/Vualto player
- added ability to play an individual stream instead of a channel

1.0.2:
- correct loglevel for errors
- log the userName not the fullname
- use highavailability for phenix stream selection

1.0.1:
- bugfix for api client - pass all attributes

1.0.0:
- added remote logging

0.9.10:
- add videoHide & videoShow method to allow use of surfaceview for ads
- APIClient now requires a context for improved logging

0.9.9:
- user gets a notification on bad video quality
- quiz questions & answers now give haptic feedback
- correctly log the sdk version in the useragent

0.9.8:
- expose all Zender events over the bridge
- improved handling/calculation of keyboard in non-immersive mode
- handle background/foreground correctly
- improve push notification handing
- redeemCode
- improved methods for easier activity lifecycle handling
- update documentation


0.9.7 : 
- fix for failing ZenderAuthentication provider
- updated docs for medialaan provider

0.9.6 :
  - first version
