# Installation
The current version is `2.0.5`

The Zender android sdk is delivered as a downloadable zip file:

<https://repo.zender.tv/android/zender-android-sdk-v2.0.5.zip>

# Find your configuration in the Zender Admin
- Logging to the admin <https://admin.zender.tv>
- Select a specific stream
- Get the information (the I icon)
- Read the targetId and channelId

 ![Zender TargetId and ChannelId](docs/images/targetId-channelId.png?raw=true "Find your Zender TargetId and ChannelId")

# Requirements
- Zender is supported from Android >= 4.4
- It currently depends on the following libraries:

Unzip the sdk zip file and put the aar files inside the libs folder ( app/libs)

Now add this to your app/build.gradle:

```
 implementation fileTree(dir: 'libs', include: ['*.aar'])
 implementation 'com.google.code.gson:gson:2.8.2'
 implementation 'com.squareup.picasso:picasso:2.5.2'
```

## It requires INTERNET permission
Add the following to your AndroidManifest.xml
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Note: sometimes it requires you to reboot the device/emulator

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
```
String targetId = "tttttttt-tttt-tttt-tttt-tttttttttttt";
String channelId = "cccccccc-cccc-cccc-cccc-cccccccccccc";


ZenderPlayerConfig playerConfig = new ZenderPlayerConfig(targetId, channelId);

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

For a more detailed explantion on authentication see <https://github.com/zendertv/zender-docs/blob/master/AUTH.md>


## Setup the video player(s)
Zender offers the unique ability to dynamically change video players depending on the media Url provided. This allows you to easily add your own video player, switch between the native ,commercial video players and social players like youtube.

The order that you add the players matters: it will use the first player that matches the url/media type. Therefore it is good to add the MediaPlayer as a catch all after all other players. Also MediaPlayer allows you trigger local media bundled inside the app (like an error or placeholder video)

Note: future versions of the sdk wil autodetect the available players


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

# Analytics
Zender tracks many metrics. See [Detailed Analytics Documentation](ANALYTICS.md) for further detail.

For further analytics or integration in your own system you can listen to the events exposed by the ZenderPlayerListener and send it to your own tracking system.

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

The data format looks like:
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

# Advanced
More advanced topics are described at [Advanced Documentation](ADVANCED.md)

# Releases
We adhere to the Semantic Versions scheme to indiciate releases. Versions starting with 0.x should be considered beta versions and not yet ready for production use.

# Privacy
Before using people their name, avatar and other information, they should give a consent to use their data. It is up the implementor of the SDK to get the agreement of the user before exposing him in the Zender Player.

The data is securely stored on the Zender platform but there needs to be an agreement.

# License
All Zender Code is copyrighted and owned by Small Town Heroes BVBA. Extensions have their own license based on the license of the supplier.
This code does not grant you ownership or a license to use it. This needs to be arranged by a specific license agreement.
All code provided should be treated as confidential until official cleared.

# Changelog
See [Changelog](CHANGELOG.md)
