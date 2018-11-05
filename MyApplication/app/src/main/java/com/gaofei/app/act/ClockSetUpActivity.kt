package com.gaofei.app.act

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.gaofei.app.R
import com.gaofei.library.ProjectApplication
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import com.gaofei.library.utils.TextUtils
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_clock_set_up.*
import kotlinx.android.synthetic.main.layout_cock_set_up_week.view.*
import java.text.SimpleDateFormat
import java.util.*

private var CLOCK_LOCAL_DATA = "clock_local_data"
private fun getPreferences(context: Context): String {
    val sp = context.getSharedPreferences(CLOCK_LOCAL_DATA, 0)
    return sp.getString(CLOCK_LOCAL_DATA, "")
}

private fun getLocalClockData(context: Context): ClockStatus {
    val clockStatusStr = getPreferences(context)
    return if (!TextUtils.isEmpty(clockStatusStr)) {
        val gson = GsonBuilder().create()
        gson.fromJson(clockStatusStr, ClockStatus::class.java)
    } else {
        ClockStatus(0, 0, 0, arrayListOf(0, 0, 0, 0, 0, 0, 0))
    }
}


private fun convertCalendarWeekToIndex(week: Int): Int {
    var index = week - 2
    if (week == Calendar.SUNDAY) {
        index = 6
    }
    return index
}

private fun calculateClockStartupTime(context: Context): Long {
    var clockFirstStartup = -1L
    val clockStatus = getLocalClockData(context)
    val calendar = Calendar.getInstance()
    val currTime = System.currentTimeMillis()
    calendar.timeInMillis = currTime
    val currWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val curWeekIndex = convertCalendarWeekToIndex(currWeek)
    LogUtils.d("getRecentlyClockStartup currWeek = $currWeek curWeekIndex = $curWeekIndex ${getDataFromate(currTime)} ${clockStatus.weekIndexList}")
    // 设置闹钟时间
    calendar.set(Calendar.AM_PM, clockStatus.AMOrPMIndex)
    calendar.set(Calendar.HOUR, clockStatus.hourIndex + 1)
    calendar.set(Calendar.MINUTE, clockStatus.minuteIndex)
    val currDate = calendar.get(Calendar.DATE)
    val clockTime = calendar.timeInMillis
    LogUtils.d("getRecentlyClockStartup currWeek  ${getDataFromate(clockTime)}")
    var aheadDay = 0
    if (clockStatus.weekIndexList[curWeekIndex] == 1 && clockTime > currTime) {
        clockFirstStartup = clockTime
    } else {
        for (tempIndex in curWeekIndex until curWeekIndex + 7) {
            val realWeekIndex = tempIndex % 7
            calendar.set(Calendar.DATE, currDate + aheadDay)
            val tempTime = calendar.timeInMillis
            LogUtils.d("getRecentlyClockStartup realWeekIndex $realWeekIndex ${getDataFromate(tempTime)}")
            if (clockStatus.weekIndexList[realWeekIndex] == 1 && tempTime > clockTime) {
                clockFirstStartup = tempTime
                break
            }
            aheadDay += 1
        }
    }
    return clockFirstStartup
}

private fun getDataFromate(time: Long): String {
    val format = SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE")
    return format.format(time)
}


data class ClockExecuteTimeInfo(val startTime: Long, val intervalTime: Long)

class ClockSetUpActivity : BaseAct() {
    private var mHourArr = arrayOfNulls<String>(12)
    private var mMinuteArr = arrayOfNulls<String>(60)
    private val mAMOrPMArr = kotlin.arrayOfNulls<String>(2)
    private lateinit var mWeekViewList: ArrayList<View>
    private lateinit var mLocalClockStatus: ClockStatus
    private lateinit var mCurrClockStatus: ClockStatus
    private var mWeekText = arrayListOf<String>("周一", "周二", "周三", "周四", "周五", "周六", "周日")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_set_up)
        init()
        initClockView()
        initWeekView()
        confirm.setOnClickListener {
            saveClockStatus()
            ClockStartActivity.intentTo(this)
        }
    }

    private fun init() {
        for (a in 1..mHourArr.size) {
            mHourArr[a - 1] = "$a"
        }
        for (a in 0 until mMinuteArr.size) {
            mMinuteArr[a] = "$a"
        }
        mAMOrPMArr[0] = "上午"
        mAMOrPMArr[1] = "下午"
        mLocalClockStatus = getLocalClockData(this)
        mCurrClockStatus = mLocalClockStatus
    }


    private fun putLocalClockData(clockStatus: ClockStatus) {
        val gson = GsonBuilder().create()
        val str = gson.toJson(clockStatus)
        putPreferences(str)
    }


    private fun putPreferences(str: String) {
        val sp = this.getSharedPreferences(CLOCK_LOCAL_DATA, 0)
        sp.edit().putString(CLOCK_LOCAL_DATA, str).commit()
    }

    private fun getPreferences(): String {
        val sp = this.getSharedPreferences(CLOCK_LOCAL_DATA, 0)
        return sp.getString(CLOCK_LOCAL_DATA, "")
    }

    private fun initClockView() {
        initClockAMOrPMView()
        initClockHourView()
        initClockMinuteView()
    }

    private fun initClockAMOrPMView() {
        initClockView(numberPickerAMOrPM, mAMOrPMArr, mCurrClockStatus.AMOrPMIndex)
        numberPickerAMOrPM.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrClockStatus.AMOrPMIndex = newVal
        }
    }

    private fun initClockHourView() {
        initClockView(numberPickerHour, mHourArr, mCurrClockStatus.hourIndex)
        numberPickerHour.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrClockStatus.hourIndex = newVal
        }
    }

    private fun initClockMinuteView() {
        initClockView(numberPickerMinute, mMinuteArr, mCurrClockStatus.minuteIndex)
        numberPickerMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            mCurrClockStatus.minuteIndex = newVal
        }
    }

    private fun initClockView(numberPicker: NumberPicker, array: Array<String?>, selectedIndex: Int) {
        numberPicker.displayedValues = array
        numberPicker.minValue = 0
        numberPicker.maxValue = array.size - 1
        numberPicker.value = selectedIndex
        setNumberPickerDescendantFocusability(numberPicker)
    }

    private fun setNumberPickerDescendantFocusability(numberPicker: NumberPicker) {
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    }

    private fun initWeekView() {
        mWeekViewList = arrayListOf(week1, week2, week3, week4, week5, week6, week7)
        mWeekViewList.forEachIndexed { index, weekView ->
            weekView.weekText.text = mWeekText[index]
            weekView.weekCheckBox.setOnCheckedChangeListener { weekCheckBox, isChecked ->
                mCurrClockStatus.weekIndexList[index] = if (isChecked) 1 else 0
            }
            weekView.weekCheckBox.isChecked = mCurrClockStatus.weekIndexList[index] != 0
        }
    }

    private fun saveClockStatus() {
        putLocalClockData(mCurrClockStatus)
        ClockProcessor.run()
    }


    companion object {

        fun intentTo(context: Context) {
            val intent = Intent(context, ClockSetUpActivity::class.java)
            context.startActivity(intent)
        }
    }


}

data class ClockStatus(var AMOrPMIndex: Int = 0, var hourIndex: Int = 0, var minuteIndex: Int = 0, var weekIndexList: MutableList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0))

object ClockProcessor : Runnable {
    override fun run() {
        triggerClock()
    }

    private fun triggerClock() {
        val context = ProjectApplication.getContext()
        val clockStartTime = calculateClockStartupTime(context)
        LogUtils.d("triggerClock currTime = ${getDataFromate(System.currentTimeMillis())}  clockStartTime = ${getDataFromate(clockStartTime)} ")
        LogUtils.d("triggerClock currTime = ${System.currentTimeMillis()}")
        LogUtils.d("triggerClock clockStartTime = ${clockStartTime}")
        if (clockStartTime < 1) {
            return
        }
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(AlarmManager.OnAlarmListener {

        })
        val pendingIntent = getClockPendingIntent(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            am.setWindow(AlarmManager.RTC_WAKEUP, clockStartTime, 2 * 60 * 1000, pendingIntent)
//            am.set(AlarmManager.RTC_WAKEUP, clockStartTime, pendingIntent)
            am.setExact(AlarmManager.RTC_WAKEUP, clockStartTime + 1000*60, pendingIntent)
        } else {
            am.set(AlarmManager.RTC_WAKEUP, clockStartTime, pendingIntent)
        }
    }

    private fun getClockPendingIntent(context: Context): PendingIntent {
        val intent = Intent()
        intent.action = ClockBroadcastReceiver.ACTION
        return PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}


