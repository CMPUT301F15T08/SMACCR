public class InventoryTest extends ActivityInstrumentationTestCase2 {

    public InventoryTest() {
        super(ca.ualberta.cs.APP_NAME.Inventory.class);
    }
    // Avoid using setUps and tearDowns

    // As an owner, I want to add an item
    public void testAddCard() {
        Inventory inv = new Inventory();
        // GCard constructor: public GCard(String name, int value, int quantity, int quality (out of 5),
        //                                   String category, bool shared, String comment)
        GCard gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        // inv.addCard(gcard);
        if (inv.hasCard(gcard)) {
            throw new IllegalArgumentException();
        }
        inv.addCard(gcard);
        assertTrue(inv.hasCard(gcard));
    }

    // As an owner, I want to remove an item
    // As an owner, I want to delete inventory items
    public void testDelete() {
        Inventory inv = new Inventory();
        GCard gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard);
        inv.removeCard(gcard);
        assertFalse(inv.hasCard(gcard));
    }

    public void testHasCard() {
        Inventory inv = new Inventory();
        Tweet gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard);
        assertTrue(inv.hasCard(gcard));
    }

    // As an owner, I want to view my inventory
    // As an owner, I want to view each of my inventory items.
    public void testGetCards() {
        Inventory inv = new Inventory();
        GCard gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard);
        GCard gcard2 = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard2);
        ArrayList<GCard> gcards2 = inv.getCards();
        assertTrue((gcards2.get(0) == gcard) && (gcards2.get(1) == gcard2));
    }

    // As an owner, I want to view my inventory's details
    public void testGetCount() {
        Inventory inv = new Inventory();
        GCard gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard);
        int count = inv.getCount();
        assertTrue(count == 1);
    }

    // As an owner, I want to edit an item
    // As an owner, not every item in my inventory will be shared or listed
    // As an owner, I want to edit and modify inventory items
    public void testEditCard() {
        Inventory inv = new Inventory();
        GCard gcard  = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        GCard gcard2 = new GCard("Cineplex", 50, 75, 5, "Entertainment", False, "I might keep these");
        inv.editCard(gcard, "name", "Cineplex");
        inv.editCard(gcard, "value", "50");
        inv.editCard(gcard, "quantity", "75");
        inv.editCard(gcard, "quality", "5");
        inv.editCard(gcard, "category", "Entertainment");
        inv.editCard(gcard, "shared", "False");
        inv.editCard(gcard, "comment", "I might keep these");
        assertTrue(gcard == gcard2);
    }

    // As an owner, I want the category for an item to be 
    // one of 10 relevant categories for <THINGS>.
    public void testVerifyCategory() {
        // CategoryList objects contain ten categories from constructor of CategoryList
        CategoryList catList = new CategoryList();
        GCard gcard  = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        assertTrue(catList.contains(gcard.category));
    }

    // As an owner, I want the entry of an item to have minimal required 
    // navigation in the user interface. It must be easy to describe an item.
    // http://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java
    public void testSortAlpha() {
        ArrayList<GCard> gcards = new ArrayList<GCard>();
        GCard gcard1 = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        gcards.add(gcard1);
        GCard gcard2 = new GCard("Cineplex", 50, 75, 5, "Entertainment", False, "I might keep these");
        gcards.add(gcard2);
        // <gcard1, gcard2>

        Collections.sort(gcards, new Comparator<GCard>() {
            @Override
            public int compare(GCard card1, GCard card2)
            {
                return  card1.getName().compareTo(card2.getName());
            }
        });
        assertTrue((gcards.get(0) == gcard2) && (gcards.get(1) == gcard1));
        // <gcard2, gcard1>
    }

    // As an owner, I want the entry of an item to have minimal required 
    // navigation in the user interface. It must be easy to describe an item.
    // http://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java
    public void testSortVal() {
        ArrayList<GCard> gcards = new ArrayList<GCard>();
        GCard gcard2 = new GCard("Cineplex", 50, 75, 5, "Entertainment", False, "I might keep these");
        gcards.add(gcard2);
        GCard gcard1 = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        gcards.add(gcard1);
        // <gcard2, gcard1>

        Collections.sort(gcards, new Comparator<GCard>() {
            @Override
            public int compare(GCard card1, GCard card2)
            {
                return  card1.getValue().compareTo(card2.getValue());
            }
        });
        assertTrue((gcards.get(0) == gcard1) && (gcards.get(1) == gcard2));
        // <gcard1, gcard2>
    }

    // As an owner, I want the entry of an item to have minimal required 
    // navigation in the user interface. It must be easy to describe an item.
    public void testGetCategory() {
        ArrayList<GCard> gcards = new ArrayList<GCard>();
        Inventory inv = new Inventory();
        GCard gcard = new GCard("Starbucks", 20, 1, 4, "Food & Drink", True, "Will get you half a coffee");
        inv.addCard(gcard);
        GCard gcard2 = new GCard("Cineplex", 50, 75, 5, "Entertainment", False, "I might keep these");
        inv.addCard(gcard2);
        GCard gcard3 = new GCard("Subway", 25, 1, 2, "Food & Drink", True, "Wonderful hexcrements ;) (hex increments ~ incrments of 6)");
        inv.addCard(gcard3);

        ArrayList<GCard> outlist = new ArrayList<GCard>();
        for (int i = 0; i < gcards.size(); ++i) {
            if (gcards.get(i).getCategory() == "Food & Drink") {
                outlist.add(gcards.get(i));
            }
        }
        assertTrue(outlist.size() == 2);
    }
}