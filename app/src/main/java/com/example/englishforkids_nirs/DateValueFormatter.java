package com.example.englishforkids_nirs;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateValueFormatter extends ValueFormatter {

    private final SimpleDateFormat dateFormat;

    // Конструктор с более удобным форматом для отображения
    public DateValueFormatter() {
        dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault()); // Формат: 12 Dec
    }

    @Override
    public String getFormattedValue(float value) {
        return dateFormat.format(new Date((long) value)); // Преобразуем float в дату
    }
}
