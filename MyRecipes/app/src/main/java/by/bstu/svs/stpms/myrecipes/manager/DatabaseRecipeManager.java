package by.bstu.svs.stpms.myrecipes.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import by.bstu.svs.stpms.myrecipes.manager.exception.SQLiteDatabaseException;
import by.bstu.svs.stpms.myrecipes.model.Category;
import by.bstu.svs.stpms.myrecipes.model.Recipe;
import by.bstu.svs.stpms.myrecipes.model.Time;

public final class DatabaseRecipeManager {

    private static DatabaseRecipeManager instance;
    private SQLiteDatabase database;

    public static DatabaseRecipeManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseRecipeManager();
        }
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context);
        instance.database = openHelper.getReadableDatabase();
        return instance;
    }

    public void add(Recipe recipe) throws SQLiteDatabaseException {
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TITLE, recipe.getTitle());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_CATEGORY, recipe.getCategory().getName());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_INGREDIENTS, recipe.getIngredients());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_STEPS, recipe.getSteps());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_PICTURE, recipe.getPicture());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_HOURS, recipe.getTimeToCook().getHours());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_MINUTES, recipe.getTimeToCook().getMinutes());

        if (database.insert(DatabaseContract.RecipeTable.TABLE_NAME, null, values) == -1) {
            throw new SQLiteDatabaseException("insert failed");
        }
    }

    public Recipe getById(Integer id) throws SQLiteDatabaseException {

        Cursor cursor = database.query(
                DatabaseContract.RecipeTable.TABLE_NAME,
                null,
                "id == ?",
                new String[] {id == null ? "null" : id.toString()},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            return getRecipeByCursor(cursor);
        } else {
            throw new SQLiteDatabaseException("id " + id + " not found");
        }

    }

    public List<Recipe> getAll() throws SQLiteDatabaseException {

        List<Recipe> recipes = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.RecipeTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
           do {
               recipes.add(getRecipeByCursor(cursor));
           } while (cursor.moveToNext());
           return recipes;
        } else {
            throw new SQLiteDatabaseException("database error");
        }
    }

    public void update(Recipe recipe) throws SQLiteDatabaseException {

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_ID, recipe.getId());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TITLE, recipe.getTitle());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_CATEGORY, recipe.getCategory().getName());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_INGREDIENTS, recipe.getIngredients());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_STEPS, recipe.getSteps());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_PICTURE, recipe.getPicture());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_HOURS, recipe.getTimeToCook().getHours());
        values.put(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_MINUTES, recipe.getTimeToCook().getMinutes());

        int updated = database.update(
                DatabaseContract.RecipeTable.TABLE_NAME,
                values,
                "id == ?",
                new String[] {recipe.getId() == null ? "null" : recipe.getId().toString()}
        );

        if (updated == 0) {
            throw new SQLiteDatabaseException("no rows updated");
        }

    }

    public void delete(Integer id) throws SQLiteDatabaseException {

        int deleted = database.delete(
                DatabaseContract.RecipeTable.TABLE_NAME,
                "id == ?",
                new String[] {id == null ? "null" : id.toString()}
        );

        if (deleted == 0) {
            throw new SQLiteDatabaseException("no rows deleted");
        }

    }

    public Recipe getRecipeByCursor(Cursor cursor) {

        Recipe recipe = new Recipe();

        int id = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_ID);
        int title = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_TITLE);
        int category = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_CATEGORY);
        int ingredients = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_INGREDIENTS);
        int steps = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_STEPS);
        int picture = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_PICTURE);
        int hours = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_HOURS);
        int minutes = cursor.getColumnIndex(DatabaseContract.RecipeTable.COLUMN_NAME_TIME_MINUTES);

        recipe.setId(cursor.getInt(id));
        recipe.setTitle(cursor.getString(title));
        recipe.setCategory(Category.getEnumFromString(cursor.getString(category)));
        recipe.setIngredients(cursor.getString(ingredients));
        recipe.setSteps(cursor.getString(steps));
        recipe.setPicture(cursor.getString(picture));
        recipe.setTimeToCook(new Time(cursor.getInt(hours), cursor.getInt(minutes)));

        return recipe;

    }

    public Cursor getCursorByQuery(String selection, String[] selectionArgs, String orderBy) {
        return database.query(DatabaseContract.RecipeTable.TABLE_NAME, null, selection, selectionArgs, null, null, orderBy);
    }

}
