package by.bstu.vs.stpms.lablistsqlite.model.observable;

import java.util.function.Consumer;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class SimpleCompletableObserver implements CompletableObserver {

    private Action onComplete;
    private Consumer<Throwable> onError;

    public SimpleCompletableObserver(Action onComplete, Consumer<Throwable> onError) {
        this.onComplete = onComplete;
        this.onError = onError;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {
        try {
            onComplete.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onError.accept(e);
    }
}
