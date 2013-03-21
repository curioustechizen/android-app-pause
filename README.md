android-app-pause
=================

This library simulates `Application`-level `onPause()` and `onResume()` life cycle events. But, what does that actually mean? For the purposes of this library, the following definitions apply:

> An app is considered to be paused when the app is no longer visible to the user. By definition, this means that when an app is paused, _none of the `Activity`s that belong to the app are visible to the user_.

Along similar lines,

> An app is considered to be resumed when at least one `Activity` from the app is visible to the user.


##How to Use

`android-app-pause` is provided as an Android Library project. To use it, follow these steps:

 1. Add `android-app-pause` as a dependent library to your Android Application Project.
 - Extend `AbstractAppPauseApplication` and override the methods `onAppPause()` and `onAppResume()`.
 - For every Activity in your app: 
   - Extend `AbstractAppPauseActivity`, instead of `android.app.Activity`. 
   - (Optional): If you want device rotations to trigger app pauses, call `setTreatRotationAsAppPause` with a value of `true`.

Check out the sample project to see how it is used.


##New in v2.0
v1.0 of this library did not take into account the device rotations. Hence, when you rotated the device, `onStop()` would be called on all the activities, all clients would "un-bind", and your `onAppPause()` would be called followed by `onAppResume()`.

v2.0 takes device rotations into account. By default, device rotations **do not** trigger app pause events. This behavior can be altered using the `setTreatRotationAsAppPause(boolean)` method.


##More Details
For more details including implementation information and tips for contributors, please see [the website](http://curioustechizen.github.com/android-app-pause).


##Contributing
If you want to improve this library, fork away. Pull requests are welcome.


##Who uses this?
If you are using this library in your app, please be kind enough to add your app to the list [here](https://github.com/curioustechizen/android-app-pause/wiki/Apps-using-android-app-pause).



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
