package com.example.unidirectionalstateflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private ArrayAdapter<String> adapter;
    private ViewModel viewModel = new ViewModel();
    private Observable<IUserEvent> mergedIUserEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       ListView listView = findViewById(R.id.list_view);
       listView.setAdapter(adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable<IUserEvent.SearchInputEvent> searchInputEvent = RxTextView.textChanges(findViewById(R.id.search_field))
                .skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(text -> new IUserEvent.SearchInputEvent(text.toString()));

        Observable<IUserEvent.AddNewTextEvent> newTextEvent = RxView.clicks(findViewById(R.id.add_button))
                .withLatestFrom(searchInputEvent, Pair::new)
                .map(pair -> new IUserEvent.AddNewTextEvent(pair.second.toString()));

        mergedIUserEvent = Observable.merge(searchInputEvent, newTextEvent);
        subscriptions.add(mergedIUserEvent
                .subscribe(viewModel::processInput));

//        subscriptions.add(viewModel.render(searchInputEvent, newTextEvent)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(viewState -> {
//                    updateAdapter(viewState.getNames());
//                    ((TextView) findViewById(R.id.number_field)).setText(viewState.getCountOfNames());
//                }));
    }

    private void updateAdapter(List<String> names) {
        adapter.clear();
        adapter.addAll(names);
    }

    private void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
