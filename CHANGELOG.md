# Changelog
2.0.5:
- update to Phenix SDK 2019.2.1

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

