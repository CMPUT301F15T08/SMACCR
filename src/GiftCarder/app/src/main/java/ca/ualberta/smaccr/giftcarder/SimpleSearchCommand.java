/*
GiftCarder: Android App for trading gift cards

Copyright 2015 Carin Li, Ali Mirza, Spencer Plant, Michael Rijlaarsdam, Richard He, Connor Sheremeta

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
and limitations under the License.
*/


//From Joshua Charles Campbell, retrieved 2015-10-27, https://github.com/joshua2ua/AndroidElasticSearch.git
//From the elastic search lab

package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/16/15.
 */

public class SimpleSearchCommand {
    private SimpleSearchQuery query;

    public SimpleSearchCommand(String query) {
        super();
        this.query = new SimpleSearchQuery(query);
    }

    public SimpleSearchCommand(String query, String[] fields) {
        super();
        throw new UnsupportedOperationException("Fields not yet implemented.");
    }

    public SimpleSearchQuery getQuery() {
        return query;
    }

    public void setQuery(SimpleSearchQuery query) {
        this.query = query;
    }

    static class SimpleSearchQuery {
        private SimpleSearchQueryString queryString;

        public SimpleSearchQuery(String query) {
            super();
            this.queryString = new SimpleSearchQueryString(query);
        }

        public SimpleSearchQueryString getQueryString() {
            return queryString;
        }

        public void setQueryString(SimpleSearchQueryString queryString) {
            this.queryString = queryString;
        }

        static class SimpleSearchQueryString {
            private String query;

            public SimpleSearchQueryString(String query) {
                super();
                this.query = query;
            }

            public String getQuery() {
                return query;
            }

            public void setQuery(String query) {
                this.query = query;
            }
        }
    }
}