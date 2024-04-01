package com.example.unidirectionalstateflow;

public interface IUserEvent {

    final class SearchInputEvent implements IUserEvent{
        private String userInputText;

        public SearchInputEvent(String userInputText) {
            this.userInputText = userInputText;
        }

        public String getUserInputText() {
            return userInputText;
        }
    }

    final class AddNewTextEvent implements IUserEvent{
        private String newText;

        public AddNewTextEvent(String newText) {
            this.newText = newText;
        }

        public String getNewText() {
            return newText;
        }
    }
}
