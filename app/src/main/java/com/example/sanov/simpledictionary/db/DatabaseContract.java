package com.example.sanov.simpledictionary.db;

import android.provider.BaseColumns;

/**
 * Created by sanov on 3/12/2018.
 */

public class DatabaseContract {

    public static String TABLE_ENG_TO_IND = "table_eng_to_ind";

    public static String TABLE_IND_TO_ENG = "table_ind_to_eng";

    public static final class EngToIndColumns implements BaseColumns {
        public static String KEYWORDS = "words";
        public static String BODY = "body";
    }

    public static final class IndToEngColumns implements  BaseColumns {
        public static String KATAKUNCI = "katakunci";
        public static String ISI = "isi";
    }
}
