# The Gallery App

## A custom gallery app which shows the images and videos in single screen as album view.

### This is a Kotlin Project.

App uses the FusedLocationProviderClient and fetches uses current location.

-*On App launch we ask the storage permissions to the user without which we cannot access device storage. 
This is a prerequisite without which we cannot proceed*.
 
-Once permission is received, we use android.provider.MediaStore to access device albums*

-The app requirement is to segregate the folders in “All Images”, “All Videos” and “Camera” specifically which is done.

-Each folder in device is displayed with its album name and item count

-For folders with no name, default Device Model name is displayed to avoid displaying blank album name

-After clicking on each folder, we display all the items within that folder in a another screen in a recyclerview

-All images and videos are displayed with its default name.

-For videos, we have a play icon (using the android inbuilt play icon) on click of which we are playing the video in a Dialog which can be closed too.

-The dialog is cancelable when user touches outside. Also, user can close it with a cancel icon.

-The cancel icon is a custom svg.

- Defined all constants in a separate file. Also have made sure to create custom styles.

- Added strings and dimen file too.
  
-Added 100% test case coverage for viewmodel using Mockito

***Android Components used:***

*Development Language* - Kotlin

*Architecture* - MVVM

*Dependency Injection* - Dagger Hilt

*Asynchronous calls* - Flows, Coroutines

*UI* - ConstraintLayout, GridLayout.

*Image Loading* - Glider

*Video Displaying* - Exoplayer

*Key Highlights* - Custom SVG, Custom Progress Dialog, No external libraries used for custom views.

*Testing* - Mockito

<img src="https://github.com/itsiramk/TheGalleryApp/blob/master/testcoverage.png"> 
<img width="350" alt="image1" src="https://github.com/itsiramk/TheGalleryApp/blob/master/gallery.png"> <img width="350" alt="image2" src="https://github.com/itsiramk/TheGalleryApp/blob/master/videolist.png">  
<img width="350" alt="image3" src="https://github.com/itsiramk/TheGalleryApp/blob/master/videoplay.png">  
