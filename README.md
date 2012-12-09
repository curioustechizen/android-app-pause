android-app-pause
=================

This library simulates `Application`-level `onPause()` and `onResume()` life cycle events. But, what does that actually mean? For the purposes of this library, the following definitions apply:

> An app is considered to be paused when the app is no longer visible to the user. By definition, this means that when an app is paused, _none of the `Activity`s that belong to the app are visible to the user_.

Along similar lines,

> An app is considered to be resumed when at least one `Activity` from the app is visible to the user.

For more details about why these definitions were chosen, take a look at [this blog post](http://curioustechizen.blogspot.in/2012/12/android-application-level-pause-and.html).


###Usage

To use this library, follow these steps:

 1. Extend `AbstractAppActiveService` and override the methods `onAppPause()` and `onAppResume()`. Typically, you would want to perform tasks like clearing scheduled Alarms, stopping HTTP communication, unregistering from broadcasts, stop monitoring the battery and the like in `onAppPause()` and reverse the actions in `onAppResume()`.
 - For every Activity in your app, extend `AbstractAppPauseActivity`, instead of `android.app.Activity`. Override the `getAppActiveServiceClass()` and return the class you created in Step 1.

Check out the sample project to see how it is used.


####Note:
In some cases, it may not possible for you to extend `AbstractAppPauseActivity`. This could happen if you are already inheriting from some other base Activity like one of the Sherlock* Activities from ActionBar Sherlock. In such cases, you can replicate the functionality of `AbstractAppPauseActivity` in your Activities. Take a look at the source for `AbstractAppPauseActivity`. All it does is:

 1. Bind your implementation of `AbstractAppActiveService` in `onStart()`,
 - Unbind it in `onStop()`.

Just remember that you need to perform these steps in __every `Activity` in your app__.


