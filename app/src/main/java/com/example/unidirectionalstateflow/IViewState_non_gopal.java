package com.example.unidirectionalstateflow;

import java.util.List;

public interface IViewState_non_gopal {

    final class listNotLoadedYet implements IViewState_non_gopal {
    }

    final class emptyResult implements IViewState_non_gopal {
        private final String searchQueryText;

        public emptyResult(String searchQueryText) {
            this.searchQueryText = searchQueryText;
        }

        public String getSearchQueryText() {
            return searchQueryText;
        }
    }

    final class SearchResult implements IViewState_non_gopal {
        private final String searchQueryText;
        private final List<String> result;

        public SearchResult(String searchQueryText, List<String> result) {
            this.searchQueryText = searchQueryText;
            this.result = result;
        }

        public String getSearchQueryText() {
            return searchQueryText;
        }

        public List<String> getResult() {
            return result;
        }
    }

    final class Loading implements IViewState_non_gopal {
    }
    final class Error implements IViewState_non_gopal {
        private final String searchQueryText;
        private final Throwable error;

        public Error(String searchQueryText, Throwable error) {
            this.searchQueryText = searchQueryText;
            this.error = error;
        }

        public String getSearchQueryText() {
            return searchQueryText;
        }

        public Throwable getError() {
            return error;
        }
    }
}
