android-app-pause
=================

This library simulates `Application`-level `onPause()` and `onResume()` life cycle events. But, what does that actually mean? For the purposes of this library, the following definitions apply:

> An app is considered to be paused when the app is no longer visible to the user. By definition, this means that when an app is paused, _none of the `Activity`s that belong to the app are visible to the user_.

Along similar lines,

> An app is considered to be resumed when at least one `Activity` from the app is visible to the user.

For more details about why these definitions were chosen, take a look at [this blog post](http://curioustechizen.blogspot.in/2012/12/android-application-level-pause-and.html).

Note that when a user is using your app, if the device orientation is changed, the user can be considered to be continuing viewing your app. Thus, in general, device orientation changes should **not** be treated as app pause events.


##New in v2.0

v1.0 of this library did not take into account the device rotations. Hence, when you rotated the device, `onStop()` would be called on all the activities, all clients would "un-bind", and your `onAppPause()` would be called followed by `onAppResume()`.

v2.0 takes device rotations into account. By default, device rotations **do not** trigger app pause events. This behavior can be altered using the `setTreatRotationAsAppPause(boolean)` method.

See the Appendices at the end of this README for more details regarding the API and implementation changes.


##How to Use

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


##When to Use

This library is useful when your app has several `Activity`s and you wish to stop or pause certain "app-wide" background tasks when your app as a whole "goes into the background". There are some background tasks that you would want to keep running as long as user is interacting with your app. However, when user is no longer actively interacting with your app, you would wish to stop/pause these background tasks. 

Typical actions you might want to perform when you app "pauses" are:
 
 - Clearing scheduled Alarms,.
 - Stopping HTTP communication. 
 - Unregistering from broadcasts.
 - Stop monitoring the battery
 -  ... and the like

Similarly, you would want to reverse these actions as soon as your app "returns to the foreground".

Note that this library is _not_ useful if your app has just a single `Activity` or two. In such cases, you could use the `Activity` life-cycle methods `onPause()` and `onResume()` to start/stop background tasks.


##Contributing
If you want to improve this library, fork away. Pull requests are welcome. The appendices at the end of this README contain information for library developers.


##Why is this a Library Project?
Well, it doesn't _have_ to be. The library could just as well have been made into a .jar file that you can include in the libs/ folder of your app. I decided to make it an Android Library project to keep room for future enhancements. Also, I really don't like the idea of making a regular Java project and adding `android.jar` as a dependency in order to make it compile.


###Appendix 1: API Changes

  - `AbstractAppActiveService` class has been removed.
  - `AbstractAppPauseApplication` has been introduced.
  - `AbstractAppPauseActivity` no longer has a `getAppActiveServiceClass()` method.
  - `setTreatRotationAsAppPause` method has been added to `AbstractAppPauseActivity` class.

Please note that the removals above are not deprecations. I decided not to maintain the old procedure of using `Service`. See the section on **Implementation Changes** below to know more about the reason.


###Appendix 2: Implementation changes

The high-level functioning of v1.0 of this library was as follows:

  - Create a bound `Service`.
  - In `onStart()` of every `Activity`, bind the above `Service`.
  - In `onStop()` of every `Activity`, unbind the above `Service`.
  - The `onBind()` method of the `Service` represents an App resume.
  - The `onDestroy()` method of the `Service represents an App pause.

As you can see, this approach does not take device rotations into account. Basically, what you want to do is this:
> If `onStop()` is being called because the device orientation is being changed, then do not perform the unbind.

However, if you bind a Service to an Activity but do not unbind it before the Activity is destroyed, then you get a `ServiceConnectionLeaked` at runtime.

To work around this, v2.0 of this library **discards the `Service` altogether**. Instead, it uses a simple counter mechanism in the custom `Application` class (`AbstractAppPauseApplication`). The functioning is now as follows:

  - In the `Application` class, maintain an integer counter.
  - in every Activity, if `onStart()` is not being called as result of an orientation change, increment this counter.
  - In every `Activity`, if `onStop()` is not being called as result of an orientation change, decrement the counter.
  - When the count gets incremented from 0 to 1, the app has resumed.
  - When the count gets decremented from 1 to 0, the app has paused.


###License

 
	Copyright 2013 Kiran Rao

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
