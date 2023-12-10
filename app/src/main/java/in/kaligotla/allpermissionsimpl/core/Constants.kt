package `in`.kaligotla.allpermissionsimpl.core

import android.provider.CalendarContract
import android.provider.CallLog
import android.provider.ContactsContract
import `in`.kaligotla.allpermissionsimpl.data.domain.model.entities.Permission
import java.util.UUID

object Constants {
    //App
    const val TAG = "AppTag"

    //Main Screen
    const val HOME = "Home"

    //Navigation Drawer
    const val MY_HOME_SCREEN = "MyHome"
    const val MY_BODY_SENSORS_SCREEN = "MyBodySensors"
    const val MY_CALENDAR_SCREEN = "MyCalendar"
    const val MY_CALL_LOGS_SCREEN = "MyCallLogs"
    const val MY_CAMERA_SCREEN = "MyCamera"
    const val MY_CONTACTS_SCREEN = "MyContacts"
    const val MY_LOCATION_SCREEN = "MyLocation"
    const val MY_MICROPHONE_SCREEN = "MyMicrophone"
    const val MY_MUSIC_AUDIO_SCREEN = "MyMusicAudio"
    const val MY_NEARBY_DEVICES_SCREEN = "MyNearbyDevices"
    const val MY_NOTIFICATIONS_SCREEN = "MyNotifications"
    const val MY_PHONE_SCREEN = "MyPhone"
    const val MY_PHOTOS_VIDEOS_SCREEN = "MyPhotosVideos"
    const val MY_PHYSICAL_ACTIVITY_SCREEN = "MyPhysicalActivity"
    const val MY_SMS_SCREEN = "MySMS"
    const val MY_BIOMETRICS_SCREEN = "MyBiometrics"

    const val EXTRAS_RETROFIT_BUILD = "retrofitBuild"
    const val PERMISSION_SERVICE = "permissionService"
    const val PERMISSION_REMOTE_DATA_SOURCE = "permissionRemoteDataSource"
    const val LOAD_DATA_SERVICE = "loadDataService"

    //Permission request code
    val CALL_LOG_PROJECTION = arrayOf(
        CallLog.Calls.NUMBER,
        CallLog.Calls.CACHED_NAME,
        CallLog.Calls.DATE,
        CallLog.Calls.TYPE,
        CallLog.Calls.DURATION,
    )

    const val PROJECTION_CALL_NUMBER_INDEX = 0
    const val PROJECTION_CALL_NAME_INDEX = 1
    const val PROJECTION_CALL_DATE_INDEX = 2
    const val PROJECTION_CALL_TYPE_INDEX = 3
    const val PROJECTION_CALL_DURATION_INDEX = 4

    //SQLite queries
    const val SQLITE_COUNT_QUERY = "SELECT count(*) FROM permission"

    val PERMISSIONS_ARRAY = arrayOf(
        //Camera
        Permission(1, "android.permission.CAMERA", false),

        //Location
        Permission(2, "android.permission.ACCESS_FINE_LOCATION", false),
        Permission(3, "android.permission.ACCESS_COARSE_LOCATION", false),

        //Microphone
        Permission(4, "android.permission.RECORD_AUDIO", false),

        //Photos and videos
        Permission(5, "android.permission.ACCESS_MEDIA_LOCATION", false),
        Permission(6, "android.permission.READ_MEDIA_IMAGES", false),
        Permission(7, "android.permission.READ_MEDIA_VIDEO", false),

        //SMS
        Permission(8, "android.permission.RECEIVE_MMS", false),
        Permission(9, "android.permission.RECEIVE_SMS", false),
        Permission(10, "android.permission.SEND_SMS", false),
        Permission(11, "android.permission.RECEIVE_WAP_PUSH", false),
        Permission(12, "android.permission.READ_SMS", false),

        //Physical activity
        Permission(13, "android.permission.ACTIVITY_RECOGNITION", false),

        //Body Sensors
        Permission(14, "android.permission.BODY_SENSORS", false),

        //Call logs
        Permission(15, "android.permission.READ_CALL_LOG", false),
        Permission(16, "android.permission.WRITE_CALL_LOG", false),
        Permission(17, "android.permission.PROCESS_OUTGOING_CALLS", false),

        //Contacts
        Permission(18, "android.permission.READ_CONTACTS", false),
        Permission(19, "android.permission.WRITE_CONTACTS", false),
        Permission(20, "android.permission.GET_ACCOUNTS", false),

        //Calendar
        Permission(21, "android.permission.READ_CALENDAR", false),
        Permission(22, "android.permission.WRITE_CALENDAR", false),

        //Music and audio
        Permission(23, "android.permission.READ_MEDIA_AUDIO", false),

        //Nearby devices
        Permission(24, "android.permission.BLUETOOTH_SCAN", false),
        Permission(25, "android.permission.BLUETOOTH_CONNECT", false),
        Permission(26, "android.permission.BLUETOOTH_ADVERTISE", false),
        Permission(27, "android.permission.NEARBY_WIFI_DEVICES", false),
        Permission(28, "android.permission.UWB_RANGING", false),

        //Notification
        Permission(29, "android.permission.POST_NOTIFICATIONS", false),

        //Phone
        Permission(30, "android.permission.ANSWER_PHONE_CALLS", false),
        Permission(31, "android.permission.READ_PHONE_NUMBERS", false),
        Permission(32, "android.permission.READ_PHONE_STATE", false),
        Permission(33, "android.permission.CALL_PHONE", false),
        Permission(34, "android.permission.ACCEPT_HANDOVER", false),
        Permission(35, "android.permission.USE_SIP", false),
        Permission(36, "com.android.voicemail.permission.ADD_VOICEMAIL", false),

        /*Permissions needs to granted in settings*/
        //Permission(37, "android.permission.BODY_SENSORS_BACKGROUND", false),
        //Permission(38, "android.permission.ACCESS_BACKGROUND_LOCATION", false),

        /*Common just add to manifest no require of user permission for below*/
        Permission(39, "android.permission.ACCESS_NOTIFICATION_POLICY", false),
        Permission(40, "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS", false),
        Permission(41, "android.permission.ACCESS_WIFI_STATE", false),
        Permission(42, "android.permission.ACCESS_NETWORK_STATE", false),
        Permission(43, "android.permission.BLUETOOTH", false),
        Permission(44, "android.permission.BLUETOOTH_ADMIN", false),
        Permission(45, "android.permission.BROADCAST_STICKY", false),
        Permission(46, "android.permission.CALL_COMPANION_APP", false),
        Permission(47, "android.permission.CHANGE_WIFI_STATE", false),
        Permission(48, "android.permission.CHANGE_WIFI_MULTICAST_STATE", false),
        Permission(49, "android.permission.CHANGE_NETWORK_STATE", false),
        Permission(50, "android.permission.DELIVER_COMPANION_MESSAGES", false),
        Permission(51, "android.permission.DISABLE_KEYGUARD", false),
        Permission(52, "android.permission.EXPAND_STATUS_BAR", false),
        Permission(53, "android.permission.FOREGROUND_SERVICE", false),
        Permission(54, "android.permission.GET_PACKAGE_SIZE", false),
        Permission(55, "android.permission.GET_TASKS", false),
        Permission(56, "android.permission.HIDE_OVERLAY_WINDOWS", false),
        Permission(57, "android.permission.HIGH_SAMPLING_RATE_SENSORS", false),
        Permission(58, "android.permission.INTERNET", false),
        Permission(59, "com.android.launcher.permission.INSTALL_SHORTCUT", false),
        Permission(60, "android.permission.KILL_BACKGROUND_PROCESSES", false),
        Permission(61, "android.permission.MODIFY_AUDIO_SETTINGS", false),
        Permission(62, "android.permission.MANAGE_OWN_CALLS", false),
        Permission(63, "android.permission.NFC_TRANSACTION_EVENT", false),
        Permission(64, "android.permission.NFC", false),
        Permission(65, "android.permission.NFC_PREFERRED_PAYMENT_INFO", false),
        Permission(66, "android.permission.PERSISTENT_ACTIVITY", false),
        Permission(67, "android.permission.READ_BASIC_PHONE_STATE", false),
        Permission(68, "android.permission.RESTART_PACKAGES", false),
        Permission(69, "android.permission.REQUEST_PASSWORD_COMPLEXITY", false),
        Permission(70, "android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE", false),
        Permission(71, "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS", false),
        Permission(72, "android.permission.REQUEST_DELETE_PACKAGES", false),
        Permission(73, "android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND", false),
        Permission(
        74,
            "android.permission.REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND",
            false
        ),
        Permission(75, "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND", false),
        Permission(76, "android.permission.REQUEST_COMPANION_PROFILE_WATCH", false),
        Permission(77, "android.permission.REORDER_TASKS", false),
        Permission(78, "android.permission.RECEIVE_BOOT_COMPLETED", false),
        Permission(79, "android.permission.READ_SYNC_STATS", false),
        Permission(80, "android.permission.READ_SYNC_SETTINGS", false),
        Permission(81, "android.permission.SET_WALLPAPER_HINTS", false),
        Permission(82, "android.permission.SET_WALLPAPER", false),
        Permission(83, "com.android.alarm.permission.SET_ALARM", false),
        Permission(84, "android.permission.SCHEDULE_EXACT_ALARM", false),
        Permission(85, "android.permission.TRANSMIT_IR", false),
        Permission(86, "android.permission.USE_FINGERPRINT", false),
        Permission(87, "android.permission.USE_FULL_SCREEN_INTENT", false),
        Permission(88, "android.permission.USE_EXACT_ALARM", false),
        Permission(89, "android.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION", false),
        Permission(90, "android.permission.USE_BIOMETRIC", false),
        Permission(91, "com.android.launcher.permission.UNINSTALL_SHORTCUT", false),
        Permission(92, "android.permission.VIBRATE", false),
        Permission(93, "android.permission.WRITE_SYNC_SETTINGS", false),
        Permission(94, "android.permission.WAKE_LOCK", false),

        /*Problem with below*/
        Permission(95, "android.permission.MANAGE_EXTERNAL_STORAGE", false),
//        Permission(96, "android.permission.REQUEST_INSTALL_PACKAGES", false),
//        Permission(97, "android.permission.REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION", false),
//        Permission(98, "android.permission.READ_VOICEMAIL", false),
//        Permission(99, "android.permission.READ_ASSISTANT_APP_SEARCH_DATA", false),
        Permission(100, "android.permission.READ_EXTERNAL_STORAGE", false),
//        Permission(101, "android.permission.READ_HOME_APP_SEARCH_DATA", false),
//        Permission(102, "android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE", false),
//        Permission(103, "android.permission.SYSTEM_ALERT_WINDOW", false),
//        Permission(104, "android.permission.WRITE_VOICEMAIL", false),
        Permission(105, "android.permission.WRITE_EXTERNAL_STORAGE", false),
    )
}