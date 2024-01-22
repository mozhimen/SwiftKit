package com.mozhimen.basick.manifestk.cons

import android.Manifest
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode


/**
 * @ClassName Permission
 * @Description

 * AndroidManifest.xml (example)

<uses-permission android:name="android.permission.ACCEPT_HANDOVER" />

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/11 12:13
 * @Version 1.0
 */
object CPermission {
    //region # protect permission
    const val READ_PRIVILEGED_PHONE_STATE = "android.permission.READ_PRIVILEGED_PHONE_STATE"
    const val REPLACE_EXISTING_PACKAGE = "android.permission.REPLACE_EXISTING_PACKAGE"
    const val READ_INSTALL_SESSIONS = "android.permission.READ_INSTALL_SESSIONS"
    const val FLASHLIGHT = "android.permission.FLASHLIGHT"
    const val GET_INSTALLED_APPS = "com.android.permission.GET_INSTALLED_APPS"
    //endregion

    @RequiresApi(CVersCode.V_28_9_P)
    const val ACCEPT_HANDOVER = Manifest.permission.ACCEPT_HANDOVER

    @RequiresApi(CVersCode.V_29_10_Q)
    const val ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION

    @RequiresApi(CVersCode.V_31_12_S)
    const val ACCESS_BLOBS_ACROSS_USERS = Manifest.permission.ACCESS_BLOBS_ACROSS_USERS
    const val ACCESS_CHECKIN_PROPERTIES = Manifest.permission.ACCESS_CHECKIN_PROPERTIES
    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS

    @RequiresApi(CVersCode.V_29_10_Q)
    const val ACCESS_MEDIA_LOCATION = Manifest.permission.ACCESS_MEDIA_LOCATION
    const val ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE

    @RequiresApi(CVersCode.V_23_6_M)
    const val ACCESS_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY
    const val ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE
    const val ACCOUNT_MANAGER = Manifest.permission.ACCOUNT_MANAGER

    @RequiresApi(CVersCode.V_29_10_Q)
    const val ACTIVITY_RECOGNITION = Manifest.permission.ACTIVITY_RECOGNITION
    const val ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL"

    @RequiresApi(CVersCode.V_26_8_O)
    const val ANSWER_PHONE_CALLS = Manifest.permission.ANSWER_PHONE_CALLS
    const val BATTERY_STATS = Manifest.permission.BATTERY_STATS
    const val BIND_ACCESSIBILITY_SERVICE = Manifest.permission.BIND_ACCESSIBILITY_SERVICE
    const val BIND_APPWIDGET = Manifest.permission.BIND_APPWIDGET

    @RequiresApi(CVersCode.V_26_8_O)
    const val BIND_AUTOFILL_SERVICE = Manifest.permission.BIND_AUTOFILL_SERVICE

    @RequiresApi(CVersCode.V_29_10_Q)
    const val BIND_CALL_REDIRECTION_SERVICE = Manifest.permission.BIND_CALL_REDIRECTION_SERVICE

    @RequiresApi(CVersCode.V_29_10_Q)
    const val BIND_CARRIER_MESSAGING_CLIENT_SERVICE = Manifest.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE

    @RequiresApi(CVersCode.V_22_51_L1)
    @Deprecated("android.permission.BIND_CARRIER_MESSAGING_SERVICE")
    val BIND_CARRIER_MESSAGING_SERVICE = Manifest.permission.BIND_CARRIER_MESSAGING_SERVICE

    @RequiresApi(CVersCode.V_23_6_M)
    const val BIND_CARRIER_SERVICES = Manifest.permission.BIND_CARRIER_SERVICES

    @RequiresApi(CVersCode.V_23_6_M)
    @Deprecated("android.permission.BIND_CHOOSER_TARGET_SERVICE")
    val BIND_CHOOSER_TARGET_SERVICE = Manifest.permission.BIND_CHOOSER_TARGET_SERVICE

    @RequiresApi(CVersCode.V_31_12_S)
    const val BIND_COMPANION_DEVICE_SERVICE = Manifest.permission.BIND_COMPANION_DEVICE_SERVICE

    @RequiresApi(CVersCode.V_24_7_N)
    const val BIND_CONDITION_PROVIDER_SERVICE = Manifest.permission.BIND_CONDITION_PROVIDER_SERVICE

    @RequiresApi(CVersCode.V_30_11_R)
    const val BIND_CONTROLS = Manifest.permission.BIND_CONTROLS
    const val BIND_DEVICE_ADMIN = Manifest.permission.BIND_DEVICE_ADMIN
    const val BIND_DREAM_SERVICE = Manifest.permission.BIND_DREAM_SERVICE

    @RequiresApi(CVersCode.V_23_6_M)
    const val BIND_INCALL_SERVICE = Manifest.permission.BIND_INCALL_SERVICE
    const val BIND_INPUT_METHOD = Manifest.permission.BIND_INPUT_METHOD

    @RequiresApi(CVersCode.V_23_6_M)
    const val BIND_MIDI_DEVICE_SERVICE = Manifest.permission.BIND_MIDI_DEVICE_SERVICE
    const val BIND_NFC_SERVICE = Manifest.permission.BIND_NFC_SERVICE
    const val BIND_NOTIFICATION_LISTENER_SERVICE = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    const val BIND_PRINT_SERVICE = Manifest.permission.BIND_PRINT_SERVICE

    @RequiresApi(CVersCode.V_30_11_R)
    const val BIND_QUICK_ACCESS_WALLET_SERVICE = Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE

    @RequiresApi(CVersCode.V_24_7_N)
    const val BIND_QUICK_SETTINGS_TILE = Manifest.permission.BIND_QUICK_SETTINGS_TILE
    const val BIND_REMOTEVIEWS = Manifest.permission.BIND_REMOTEVIEWS

    @RequiresApi(CVersCode.V_24_7_N)
    const val BIND_SCREENING_SERVICE = Manifest.permission.BIND_SCREENING_SERVICE

    @RequiresApi(CVersCode.V_23_6_M)
    const val BIND_TELECOM_CONNECTION_SERVICE = Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE
    const val BIND_TEXT_SERVICE = Manifest.permission.BIND_TEXT_SERVICE
    const val BIND_TV_INPUT = Manifest.permission.BIND_TV_INPUT

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val BIND_TV_INTERACTIVE_APP = Manifest.permission.BIND_TV_INTERACTIVE_APP

    @RequiresApi(CVersCode.V_26_8_O)
    const val BIND_VISUAL_VOICEMAIL_SERVICE = Manifest.permission.BIND_VISUAL_VOICEMAIL_SERVICE
    const val BIND_VOICE_INTERACTION = Manifest.permission.BIND_VOICE_INTERACTION
    const val BIND_VPN_SERVICE = Manifest.permission.BIND_VPN_SERVICE

    @RequiresApi(CVersCode.V_24_7_N)
    const val BIND_VR_LISTENER_SERVICE = Manifest.permission.BIND_VR_LISTENER_SERVICE
    const val BIND_WALLPAPER = Manifest.permission.BIND_WALLPAPER
    const val BLUETOOTH = Manifest.permission.BLUETOOTH
    const val BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN

    @RequiresApi(CVersCode.V_31_12_S)
    const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE

    @RequiresApi(CVersCode.V_31_12_S)
    const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
    const val BLUETOOTH_PRIVILEGED = Manifest.permission.BLUETOOTH_PRIVILEGED

    @RequiresApi(CVersCode.V_31_12_S)
    const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
    const val BODY_SENSORS = Manifest.permission.BODY_SENSORS

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val BODY_SENSORS_BACKGROUND = Manifest.permission.BODY_SENSORS_BACKGROUND
    const val BROADCAST_PACKAGE_REMOVED = Manifest.permission.BROADCAST_PACKAGE_REMOVED
    const val BROADCAST_SMS = Manifest.permission.BROADCAST_SMS
    const val BROADCAST_STICKY = Manifest.permission.BROADCAST_STICKY
    const val BROADCAST_WAP_PUSH = Manifest.permission.BROADCAST_WAP_PUSH

    @RequiresApi(CVersCode.V_29_10_Q)
    const val CALL_COMPANION_APP = Manifest.permission.CALL_COMPANION_APP
    const val CALL_PHONE = Manifest.permission.CALL_PHONE
    const val CALL_PRIVILEGED = Manifest.permission.CALL_PRIVILEGED
    const val CAMERA = Manifest.permission.CAMERA
    const val CAPTURE_AUDIO_OUTPUT = Manifest.permission.CAPTURE_AUDIO_OUTPUT
    const val CHANGE_COMPONENT_ENABLED_STATE = Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE
    const val CHANGE_CONFIGURATION = Manifest.permission.CHANGE_CONFIGURATION
    const val CHANGE_NETWORK_STATE = Manifest.permission.CHANGE_NETWORK_STATE
    const val CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
    const val CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE
    const val CLEAR_APP_CACHE = Manifest.permission.CLEAR_APP_CACHE
    const val CONTROL_LOCATION_UPDATES = Manifest.permission.CONTROL_LOCATION_UPDATES
    const val DELETE_CACHE_FILES = Manifest.permission.DELETE_CACHE_FILES
    const val DELETE_PACKAGES = Manifest.permission.DELETE_PACKAGES

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val DELIVER_COMPANION_MESSAGES = Manifest.permission.DELIVER_COMPANION_MESSAGES
    const val DIAGNOSTIC = Manifest.permission.DIAGNOSTIC
    const val DISABLE_KEYGUARD = Manifest.permission.DISABLE_KEYGUARD
    const val DUMP = Manifest.permission.DUMP
    const val EXPAND_STATUS_BAR = Manifest.permission.EXPAND_STATUS_BAR
    const val FACTORY_TEST = Manifest.permission.FACTORY_TEST

    @RequiresApi(CVersCode.V_28_9_P)
    const val FOREGROUND_SERVICE = Manifest.permission.FOREGROUND_SERVICE
    const val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS

    @RequiresApi(CVersCode.V_23_6_M)
    const val GET_ACCOUNTS_PRIVILEGED = Manifest.permission.GET_ACCOUNTS_PRIVILEGED
    const val GET_PACKAGE_SIZE = Manifest.permission.GET_PACKAGE_SIZE

    @Deprecated("android.permission.GET_TASKS")
    const val GET_TASKS = Manifest.permission.GET_TASKS
    const val GLOBAL_SEARCH = Manifest.permission.GLOBAL_SEARCH

    @RequiresApi(CVersCode.V_31_12_S)
    const val HIDE_OVERLAY_WINDOWS = Manifest.permission.HIDE_OVERLAY_WINDOWS

    @RequiresApi(CVersCode.V_31_12_S)
    const val HIGH_SAMPLING_RATE_SENSORS = Manifest.permission.HIGH_SAMPLING_RATE_SENSORS
    const val INSTALL_LOCATION_PROVIDER = Manifest.permission.INSTALL_LOCATION_PROVIDER

    /**
     * 特别的，如果是静默安装，则需要INSTALL_PACKAGES权限（注意：INSTALL_PACKAGES权限是针对于系统应用的，换言之，想要实现静默安装，那你得要是系统应用）
     */
    const val INSTALL_PACKAGES = Manifest.permission.INSTALL_PACKAGES
    const val INSTALL_SHORTCUT = "com.android.launcher.permission.INSTALL_SHORTCUT"

    @RequiresApi(CVersCode.V_26_8_O)
    const val INSTANT_APP_FOREGROUND_SERVICE = Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE

    @RequiresApi(CVersCode.V_30_11_R)
    const val INTERACT_ACROSS_PROFILES = Manifest.permission.INTERACT_ACROSS_PROFILES
    const val INTERNET = Manifest.permission.INTERNET
    const val KILL_BACKGROUND_PROCESSES = Manifest.permission.KILL_BACKGROUND_PROCESSES

    @RequiresApi(CVersCode.V_32_12_S_V2)
    const val LAUNCH_MULTI_PANE_SETTINGS_DEEP_LINK = Manifest.permission.LAUNCH_MULTI_PANE_SETTINGS_DEEP_LINK

    @RequiresApi(CVersCode.V_30_11_R)
    const val LOADER_USAGE_STATS = Manifest.permission.LOADER_USAGE_STATS
    const val LOCATION_HARDWARE = Manifest.permission.LOCATION_HARDWARE
    const val MANAGE_DOCUMENTS = Manifest.permission.MANAGE_DOCUMENTS

    @RequiresApi(CVersCode.V_30_11_R)
    const val MANAGE_EXTERNAL_STORAGE = Manifest.permission.MANAGE_EXTERNAL_STORAGE

    @RequiresApi(CVersCode.V_31_12_S)
    const val MANAGE_MEDIA = Manifest.permission.MANAGE_MEDIA

    @RequiresApi(CVersCode.V_31_12_S)
    const val MANAGE_ONGOING_CALLS = Manifest.permission.MANAGE_ONGOING_CALLS

    @RequiresApi(CVersCode.V_26_8_O)
    const val MANAGE_OWN_CALLS = Manifest.permission.MANAGE_OWN_CALLS

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val MANAGE_WIFI_INTERFACES = Manifest.permission.MANAGE_WIFI_INTERFACES

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val MANAGE_WIFI_NETWORK_SELECTION = Manifest.permission.MANAGE_WIFI_NETWORK_SELECTION
    const val MASTER_CLEAR = Manifest.permission.MASTER_CLEAR
    const val MEDIA_CONTENT_CONTROL = Manifest.permission.MEDIA_CONTENT_CONTROL
    const val MODIFY_AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS
    const val MODIFY_PHONE_STATE = Manifest.permission.MODIFY_PHONE_STATE
    const val MOUNT_FORMAT_FILESYSTEMS = Manifest.permission.MOUNT_FORMAT_FILESYSTEMS
    const val MOUNT_UNMOUNT_FILESYSTEMS = Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val NEARBY_WIFI_DEVICES = Manifest.permission.NEARBY_WIFI_DEVICES
    const val NFC = Manifest.permission.NFC

    @RequiresApi(CVersCode.V_30_11_R)
    const val NFC_PREFERRED_PAYMENT_INFO = Manifest.permission.NFC_PREFERRED_PAYMENT_INFO

    @RequiresApi(CVersCode.V_28_9_P)
    const val NFC_TRANSACTION_EVENT = Manifest.permission.NFC_TRANSACTION_EVENT

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val OVERRIDE_WIFI_CONFIG = Manifest.permission.OVERRIDE_WIFI_CONFIG

    @RequiresApi(CVersCode.V_23_6_M)
    const val PACKAGE_USAGE_STATS = Manifest.permission.PACKAGE_USAGE_STATS

    @Deprecated("android.permission.PERSISTENT_ACTIVITY")
    val PERSISTENT_ACTIVITY = Manifest.permission.PERSISTENT_ACTIVITY

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS

    @Deprecated("android.permission.PROCESS_OUTGOING_CALLS")
    val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS

    @RequiresApi(CVersCode.V_30_11_R)
    const val QUERY_ALL_PACKAGES = Manifest.permission.QUERY_ALL_PACKAGES

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_ASSISTANT_APP_SEARCH_DATA = Manifest.permission.READ_ASSISTANT_APP_SEARCH_DATA

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_BASIC_PHONE_STATE = Manifest.permission.READ_BASIC_PHONE_STATE
    const val READ_CALENDAR = Manifest.permission.READ_CALENDAR
    const val READ_CALL_LOG = Manifest.permission.READ_CALL_LOG
    const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_HOME_APP_SEARCH_DATA = Manifest.permission.READ_HOME_APP_SEARCH_DATA

    @Deprecated("android.permission.READ_INPUT_STATE")
    val READ_INPUT_STATE = Manifest.permission.READ_INPUT_STATE
    const val READ_LOGS = Manifest.permission.READ_LOGS

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_MEDIA_AUDIO = Manifest.permission.READ_MEDIA_AUDIO

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_MEDIA_VIDEO = Manifest.permission.READ_MEDIA_VIDEO

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val READ_NEARBY_STREAMING_POLICY = Manifest.permission.READ_NEARBY_STREAMING_POLICY

    @RequiresApi(CVersCode.V_26_8_O)
    const val READ_PHONE_NUMBERS = Manifest.permission.READ_PHONE_NUMBERS
    const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

    @RequiresApi(CVersCode.V_30_11_R)
    const val READ_PRECISE_PHONE_STATE = Manifest.permission.READ_PRECISE_PHONE_STATE
    const val READ_SMS = Manifest.permission.READ_SMS
    const val READ_SYNC_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS
    const val READ_SYNC_STATS = Manifest.permission.READ_SYNC_STATS
    const val READ_VOICEMAIL = "com.android.voicemail.permission.READ_VOICEMAIL"
    const val REBOOT = Manifest.permission.REBOOT
    const val RECEIVE_BOOT_COMPLETED = Manifest.permission.RECEIVE_BOOT_COMPLETED
    const val RECEIVE_MMS = Manifest.permission.RECEIVE_MMS
    const val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
    const val RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH
    const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
    const val REORDER_TASKS = Manifest.permission.REORDER_TASKS

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val REQUEST_COMPANION_PROFILE_APP_STREAMING = Manifest.permission.REQUEST_COMPANION_PROFILE_APP_STREAMING

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION = Manifest.permission.REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val REQUEST_COMPANION_PROFILE_COMPUTER = Manifest.permission.REQUEST_COMPANION_PROFILE_COMPUTER

    @RequiresApi(CVersCode.V_31_12_S)
    const val REQUEST_COMPANION_PROFILE_WATCH = Manifest.permission.REQUEST_COMPANION_PROFILE_WATCH

    @RequiresApi(CVersCode.V_26_8_O)
    const val REQUEST_COMPANION_RUN_IN_BACKGROUND = Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val REQUEST_COMPANION_SELF_MANAGED = Manifest.permission.REQUEST_COMPANION_SELF_MANAGED

    @RequiresApi(CVersCode.V_31_12_S)
    const val REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND = Manifest.permission.REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND

    @RequiresApi(CVersCode.V_26_8_O)
    const val REQUEST_COMPANION_USE_DATA_IN_BACKGROUND = Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND

    @RequiresApi(CVersCode.V_26_8_O)
    const val REQUEST_DELETE_PACKAGES = Manifest.permission.REQUEST_DELETE_PACKAGES

    @RequiresApi(CVersCode.V_23_6_M)
    const val REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS

    @RequiresApi(CVersCode.V_23_6_M)
    const val REQUEST_INSTALL_PACKAGES = Manifest.permission.REQUEST_INSTALL_PACKAGES

    @RequiresApi(CVersCode.V_31_12_S)
    const val REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE = Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE

    @RequiresApi(CVersCode.V_29_10_Q)
    const val REQUEST_PASSWORD_COMPLEXITY = Manifest.permission.REQUEST_PASSWORD_COMPLEXITY

    @Deprecated("android.permission.RESTART_PACKAGES")
    val RESTART_PACKAGES = Manifest.permission.RESTART_PACKAGES

    @RequiresApi(CVersCode.V_31_12_S)
    const val SCHEDULE_EXACT_ALARM = Manifest.permission.SCHEDULE_EXACT_ALARM
    const val SEND_RESPOND_VIA_MESSAGE = Manifest.permission.SEND_RESPOND_VIA_MESSAGE
    const val SEND_SMS = Manifest.permission.SEND_SMS
    const val SET_ALARM = "com.android.alarm.permission.SET_ALARM"
    const val SET_ALWAYS_FINISH = Manifest.permission.SET_ALWAYS_FINISH
    const val SET_ANIMATION_SCALE = Manifest.permission.SET_ANIMATION_SCALE
    const val SET_DEBUG_APP = Manifest.permission.SET_DEBUG_APP

    @Deprecated("android.permission.SET_PREFERRED_APPLICATIONS")
    val SET_PREFERRED_APPLICATIONS = Manifest.permission.SET_PREFERRED_APPLICATIONS
    const val SET_PROCESS_LIMIT = Manifest.permission.SET_PROCESS_LIMIT
    const val SET_TIME = Manifest.permission.SET_TIME
    const val SET_TIME_ZONE = Manifest.permission.SET_TIME_ZONE
    const val SET_WALLPAPER = Manifest.permission.SET_WALLPAPER
    const val SET_WALLPAPER_HINTS = Manifest.permission.SET_WALLPAPER_HINTS
    const val SIGNAL_PERSISTENT_PROCESSES = Manifest.permission.SIGNAL_PERSISTENT_PROCESSES

    @RequiresApi(CVersCode.V_29_10_Q)
    @Deprecated("android.permission.SMS_FINANCIAL_TRANSACTIONS")
    val SMS_FINANCIAL_TRANSACTIONS = Manifest.permission.SMS_FINANCIAL_TRANSACTIONS

    @RequiresApi(CVersCode.V_31_12_S)
    const val START_FOREGROUND_SERVICES_FROM_BACKGROUND = Manifest.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val START_VIEW_APP_FEATURES = Manifest.permission.START_VIEW_APP_FEATURES

    @RequiresApi(CVersCode.V_29_10_Q)
    const val START_VIEW_PERMISSION_USAGE = Manifest.permission.START_VIEW_PERMISSION_USAGE
    const val STATUS_BAR = Manifest.permission.STATUS_BAR

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE = Manifest.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE
    const val SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW
    const val TRANSMIT_IR = Manifest.permission.TRANSMIT_IR
    const val UNINSTALL_SHORTCUT = "com.android.launcher.permission.UNINSTALL_SHORTCUT"
    const val UPDATE_DEVICE_STATS = Manifest.permission.UPDATE_DEVICE_STATS

    @RequiresApi(CVersCode.V_31_12_S)
    const val UPDATE_PACKAGES_WITHOUT_USER_ACTION = Manifest.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION

    @RequiresApi(CVersCode.V_28_9_P)
    const val USE_BIOMETRIC = Manifest.permission.USE_BIOMETRIC

    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    const val USE_EXACT_ALARM = Manifest.permission.USE_EXACT_ALARM

    @RequiresApi(CVersCode.V_23_6_M)
    @Deprecated("android.permission.USE_FINGERPRINT")
    val USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT

    @RequiresApi(CVersCode.V_29_10_Q)
    const val USE_FULL_SCREEN_INTENT = Manifest.permission.USE_FULL_SCREEN_INTENT

    @RequiresApi(CVersCode.V_31_12_S)
    const val USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER = Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER
    const val USE_SIP = Manifest.permission.USE_SIP

    @RequiresApi(CVersCode.V_31_12_S)
    const val UWB_RANGING = Manifest.permission.UWB_RANGING
    const val VIBRATE = Manifest.permission.VIBRATE
    const val WAKE_LOCK = Manifest.permission.WAKE_LOCK
    const val WRITE_APN_SETTINGS = Manifest.permission.WRITE_APN_SETTINGS
    const val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR
    const val WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG
    const val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS
    const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val WRITE_GSERVICES = Manifest.permission.WRITE_GSERVICES
    const val WRITE_SECURE_SETTINGS = Manifest.permission.WRITE_SECURE_SETTINGS
    const val WRITE_SETTINGS = Manifest.permission.WRITE_SETTINGS
    const val WRITE_SYNC_SETTINGS = Manifest.permission.WRITE_SYNC_SETTINGS
    const val WRITE_VOICEMAIL = "com.android.voicemail.permission.WRITE_VOICEMAIL"
}