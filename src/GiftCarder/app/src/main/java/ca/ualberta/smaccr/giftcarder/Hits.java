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

package ca.ualberta.smaccr.giftcarder;

/**
 * Created by splant on 11/16/15.
 */

import java.util.List;

/*
//From Joshua Charles Campbell, retrieved 2015-10-27, https://github.com/joshua2ua/AndroidElasticSearch.git
//From the elastic search lab

Hit is template class that count number of hits of search results
 */

public class Hits<T> {
    private int total;
    private float max_score;
    private List<SearchHit<T>> hits;

    public Hits() {}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getMax_score() {
        return max_score;
    }

    public void setMax_score(float max_score) {
        this.max_score = max_score;
    }

    public List<SearchHit<T>> getHits() {
        return hits;
    }

    public void setHits(List<SearchHit<T>> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
                + hits + "]";
    }
}
