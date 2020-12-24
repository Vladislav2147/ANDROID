package by.bstu.vs.stpms.lablistsqlite.model.database;

public final class DatabaseContract {

    public static final int     DATABASE_VERSION    = 8;
    public static final String  DATABASE_NAME       = "LabListDB";
    private static final String INT_TYPE            = " INTEGER";
    private static final String TEXT_TYPE           = " TEXT";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class TermTable {

        public static final String TABLE_NAME       = "terms";
        public static final String COLUMN_ID        = "id";
        public static final String COLUMN_COURSE    = "course";
        public static final String COLUMN_SEMESTER  = "semester";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME      + " (" +
                COLUMN_ID       + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE   + INT_TYPE + "NOT NULL, " +
                COLUMN_SEMESTER + INT_TYPE + "NOT NULL, " +
                " UNIQUE(" + COLUMN_COURSE + ", " + COLUMN_SEMESTER + "))";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class SubjectTable {

        public static final String TABLE_NAME       = "subjects";
        public static final String COLUMN_ID        = "id";
        public static final String COLUMN_NAME      = "name";
        public static final String COLUMN_TERM_ID   = "term_id";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME          + " (" +
                COLUMN_ID           + INT_TYPE  + " PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME         + TEXT_TYPE + "NOT NULL, " +
                COLUMN_TERM_ID      + INT_TYPE  + "NOT NULL, " +
                " FOREIGN KEY ("    + COLUMN_TERM_ID + ")" +
                " REFERENCES "      + TermTable.TABLE_NAME + "(" + TermTable.COLUMN_ID +") " +
                " ON UPDATE CASCADE ON DELETE CASCADE, " +
                " UNIQUE(" + COLUMN_NAME + ", " + COLUMN_TERM_ID + "))";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class LabTable {

        public static final String TABLE_NAME               = "labs";
        public static final String COLUMN_ID                = "id";
        public static final String COLUMN_NAME              = "name";
        public static final String COLUMN_SUBJECT_ID        = "subject_id";
        public static final String COLUMN_TASK_PATH         = "task_path";
        public static final String COLUMN_CODE_REFERENCE    = "code_reference";
        public static final String COLUMN_NOTES             = "notes";
        public static final String COLUMN_IS_PASSED         = "is_passed";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME              + " (" +
                COLUMN_ID               + INT_TYPE   + " PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME             + TEXT_TYPE  + "NOT NULL, " +
                COLUMN_SUBJECT_ID       + INT_TYPE   + "NOT NULL, " +
                COLUMN_TASK_PATH        + TEXT_TYPE  + ", " +
                COLUMN_CODE_REFERENCE   + TEXT_TYPE  + ", " +
                COLUMN_NOTES            + TEXT_TYPE  + ", " +
                COLUMN_IS_PASSED        + INT_TYPE   + "NOT NULL CHECK(" + COLUMN_IS_PASSED + " in (0,1)), " +
                " FOREIGN KEY ("        + COLUMN_SUBJECT_ID + ")" +
                " REFERENCES "          + SubjectTable.TABLE_NAME + "(" + SubjectTable.COLUMN_ID +") " +
                " ON UPDATE CASCADE ON DELETE CASCADE," +
                " UNIQUE(" + COLUMN_NAME + ", " + COLUMN_SUBJECT_ID + "))";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}