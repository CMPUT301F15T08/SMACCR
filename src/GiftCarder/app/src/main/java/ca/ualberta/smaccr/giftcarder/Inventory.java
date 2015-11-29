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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Richard on 2015-10-24.
 * Refactored and maintained by Spencer from 2015-10-28
 *
 * Inventory class is responsible for
 */
public class Inventory implements Serializable {
    private ArrayList<GiftCard> inv;

    public Inventory() {this.inv = new ArrayList<GiftCard>(); }

    public Inventory(ArrayList<GiftCard> that) {this.inv = that; }

    public void addGiftCard(GiftCard gc){
        this.inv.add(0, gc);
    }

    public void deleteGiftCard(int gcIndex){
        this.inv.remove(gcIndex);
    }

    public void removeGiftCard(GiftCard giftCard) {
        for (int i = 0; i < this.inv.size(); i++) {
            if (this.getGiftCard(i).getMerchant().equals(giftCard.getMerchant())) {
                deleteGiftCard(i);
            }

        }
    }

    public int getSize(){
        return this.inv.size();
    }


    // Getters and setters
    public ArrayList<GiftCard> getInvList() {
        return this.inv;
    }

    public void setInv(ArrayList<GiftCard> inv) {
        this.inv = inv;
    }

    public GiftCard getGiftCard(int position) {
        return this.inv.get(position);
    }
}
