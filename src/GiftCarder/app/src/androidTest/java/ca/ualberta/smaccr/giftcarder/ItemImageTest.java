package ca.ualberta.smaccr.giftcarder;

/**
 * Created by cbli on 11/29/15.
 */
public class ItemImageTest extends android.test.ActivityInstrumentationTestCase2 {

    public ItemImageTest(){
        super(ca.ualberta.smaccr.giftcarder.ItemImage.class);
    }

    public void testFeatured() throws Exception {
        ItemImage itemImage = new ItemImage("bitmapString");
        itemImage.setFeatured(true);
        assertTrue(itemImage.isFeatured());
    }
}
