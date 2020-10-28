package by.bstu.svs.stpms.myrecipes.manager;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

import by.bstu.svs.stpms.myrecipes.model.Recipe;

public final class FirebaseManager {

    private static FirebaseManager instance;
    private final DatabaseReference databaseReference;
    private final FirebaseUser currentUser;
    private Long availableId;

    private FirebaseManager() {
        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        availableId = 0L;
//        addAvailableIdListener();
    }

    public void callOnRecipeById(String recipeId, Consumer<Recipe> recipeConsumer) {
        databaseReference
                .child(currentUser.getUid())
                .child(recipeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Recipe recipe = snapshot.getValue(Recipe.class);
                        recipeConsumer.accept(recipe);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });

    }

    public void appendToList(Recipe recipe) {
        DatabaseReference add = databaseReference
                .child(currentUser.getUid())
                .push();
        recipe.setId(add.getKey());
        add.setValue(recipe);
    }

//    private void addAvailableIdListener() {
//        databaseReference
//                .child(currentUser.getUid())
//                .orderByChild("id")
//                .limitToLast(1)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot recipeKey: snapshot.getChildren()) {
//                            Recipe recipe = recipeKey.getValue(Recipe.class);
//                            availableId = recipe.getId() + 1;
//                            break;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        throw error.toException();
//                    }
//                });
//    }

    public void update(Recipe recipe, DatabaseReference.CompletionListener completionListener) {
        DatabaseReference recipeReference = databaseReference
                .child(currentUser.getUid())
                .child(recipe.getId());
        recipeReference.setValue(recipe, completionListener);
    }
    public void delete(String recipeId, DatabaseReference.CompletionListener completionListener) {
        databaseReference
                .child(currentUser.getUid())
                .child(recipeId)
                .removeValue(completionListener);
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

}
