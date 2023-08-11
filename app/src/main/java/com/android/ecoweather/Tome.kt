package com.android.ecoweather;

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.animation.doOnStart
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import java.text.MessageFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale


class Tome : AppCompatActivity() {
    private lateinit var monthTitle: TextView
    private lateinit var yearTitle: TextView
    private lateinit var backButton: ImageButton
    private lateinit var timeButton: Button

    private lateinit var drawer: CheckBox

    private lateinit var calendarView: CalendarView
    private lateinit var weekCalendarView: WeekCalendarView
    private lateinit var titlesContainer: ViewGroup
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val months = arrayOf(
        "Jan",
        "Fev",
        "Mar",
        "Abr",
        "Mai",
        "Jun",
        "Jul",
        "Ago",
        "Set",
        "Out",
        "Nov",
        "Dez"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lembretes)

        drawer = findViewById(R.id.drawer)
        backButton = findViewById(R.id.backButton)

        calendarView = findViewById(R.id.calendarView)
        weekCalendarView = findViewById(R.id.weekCalendarView)

        titlesContainer = findViewById(R.id.titlesContainer)
        timeButton = findViewById(R.id.timeButton)
        monthTitle = findViewById(R.id.month_title)
        yearTitle = findViewById(R.id.year_title)

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100L)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(100L)  // Adjust as needed
        val daysOfWeek = daysOfWeek()

        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)
        setupWeekCalendar(startMonth, endMonth, currentMonth, daysOfWeek)

        titlesContainer.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                val dayOfWeek = daysOfWeek[index]
                val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                textView.text = title
            }

        calendarView.isInvisible = drawer.isChecked
        weekCalendarView.isInvisible = !drawer.isChecked

        backButton.setOnClickListener { onBackPressed() }
        timeButton.setOnClickListener { popTimePicker() }

        drawer.setOnCheckedChangeListener(weekModeToggled)
    }

    @SuppressLint("SetTextI18n")
    private fun scroll() {
        val isMonthMode = !drawer.isChecked
        if (isMonthMode) {
            val month = calendarView.findFirstVisibleMonth()?.yearMonth ?: return
            monthTitle.text = months[month.month.ordinal]

            if (month.year > today.year || month.year < today.year) {
                yearTitle.text = "${month.year}"
            } else {
                yearTitle.text = ""
            }
        } else {
            val week = weekCalendarView.findFirstVisibleWeek() ?: return
            // In week mode, we show the header a bit differently because
            // an index can contain dates from different months/years.
            val firstDate = week.days.first().date
            val lastDate = week.days.last().date

            if (firstDate.year > today.year || firstDate.year < today.year) {
                yearTitle.text = "${lastDate.year}"
            } else {
                yearTitle.text = ""
            }

            if (firstDate.yearMonth == lastDate.yearMonth) {
                monthTitle.text = months[firstDate.month.ordinal]
            } else {
                monthTitle.text =
                    months[firstDate.month.ordinal] + " - " + months[lastDate.month.ordinal]

                if (firstDate.year != lastDate.year) {
                    yearTitle.text = "${lastDate.year}"
                }
            }

        }
    }

    private fun setupWeekCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class WeekDayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)

            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: WeekDay

            init {
                view.setOnClickListener {
                    // Check the day position as we do not want to select in or out dates.
                    if (day.position == WeekDayPosition.RangeDate) {
                        dateClicked(day.date)
                    }
                }
            }
        }

        weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewContainer> {
            override fun create(view: View): WeekDayViewContainer = WeekDayViewContainer(view)
            override fun bind(container: WeekDayViewContainer, data: WeekDay) {

                val textView = container.textView
                // Set the calendar day for this container.
                container.day = data
                // Set the date text
                textView.text = data.date.dayOfMonth.toString()
                // Any other binding logic

                if (data.position == WeekDayPosition.RangeDate) {
                    if (data.date == today)
                        textView.setBackgroundResource(R.drawable.today_date)

                    if (data.date == selectedDate) {
                        // If this is the selected date, show a round background and change the text color.
                        textView.setBackgroundResource(R.drawable.container_user)
                        textView.setTextColor(Color.BLACK)
                    } else {
                        // If this is NOT the selected date, remove the background and reset the text color.

                        if (data.date != today)
                            textView.background = null

                        textView.setTextColor(Color.WHITE)
                    }
                } else {
                    container.textView.setTextColor(Color.LTGRAY)
                    textView.background = null
                }
            }
        }
        weekCalendarView.setup(startMonth.atStartOfMonth(), endMonth.atEndOfMonth(), daysOfWeek.first())

        weekCalendarView.scrollToWeek(currentMonth.atStartOfMonth())
        weekCalendarView.weekScrollListener = { scroll() }
    }


    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)

            // Will be set when this container is bound
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(day.date)
                    }
                }
            }
        }

        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {

                val textView = container.textView
                // Set the calendar day for this container.
                container.day = data
                // Set the date text
                textView.text = data.date.dayOfMonth.toString()
                // Any other binding logic


                if (data.position == DayPosition.MonthDate) {
                    if (data.date == today)
                        textView.setBackgroundResource(R.drawable.today_date)

                    if (data.date == selectedDate) {
                        // If this is the selected date, show a round background and change the text color.
                        textView.setBackgroundResource(R.drawable.container_user)
                        textView.setTextColor(Color.BLACK)
                    } else {
                        // If this is NOT the selected date, remove the background and reset the text color.

                        if (data.date != today)
                            textView.background = null

                        textView.setTextColor(Color.WHITE)
                    }
                } else {
                    container.textView.setTextColor(Color.LTGRAY)
                    textView.background = null
                }
            }
        }
        calendarView.setup(startMonth, endMonth, daysOfWeek.first())

        calendarView.scrollToMonth(currentMonth)
        calendarView.monthScrollListener = { scroll() }
    }

    private fun dateClicked(date: LocalDate) {
        // Keep a reference to any previous selection
        // in case we overwrite it and need to reload it.

        // Keep a reference to any previous selection
        // in case we overwrite it and need to reload it.
        val currentSelection = selectedDate
            selectedDate = date
            // Reload the newly selected date so the dayBinder is
            // called and we can ADD the selection background.
            calendarView.notifyDateChanged(date)
            weekCalendarView.notifyDateChanged(date)
            if (currentSelection != null) {
                // We need to also reload the previously selected
                // date so we can REMOVE the selection background.
                calendarView.notifyDateChanged(currentSelection)
                weekCalendarView.notifyDateChanged(currentSelection)
            }


    }

    private val weekModeToggled = object : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton, monthToWeek: Boolean) {
            // We want the first visible day to remain visible after the
            // change so we scroll to the position on the target calendar.

            if (monthToWeek) {
                val targetDate = calendarView.findFirstVisibleDay()?.date ?: return
                weekCalendarView.scrollToWeek(targetDate)
            } else {
                // It is possible to have two months in the visible week (30 | 31 | 1 | 2 | 3 | 4 | 5)
                // We always choose the second one. Please use what works best for your use case.
                val targetMonth = weekCalendarView.findLastVisibleDay()?.date?.yearMonth ?: return
                calendarView.scrollToMonth(targetMonth)
            }
            val weekHeight = weekCalendarView.height
            val visibleMonthHeight = weekHeight *
                    calendarView.findFirstVisibleMonth()?.weekDays.orEmpty().count()

            // If OutDateStyle is EndOfGrid, you could simply multiply weekHeight by 6.

            val oldHeight = if (monthToWeek) visibleMonthHeight else weekHeight
            val newHeight = if (monthToWeek) weekHeight else visibleMonthHeight


            // Animate calendar height changes.
            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
            animator.addUpdateListener { anim ->
                calendarView.updateLayoutParams {
                    height = anim.animatedValue as Int
                }
                // A bug is causing the month calendar to not redraw its children
                // with the updated height during animation, this is a workaround.
                calendarView.children.forEach { child ->
                    child.requestLayout()
                }
            }

            animator.doOnStart {
                if (!monthToWeek) {
                    weekCalendarView.isInvisible = true
                    calendarView.isVisible = true
                }
            }
            animator.doOnEnd {
                if (monthToWeek) {
                    weekCalendarView.isVisible = true
                    calendarView.isInvisible = true
                }
                scroll()
            }
            animator.duration = 250
            animator.start()
        }
    }


    private fun popTimePicker() {

        val sysFormat =
            if (DateFormat.is24HourFormat(this)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val hour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val minute = Calendar.getInstance()[Calendar.MINUTE]
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(sysFormat)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText("Select Date")
            .build()

        picker.addOnPositiveButtonClickListener {
            timeButton.text = MessageFormat.format(
                "{0}:{1}",
                String.format(Locale.getDefault(), "%02d", picker.hour),
                String.format(Locale.getDefault(), "%02d", picker.minute)
            )
        }
        picker.show(supportFragmentManager, picker.toString())
    }
}
