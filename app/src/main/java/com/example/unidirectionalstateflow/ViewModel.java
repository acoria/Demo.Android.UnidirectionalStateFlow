package com.example.unidirectionalstateflow;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class ViewModel {
    private static final String TAG = ViewModel.class.getSimpleName();
    private IViewState viewState = new ViewState();
    private Observable<String> searchInputEvent;
    private Observable<String> newTextEvent;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private PublishSubject<IUserEvent> eventEmitter = PublishSubject.create();


    public Observable<IViewState> render(
            Observable<String> searchInputEvent,
            Observable<String> newTextEvent
    ){
        this.searchInputEvent = searchInputEvent;
        subscriptions.add(this.searchInputEvent
                .subscribe(searchInput -> handleSearchInput(searchInput)));

        this.newTextEvent = newTextEvent;
        subscriptions.add(this.newTextEvent
                .subscribe(newText -> handleNewText(newText)));

        ObservableTransformer<IUserEvent, IResult> userEventToResult =
                events -> events.publish(sharedEvents -> Observable.merge(
                        sharedEvents.ofType(IUserEvent.AddNewTextEvent.class).compose(onNewText())
                ))

        eventEmitter
                .doOnNext(input -> Log.d(TAG, "received a user input"))
                //map userEvent to result
                .map(this::mapUserEventToResult)
                .share()
                .map(this::mapResultToViewState);


        return null;
    }

    public void processInput(IUserEvent userEvent){
        eventEmitter.onNext(userEvent);
    }

    private IResult mapUserEventToResult(IUserEvent userEvent){
        IResult result = null;
        if(userEvent instanceof IUserEvent.AddNewTextEvent){
            result = onNewText();
        }else if(userEvent instanceof IUserEvent.SearchInputEvent) {
            result = onSearchInput();
        }
        return result;
    }
    private ViewState mapResultToViewState(IResult result){

        if(result instanceof IResult.AddToListResult){

        }

        ViewState newViewState = new ViewState(viewState)
                ;

        viewState = Observable.just(result)
                    .;

        return new ViewState();
    }

    private void handleNewText(String newText) {
        names.add(newText);
    }

    private IResult.AddToListResult onNewText(){
        //TODO: logic on how to show loading, add a text etc.

        return null;
    }

    private void handleSearchInput(String searchInput) {
        names.add(0,searchInput);
    }

    private IResult.SearchInputResult onSearchInput(){
        //TODO
        return null;
    }


}
