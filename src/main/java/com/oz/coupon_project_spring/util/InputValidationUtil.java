package com.oz.coupon_project_spring.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationUtil {
        private final static String PASSWORD_REGEX = "[a-zA-Z0-9]{4,12}";
        private final static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        private final static String NAME_REGEX = "[a-zA-Z ,.'-]{2,}";
        private final static String DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        // --------------------------------Password validation-----------------------------------

        public static boolean isPasswordValid (String password) {

            if (password.matches(PASSWORD_REGEX)) {
                return true;
            }
            return false;
        }

        //----------------------------------------------------------------------------------------

        // --------------------------------Email validation---------------------------------------

        public static boolean isEmailValid (String email) {

            // This line converts the regex code into the desired pattern
            Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

            System.out.println(emailPattern);

            System.out.println(email);
            //This line checks if the inserted email follows the previously created pattern
            Matcher matcher = emailPattern.matcher(email);

            return matcher.find();
        }

        // --------------------------------Name validation---------------------------------------

        public static boolean isNameValid (String name) {

            if (name.matches(NAME_REGEX)) {
                return true;
            }
            return false;
        }

        // --------------------------------Date validation---------------------------------------

        public static boolean isDateValid (String date) {

            if (date.matches(DATE_REGEX)) {
                return true;
            }
            return false;
        }

        //----------------------------------------------------------------------------------------
    }
