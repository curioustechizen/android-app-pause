##CHANGELOG

###v2.1.1
Updated sample app to demonstrate real world usage. It now runs an HTTP server. The server runs as long as the app is resumed. Once the app pauses, the HTTP server is stopped.


###v2.1:

 - Fine-tuned the logic to detect whether `onStop()` is being called because of an orientation change. This uses `isChangingConfigurations()` if running on HC or higher.
    

###v2.0:
Complete overhaul of the library core.

 - This version takes device rotations into account.
 - Instead of using a `Service` to keep track of "live" activities, we now use an integer counter in an `Application`.
 - **Removed** `AbstractAppPauseService`, the functionality of this class is replaced with `AbstractAppPauseApplication`


