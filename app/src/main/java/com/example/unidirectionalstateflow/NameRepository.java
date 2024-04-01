package com.example.unidirectionalstateflow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class NameRepository {

    BehaviorSubject<List<String>> names = BehaviorSubject.createDefault(resetNames());

    public NameRepository() {

    }

    public Observable<List<String>> addAName(String name){
        names.getValue().add(name);
        names.onNext(names.getValue());
        return names.hide();
    }

//    public

    private List<String> resetNames(){
        List<String> names = new ArrayList<>();
        names.add("Stacey");
        names.add("Jimmy");
        names.add("Shawn");
        return names;
    }
}
