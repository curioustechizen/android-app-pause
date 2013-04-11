This project is a sample for demonstrating the use of android-app-pause library. This app runs an HTTP server. The behavior is:

  - When the app resumes, the HTTP server starts
  - When the app pauses, the HTTP server stops
  - The app has a few distinct Activities - all of which display the user agent and timestamp of the last request to the HTTP server

To see the app in action, run it and then make a request to IP:10000/ (the default port is 10000). Navigate between the various Activities - the HTTP server remains started as long as any of the Activities of this app are visible to the user.
