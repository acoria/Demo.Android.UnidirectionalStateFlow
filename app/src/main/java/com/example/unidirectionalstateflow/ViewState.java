package com.example.unidirectionalstateflow;


import java.util.List;

public class ViewState implements IViewState{

    private List<String> names;

    public ViewState(List<String> names) {
        this.names = names;
    }

    public ViewState(IViewState oldViewState){
        this.names = oldViewState.getNames();
    }

    @Override
    public List<String> getNames() {
        return names;
    }

    @Override
    public int getCountOfNames() {
        return names.size();
    }
}
