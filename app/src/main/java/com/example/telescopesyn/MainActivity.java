package com.example.telescopesyn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //*Имеется отличный астрономический телескоп стоимостью 14 000 серебряных
    //монет (далее монет)
    //На счету имеется 1 000 монет и ежемесячно поступает стипендия в размере
    //2 500 монет из которых все средства можно отложить на телескоп
    //Ваши накопления хранятся в банке под 5 % годовых

    private int telescope = 14_000;
    private int account = 1_000;
    private int scholarship = 2_500;
    private int percentFree = 0;
    private int percentBank = 5;
    private double[] time = new double[60];

    public double TelescopePriceWithContribution() {
        return telescope - account;
    }

    public double TelescopeCost(double amount, int percent) {
        return (amount * percent) / 100;
    }

    public int CountMonth(double total, double telescopeCost, double percentBankYear) {
        double percentBankMonth = percentBankYear / 12;
        int count = 0;

        while (total > 0) {
            count++;
            total = (total + (total * percentBankMonth) / 100) - telescopeCost;
            if (total > telescopeCost) {
                time[count - 1] = telescopeCost;
            } else {
                time[count - 1] = total;
            }
        }
        return count;
    }

    private TextView countOut;
    private TextView manyMonthOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countOut = findViewById(R.id.countOut);
        manyMonthOut = findViewById(R.id.manyMonthOut);

        countOut.setText("Вы будете платить за телескоп " +
                CountMonth(TelescopePriceWithContribution(),
                TelescopeCost(scholarship, percentFree), percentBank)
                + " месяцев");

        String timeList = "";
        for(double list : time) {
            if (list > 0) {
                timeList = timeList + Double.toString(list) + " монет";
            } else {
                break;
            }
        }

        manyMonthOut.setText("Первоначальный взнос " + account +
                " монет, ежемесячные выпалты " + timeList);
    }
}