package com.example.mabna.ui.lastMovies.presentation.bottomSheet

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.mabna.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class DatePickerBottomSheet(private val onSelectDate: (String) -> Unit): BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener {
    var year=0
    var month=0
    var day=0
    var selectedDay=0
    var selectedYear=0
    var selectedMonth=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
      val dialog=  pickDate()

        val view=View.inflate(context, R.layout.date_picker_bottom_sheet, null)
        dialog.setContentView(view)
        return dialog
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

    }
    private fun pickDate():DatePickerDialog{
        getDateTimeCalender()
        var datePickerDialog= DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.maxDate = Date().time
        return datePickerDialog
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedDay=dayOfMonth
        selectedMonth=month
        var selectedDayStr=dayOfMonth.toString()
        var selectedMonthStr=month.toString()

        selectedYear=year
        if (selectedMonth<10) selectedMonthStr= "0" + selectedMonthStr
        if (selectedDay<10) selectedDayStr = "0" + selectedDayStr
        onSelectDate("$selectedYear-$selectedMonthStr-$selectedDayStr")

    }
    private fun getDateTimeCalender(){
        val calender= Calendar.getInstance()
        day=calender.get(Calendar.DAY_OF_MONTH)
        month=calender.get(Calendar.MONTH)
        year=calender.get(Calendar.YEAR)
    }
}