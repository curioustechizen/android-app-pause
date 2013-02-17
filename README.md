android-app-pause
=================

This library simulates `Application`-level `onPause()` and `onResume()` life cycle events. But, what does that actually mean? For the purposes of this library, the following definitions apply:

> An app is considered to be paused when the app is no longer visible to the user. By definition, this means that when an app is paused, _none of the `Activity`s that belong to the app are visible to the user_.

Along similar lines,

> An app is considered to be resumed when at least one `Activity` from the app is visible to the user.

For more details about why these definitions were chosen, take a look at [this blog post](http://curioustechizen.blogspot.in/2012/12/android-application-level-pause-and.html).

Note that when a user is using your app, if the device orientation is changed, the user can be considered to be continuing viewing your app. Thus, in general, device orientation changes should **not** be treated as app pause events.


###When to Use

This library is useful when your app has several `Activity`s and you wish to stop or pause certain "app-wide" background tasks when your app as a whole "goes into the background". There are some background tasks that you would want to keep running as long as user is interacting with your app. However, when user is no longer actively interacting with your app, you would wish to stop/pause these background tasks. 

Typical actions you might want to perform when you app "pauses" are:
 
 - Clearing scheduled Alarms,.
 - Stopping HTTP communication. 
 - Unregistering from broadcasts.
 - Stop monitoring the battery
 -  ... and the like

Similarly, you would want to reverse these actions as soon as your app "returns to the foreground".

Note that this library is _not_ useful if your app has just a single `Activity` or two. In such cases, you could use the `Activity` life-cycle methods `onPause()` and `onResume()` to start/stop background tasks.

###How to Use

`android-app-pause` is provided as an Android Library project. To use it, follow these steps:

 1. Add `android-app-pause` as a dependent library to your Android Application Project.
 - Extend `AbstractAppPauseApplication` and override the methods `onAppPause()` and `onAppResume()`.
 - For every Activity in your app: 
   - Extend `AbstractAppPauseActivity`, instead of `android.app.Activity`. 
   - (Optional): If you want device rotations to trigger app pauses, call `setTreatRotationAsAppPause` with a value of `true`.

Check out the sample project to see how it is used.


####Note:
In some cases, it may not possible for you to extend `AbstractAppPauseActivity`. This could happen if you are already inheriting from some other base Activity like one of the Sherlock* Activities from ActionBar Sherlock. In such cases, you can replicate the functionality of `AbstractAppPauseActivity` in your Activities. Take a look at the source for `AbstractAppPauseActivity`.

Just remember that you need to perform these steps in __every `Activity` in your app__.


###Why is this a Library Project?
Well, it doesn't _have_ to be. The library could just as well have been made into a .jar file that you can include in the libs/ folder of your app. I decided to make it an Android Library project to keep room for future enhancements. Also, I really don't like the idea of making a regular Java project and adding `android.jar` as a dependency in order to make it compile.

