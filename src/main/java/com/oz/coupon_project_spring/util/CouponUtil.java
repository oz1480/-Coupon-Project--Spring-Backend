package com.oz.coupon_project_spring.util;

import com.oz.coupon_project_spring.entities.Coupon;
import com.oz.coupon_project_spring.enums.Category;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

public class CouponUtil {

    public static void decreaseCouponAmount(final Coupon coupon) {
        int prevAmount = coupon.getAmount();
        coupon.setAmount(prevAmount - 1);
    }


    public static boolean isCouponExpired(final LocalDate date) {
        return date.isBefore(LocalDate.now());
    }


    public static boolean isCouponDateValid(final LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startMonth, int endMonth) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(startMonth, endMonth);
        int year = createRandomIntBetween(2022, 2022);
        return LocalDate.of(year, month, day);
    }

    public static Category randomCategory(){
        int pick = new Random().nextInt(Category.values().length);
        return Category.values()[pick];
    }

}
