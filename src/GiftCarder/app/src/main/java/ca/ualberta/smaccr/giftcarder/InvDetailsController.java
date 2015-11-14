package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by splant on 11/13/15.
 */
public class InvDetailsController {

    private User user;
    private Inventory inv;
    private Activity detailsActivity;

    private int totalCount = 0;
    private int foodCount = 0;          // 0
    private int clothingCount = 0;      // 1
    private int HGCount = 0;            // 2
    private int electronicsCount = 0;   // 3
    private int departmentCount = 0;    // 4
    private int serviceCount = 0;       // 5
    private int entertainCount = 0;     // 6
    private int onlineCount = 0;        // 7
    private int healthCount = 0;        // 8
    private int otherCount = 0;         // 9

    private double totalValue = 0;
    private int foodValue = 0;          // 0
    private int clothingValue = 0;      // 1
    private int HGValue = 0;            // 2
    private int electronicsValue = 0;   // 3
    private int departmentValue = 0;    // 4
    private int serviceValue = 0;       // 5
    private int entertainValue = 0;     // 6
    private int onlineValue = 0;        // 7
    private int healthValue = 0;        // 8
    private int otherValue = 0;         // 9

    public InvDetailsController(User user, Inventory inv, Activity activity) {
        this.user = user;
        this.inv = inv;
        this.detailsActivity = activity;
    }

    public void updateDetails() {

        countValueAndCategories();

        // Change the Title and the Total Value fields
        String invDetailsTitle = user.getUsername() + "'s Inventory";
        String invDetailsValue = "Total Value of Cards: $" + this.totalValue;

        TextView detailsTitle = (TextView) detailsActivity.findViewById(R.id.userInvDetTitleTextView);
        detailsTitle.setText(invDetailsTitle);
        TextView detailsValue = (TextView) detailsActivity.findViewById(R.id.totalValueTextView);
        detailsValue.setText(invDetailsValue);


        TextView detailsNumberOfCards = (TextView) detailsActivity.findViewById(R.id.ID_numberOfCards);
        detailsNumberOfCards.setText(String.valueOf(user.getInv().getSize()));
    }

    public void countValueAndCategories() {
        // For every value in the inventory list:
        // count++
        // totalValue += item.value*item.quantity
        for (int i = 0; i < this.inv.getInvList().size(); ++i) {
            double value = this.inv.getInvList().get(i).getValue();
            int quantity = this.inv.getInvList().get(i).getQuantity();

            this.totalCount++;
            this.totalValue += value*quantity;

            switch(inv.getInvList().get(i).getCategory()) {
                case 0:
                    this.foodCount++;
                    this.foodValue += value*quantity;
                    break;
                case 1:
                    this.clothingCount++;
                    this.clothingValue += value*quantity;
                    break;
                case 2:
                    this.HGCount++;
                    this.HGValue += value*quantity;
                    break;
                case 3:
                    this.electronicsCount++;
                    this.electronicsValue += value*quantity;
                    break;
                case 4:
                    this.departmentCount++;
                    this.departmentValue += value*quantity;
                    break;
                case 5:
                    this.serviceCount++;
                    this.serviceValue += value*quantity;
                    break;
                case 6:
                    this.entertainCount++;
                    this.entertainValue += value*quantity;
                    break;
                case 7:
                    this.onlineCount++;
                    this.onlineValue += value*quantity;
                    break;
                case 8:
                    this.healthCount++;
                    this.healthValue += value*quantity;
                    break;
                case 9:
                    this.otherCount++;
                    this.otherValue += value*quantity;
                    break;
            }
        }
    }
}
