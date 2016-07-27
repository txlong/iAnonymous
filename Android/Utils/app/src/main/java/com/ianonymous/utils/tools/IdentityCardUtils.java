package com.ianonymous.utils.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ianonymous on 16/7/7.
 */

public class IdentityCardUtils {

    /**
     * 根据身份证计算一个人的年龄
     *
     * @param idNo 身份证号(15位或18位)
     */
    public static int calcAge(String idNo) {
        int age = -1;

        String year;
        String month;
        String day;
        //输入个数判断，初步验证身份证号码的真假
        if (idNo.length() == 15) {
            year = "19" + idNo.substring(6, 8); //出生的年份
            month = idNo.substring(8, 10); //出生的月份
            day = idNo.substring(10, 12); //出生的日期
        } else if (idNo.length() == 18) {
            year = idNo.substring(6, 10); //出生的年份
            month = idNo.substring(10, 12); //出生的月份
            day = idNo.substring(12, 14); //出生的日期
        } else {
            System.err.println("你的输入有误，请重新输入！");
            return age;
        }

        //计算年龄
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
            Date birthMonth = sdf.parse(month);
            Date nowMonth = sdf.parse(String.valueOf(new Date().getMonth()));
            age = new Date().getYear() - Integer.parseInt(year);

            if (nowMonth.compareTo(birthMonth) < 0) {
                age -= 1;
            }
            return age;
        } catch (Exception e) {
            System.err.println("a problem has happened!");
            return age;
        }
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        System.out.println("age:" + age);
        return age;
    }
}
