package `in`.kaligotla.allpermissionsimpl.navigation

import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_BIOMETRICS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_BODY_SENSORS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_CALENDAR_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_CALL_LOGS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_CAMERA_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_CONTACTS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_HOME_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_LOCATION_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_MICROPHONE_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_MUSIC_AUDIO_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_NEARBY_DEVICES_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_NOTIFICATIONS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_PHONE_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_PHOTOS_VIDEOS_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_PHYSICAL_ACTIVITY_SCREEN
import `in`.kaligotla.allpermissionsimpl.core.Constants.MY_SMS_SCREEN

sealed class Screen(val route: String) {
    object MyHomeScreen : Screen(MY_HOME_SCREEN)
    object MyBodySensorsScreen : Screen(MY_BODY_SENSORS_SCREEN)
    object MyCalendarScreen : Screen(MY_CALENDAR_SCREEN)
    object MyCallLogsScreen : Screen(MY_CALL_LOGS_SCREEN)
    object MyCameraScreen : Screen(MY_CAMERA_SCREEN)
    object MyContactsScreen : Screen(MY_CONTACTS_SCREEN)
    object MyLocationScreen : Screen(MY_LOCATION_SCREEN)
    object MyMicrophoneScreen : Screen(MY_MICROPHONE_SCREEN)
    object MyMusicAudioScreen : Screen(MY_MUSIC_AUDIO_SCREEN)
    object MyNearbyDevicesScreen : Screen(MY_NEARBY_DEVICES_SCREEN)
    object MyNotificationsScreen : Screen(MY_NOTIFICATIONS_SCREEN)
    object MyPhoneScreen : Screen(MY_PHONE_SCREEN)
    object MyPhotosVideosScreen : Screen(MY_PHOTOS_VIDEOS_SCREEN)
    object MyPhysicalActivityScreen : Screen(MY_PHYSICAL_ACTIVITY_SCREEN)
    object MySMSScreen : Screen(MY_SMS_SCREEN)
    object MyBiometricsScreen : Screen(MY_BIOMETRICS_SCREEN)
}