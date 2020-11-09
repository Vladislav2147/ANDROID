package by.bstu.svs.stpms.myrecipes.manager;

import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final  int    DATABASE_VERSION    = 7;
    public static final  String DATABASE_NAME       = "Recipes";
    private static final String INT_TYPE            = " INTEGER";
    private static final String TEXT_TYPE           = " TEXT";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class RecipeTable implements BaseColumns {
        public static final String TABLE_NAME               = "recipe_table";
        public static final String COLUMN_NAME_ID           = "id";
        public static final String COLUMN_NAME_TITLE        = "title";
        public static final String COLUMN_NAME_CATEGORY     = "category";
        public static final String COLUMN_NAME_INGREDIENTS  = "ingredients";
        public static final String COLUMN_NAME_STEPS        = "steps";
        public static final String COLUMN_NAME_PICTURE      = "picture";
        public static final String COLUMN_NAME_TIME_HOURS   = "hours";
        public static final String COLUMN_NAME_TIME_MINUTES = "minutes";
        public static final String COLUMN_NAME_IS_FAVORITE  = "is_favorite";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_NAME_ID              + INT_TYPE  + " PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME_TITLE           + TEXT_TYPE + "," +
                COLUMN_NAME_CATEGORY        + TEXT_TYPE  + "," +
                COLUMN_NAME_INGREDIENTS     + TEXT_TYPE + "," +
                COLUMN_NAME_STEPS           + TEXT_TYPE + "," +
                COLUMN_NAME_PICTURE         + TEXT_TYPE + "," +
                COLUMN_NAME_TIME_HOURS      + INT_TYPE  + "," +
                COLUMN_NAME_TIME_MINUTES    + INT_TYPE  + "," +
                COLUMN_NAME_IS_FAVORITE     + INT_TYPE  + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}