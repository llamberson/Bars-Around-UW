# Bars-Around-UW
Application that shows user top 10 highest rated, lowest cost bars within a 2 mile radius to any of the 3 UW campuses.

PROJECT NAME: "Bars Around UW"


CLIENT/PROJECT: UW Tacoma, TCSS 450 A (Summer, '15)


DEVELOPERS: Ankit Sabhaya & Luke Lamberson


——————————DESCRIPTION——————————


This application displays bars around any of the three University of Washington campuses,
within a 2-mile radius. The user can login as a guest or via their Facebook credentials. 
User must have previously set up a Facebook account to use the Facebook login, or create a new one 
via the Facebook app link. The application then displays information regarding the user-selected bar, 
including: address, phone number, description and photo(s).


——————————TECHNOLOGIES USED——————————


This application requires Android 4.0 or higher, uses the Facebook SDK, and Google Play services. 
The emulator/device must have Google Play services installed for the map function of this app to work properly, 
and for the pictures of the bars to display. The user must have previously set up a Facebook account in order to 
use the "Facebook login" option. Facebook SDK provides a link to "Create New Account" for those that wish to 
create a new Facebook account via the app. Database stores user favorites. We also implemented JUnit test case(s) 
for our model class, and Robotium test case(s) for our Login Activity and Menu Activity.


——————————FEATURES IMPLEMENTED——————————


- Facebook Login
- Guest Login 
- Campus Selection
- Bars List View
- Bar Details
- Add Bar to Favorites List (Facebook user login only)
- Call Bar Function
- View Favorite Bar List
- Share Bar comment on User's Facebook page


——————————LIST OF USE CASES IMPLEMENTED——————————


        NAME OF USE CASE: Finding bar by map location
        Actor(s): Poor & Thirsty Student with High Expectations (aka User)
        Flow of Events: 
1.        User initiates application.
2.        User enters Facebook login credentials or chooses ‘Guest Login”.
3.        Users who have selected the ‘Guest Login’ option will still be able to use the app, 
          but won't be able to share or save to the favorites list. 
4.        User selects a UW campus location from different campus buttons. 
5.        User looks at bar locations as points on ranked list of bar names. 
6.        User selects a bar on from the list.
        User is presented with details including: Name of bar, brief description of bar, and address for selected bar.


        NAME OF USE CASE: Favoriting a selected bar
        Actor(s): Poor & Thirsty Student with High Expectations (aka User)
        Flow of events:
1.        User initiates application
2.        User enters Facebook login credentials or Login as a guest.
3.        User selects a UW campus location from different bar buttons.
4.        User looks at bar on list view. 
5.        User selects desired bar from list.
6.        User selects “Favorite” (Star Shaped) button.
7.        Item is added to favorites list
8.        User can access the list of their favorite bars.
9.        User can add/remove a favorite bar from their list.


        NAME OF CASE: Sharing a bar with Facebook friends
        Actor(s): Poor & Thirsty Student with High Expectations (aka User)
        Flow of events:
1.        User initiates application
2.        User enters Facebook login credentials
3.        User selects a UW campus location from drop down menu
4.        User looks at bar on list view
5.        User selects desired bar from list
6.        User selects “Share” button
7.        Selected bar is shared with User’s list of Facebook friends which will include 
          details about the bar (name, address, description) and a message from User to friends NOTE: 
          Sharing Detail feature is not working currently still waiting on Facebook to give us permission. 
          But the personal message feature is working. 


        NAME OF CASE: Calling a Selected Bar
        Actor(s): Poor & Thirsty Student with High Expectations (aka User)
        Flow of events:
1.        User initiates application
2.        User enters Facebook login credentials
3.        User selects a UW campus location from drop down menu
4.        User looks at bar on list view
5.        User selects desired bar from list
6.        User selects “Phone” button
7.        Call is made to selected bar.




*******************************NOTES************************************
<<IMPORTANT>> The emulator that we used and all tests completed successfully is: 
Google Nexus 4 - 4.3 - API 18 running Google Play Services. We used the ARM translation 1.1 and 
flashed gapps version 4.3. A good tutorial for this setup can be found at this link: 
	http://inthecheesefactory.com/blog/how-to-install-google-services-on-genymotion/en

- We tested this on two different HTC One M9 phones, as well as on multiple variations of emulators 
with different builds. The pictures on some of the builds and one of the M9s didn’t show up, oddly enough. 
This seems to be a problem with Google Services combined with various build versions.
