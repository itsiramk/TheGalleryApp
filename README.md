# The Gallery App

## A custom gallery app which shows the images and videos in single screen as album view.

### This is a Kotlin Project.

App uses the FusedLocationProviderClient and fetches uses current location.

-*On App launch we ask the mandatory user permissions to access storage without which we cannot access device storage. 
This is a prerequisite without which we cannot proceed*.
 
-Once permission is received, we use android.provider.MediaStore to access device albums*

-The app requirement is to segregate the folders in “All Images”, “All Videos” and “Camera” specifically which is done.

-Each folder in device is displayed with its album name and item count

-For folders with no name default Device Model name is displayed to avoid displayed blank album name

***Android Components used:***

*Development Language* - Kotlin

*Architecture* - MVVM

*Dependency Injection* - Dagger Hilt

*Networking Libraries* - Retrofit

*Asynchronous calls* - Flows, Coroutines

*UI* - ConstraintLayout, GridLayout.

*Image Loading* - Glider

*Video Displaying” - Exoplayer

![Image Albums](./images/gallery.png)    ![Loading Videos](./images/video.mp4)
