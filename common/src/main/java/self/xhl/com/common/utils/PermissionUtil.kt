package self.xhl.com.common.utils

/**
 * @author xhl
 * @desc  权限工具类
 * 2018/10/25 18:14
 */
class PermissionUtil {

    companion object {
        /**
         * 通讯录
         */
        val PERMISSION_WRITE_CONTACTS = "android.permission.WRITE_CONTACTS"
        val PERMISSION_GET_ACCOUNTS = "android.permission.GET_ACCOUNTS"
        val PERMISSION_READ_CONTACTS = "android.permission.READ_CONTACTS"

        /**
         * 电话
         */
        val PERMISSION_READ_CALL_LOG = "android.permission.READ_CALL_LOG"
        val PERMISSION_READ_PHONE_STATE = "android.permission.READ_PHONE_STATE"
        val PERMISSION_CALL_PHONE = "android.permission.CALL_PHONE"
        val PERMISSION_WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG"
        val PERMISSION_USE_SIP = "android.permission.USE_SIP"
        val PERMISSION_PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS"
        val PERMISSION_ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL"
        /**
         * 相机
         */
        val PERMISSION_CAMERA = "android.permission.CAMERA"

        /**
         * 日历
         */
        val PERMISSION_READ_CALENDAR = "android.permission.READ_CALENDAR"
        val PERMISSION_WRITE_CALENDAR = "android.permission.WRITE_CALENDAR"

        /**
         * 传感器
         */
        val PERMISSION_BODY_SENSORS = "android.permission.BODY_SENSORS"

        /**
         * 麦克风
         */
        val PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO"

        /**
         * 位置
         */
        val PERMISSION_ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION"
        const val PERMISSION_ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION"

          /**
         * 存储
         */
        val PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"
        val PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE"

        /**
         * 短信
         */
        val PERMISSION_READ_SMS = "android.permission.READ_SMS"
        val PERMISSION_RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH"
        val PERMISSION_RECEIVE_MMS = "android.permission.RECEIVE_MMS"
        val PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS"
        val PERMISSION_SEND_SMS = "android.permission.SEND_SMS"
        val PERMISSION_READ_CELL_BROADCASTS = "android.permission.READ_CELL_BROADCASTS"



        private val contactArray = mutableListOf(PERMISSION_WRITE_CONTACTS, PERMISSION_GET_ACCOUNTS, PERMISSION_READ_CONTACTS)
        private val phoneArray = mutableListOf(PERMISSION_READ_CALL_LOG, PERMISSION_READ_PHONE_STATE, PERMISSION_CALL_PHONE,
                PERMISSION_WRITE_CALL_LOG, PERMISSION_USE_SIP, PERMISSION_PROCESS_OUTGOING_CALLS, PERMISSION_ADD_VOICEMAIL)
        private val camaraArray = mutableListOf(PERMISSION_CAMERA)
        private val recordRedioArray = mutableListOf(PERMISSION_RECORD_AUDIO)
        private val sensorArray = mutableListOf(PERMISSION_BODY_SENSORS)
        private val calendarArray = mutableListOf(PERMISSION_READ_CALENDAR, PERMISSION_WRITE_CALENDAR)
        private val locationArray = mutableListOf(PERMISSION_ACCESS_FINE_LOCATION, PERMISSION_ACCESS_COARSE_LOCATION)
        private val storageArray = mutableListOf(PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE)
        private val smsArray = mutableListOf(PERMISSION_READ_SMS, PERMISSION_RECEIVE_WAP_PUSH,PERMISSION_RECEIVE_MMS,
                PERMISSION_RECEIVE_SMS,PERMISSION_SEND_SMS,PERMISSION_READ_CELL_BROADCASTS)


        fun getPermissionTip(mPermission: String?): String {
            for (permission in contactArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.ContactTip.value
            }
            for (permission in phoneArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.PhoneTip.value
            }
            for (permission in camaraArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.CamreaTip.value
            }
            for (permission in calendarArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.CalendarTip.value
            }
            for (permission in sensorArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.SensorsTip.value
            }
            for (permission in locationArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.LocationTip.value
            }
            for (permission in storageArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.StorageTip.value
            }
            for (permission in recordRedioArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.MicrophoneTip.value
            }
            for (permission in smsArray) {
                if (permission.equals(mPermission))
                    return PermissionTip.SmsTip.value
            }
            return "未知权限"
        }
    }

    enum class PermissionTip(val value: String) {
        ContactTip("通讯录权限"),
        CamreaTip("相机权限"),
        PhoneTip("电话权限"),
        CalendarTip("日历权限"),
        SensorsTip("传感器权限"),
        LocationTip("位置权限"),
        StorageTip("存储权限"),
        MicrophoneTip("麦克风权限"),
        SmsTip("短信权限")
    }
}
