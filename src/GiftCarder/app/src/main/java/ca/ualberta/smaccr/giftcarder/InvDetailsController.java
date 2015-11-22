package ca.ualberta.smaccr.giftcarder;

import android.app.Activity;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by splant on 11/13/15.
 */
public class InvDetailsController {

    private User user;
    private Inventory inv;
    private Activity detailsActivity;

    private int totalCount = 0;
    // private int allCount = 0;        // 0
    private int foodCount = 0;          // 1
    private int clothingCount = 0;      // 2
    private int hgCount = 0;            // 3
    private int electronicsCount = 0;   // 4
    private int departmentCount = 0;    // 5
    private int serviceCount = 0;       // 6
    private int entertainCount = 0;     // 7
    private int onlineCount = 0;        // 8
    private int healthCount = 0;        // 9
    private int otherCount = 0;         // 10

    private double totalValue = 0;
    // private double allValue = 0;        // 0
    private double foodValue = 0;          // 1
    private double clothingValue = 0;      // 2
    private double hgValue = 0;            // 3
    private double electronicsValue = 0;   // 4
    private double departmentValue = 0;    // 5
    private double serviceValue = 0;       // 6
    private double entertainValue = 0;     // 7
    private double onlineValue = 0;        // 8
    private double healthValue = 0;        // 9
    private double otherValue = 0;         // 10

    public InvDetailsController(User user, Inventory inv, Activity activity) {
        this.user = user;
        this.inv = inv;
        this.detailsActivity = activity;
    }

    public void updateDetails() {

        getValuesAndCategories();

        // Change the Title,total count and the Total Value fields
        String invDetailsTitle = user.getUsername() + "'s Inventory";

        DecimalFormat df = new DecimalFormat("#.00");
        String invDetailsValue = "Total Value of GiftCards: $" + df.format(this.totalValue);
        String detailsStrTotalCount = "Total Number of GiftCards: " + this.totalCount;

        TextView detailsTitle = (TextView) detailsActivity.findViewById(R.id.userInvDetTitleTextView);
        detailsTitle.setText(invDetailsTitle);

        TextView detailsValue = (TextView) detailsActivity.findViewById(R.id.totalValueTextView);
        detailsValue.setText(invDetailsValue);

        TextView detailsNumberOfCards = (TextView) detailsActivity.findViewById(R.id.totalCountTextView);
        detailsNumberOfCards.setText(detailsStrTotalCount);

        // Change the values of the "value" and "count" fields for each category
        // Food & Beverage:
        TextView tv = (TextView) detailsActivity.findViewById(R.id.foodCountTV);
        tv.setText(""+this.foodCount);
        tv = (TextView) detailsActivity.findViewById(R.id.foodValTV);
        tv.setText(""+this.foodValue);

        // Clothing:
        tv = (TextView) detailsActivity.findViewById(R.id.clothCountTV);
        tv.setText(""+this.clothingCount);
        tv = (TextView) detailsActivity.findViewById(R.id.clothValTV);
        tv.setText(""+this.clothingValue);

        // Home & Garden:
        tv = (TextView) detailsActivity.findViewById(R.id.hgCountTV);
        tv.setText(""+this.hgCount);
        tv = (TextView) detailsActivity.findViewById(R.id.hgValTV);
        tv.setText(""+this.hgValue);

        // Electronics:
        tv = (TextView) detailsActivity.findViewById(R.id.elecCountTV);
        tv.setText(""+this.electronicsCount);
        tv = (TextView) detailsActivity.findViewById(R.id.elecValTV);
        tv.setText(""+this.electronicsValue);

        // Department:
        tv = (TextView) detailsActivity.findViewById(R.id.depCountTV);
        tv.setText(""+this.departmentCount);
        tv = (TextView) detailsActivity.findViewById(R.id.depValTV);
        tv.setText(""+this.departmentValue);

        // Services:
        tv = (TextView) detailsActivity.findViewById(R.id.servCountTV);
        tv.setText(""+this.serviceCount);
        tv = (TextView) detailsActivity.findViewById(R.id.servValTV);
        tv.setText(""+this.serviceValue);

        // Entertainment:
        tv = (TextView) detailsActivity.findViewById(R.id.entCountTV);
        tv.setText(""+this.entertainCount);
        tv = (TextView) detailsActivity.findViewById(R.id.entValTV);
        tv.setText(""+this.entertainValue);

        // Online:
        tv = (TextView) detailsActivity.findViewById(R.id.onlineCountTV);
        tv.setText(""+this.onlineCount);
        tv = (TextView) detailsActivity.findViewById(R.id.onlineValTV);
        tv.setText(""+this.onlineValue);

        // Health & Beauty:
        tv = (TextView) detailsActivity.findViewById(R.id.hbCountTV);
        tv.setText(""+this.healthCount);
        tv = (TextView) detailsActivity.findViewById(R.id.hbValTV);
        tv.setText(""+this.healthValue);

        // Other:
        tv = (TextView) detailsActivity.findViewById(R.id.otherCountTV);
        tv.setText(""+this.otherCount);
        tv = (TextView) detailsActivity.findViewById(R.id.otherValTV);
        tv.setText(""+this.otherValue);
    }

    public void getValuesAndCategories() {
        // For every value in the inventory list:
        // count++
        // totalValue += item.value*item.quantity
        for (int i = 0; i < this.inv.getInvList().size(); ++i) {
            double value = this.inv.getInvList().get(i).getValue();
            int quantity = this.inv.getInvList().get(i).getQuantity();

            this.totalCount += quantity;
            this.totalValue += value*quantity;

            switch(inv.getInvList().get(i).getCategory()) {
                case 1:
                    this.foodCount += quantity;
                    this.foodValue += value*quantity;
                    break;
                case 2:
                    this.clothingCount += quantity;
                    this.clothingValue += value*quantity;
                    break;
                case 3:
                    this.hgCount += quantity;
                    this.hgValue += value*quantity;
                    break;
                case 4:
                    this.electronicsCount += quantity;
                    this.electronicsValue += value*quantity;
                    break;
                case 5:
                    this.departmentCount += quantity;
                    this.departmentValue += value*quantity;
                    break;
                case 6:
                    this.serviceCount += quantity;
                    this.serviceValue += value*quantity;
                    break;
                case 7:
                    this.entertainCount += quantity;
                    this.entertainValue += value*quantity;
                    break;
                case 8:
                    this.onlineCount += quantity;
                    this.onlineValue += value*quantity;
                    break;
                case 9:
                    this.healthCount += quantity;
                    this.healthValue += value*quantity;
                    break;
                case 10:
                    this.otherCount += quantity;
                    this.otherValue += value*quantity;
                    break;
            }
        }
    }
}
