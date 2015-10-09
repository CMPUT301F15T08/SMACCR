public class TradeWithFriendsTest extends AcitivityInstrumentationTestCase2 {

	public void testComposeNormalTrade() {
		Trade trade = new Trade(new NormalTrade());
		Inventory inventory;
		Owner owner;
		Borrower me;
		trade.addInventory(inventory);
		trade.initiateTrade(me, owner);
		assertEquals(trade.getTradeStatus(), "SENT");
	}

	public void testTradeAccepted() {
		Trade trade = new Trade(new NormalTrade());
		Inventory inventory;
		Owner owner;
		Borrower me;
		trade.addInventory(inventory);
		trade.initiateTrade(me, owner);
		assertEquals(trade.getTradeStatus(), "ACCEPTED");
	}

	public void testTradeDeclined() {
		Trade trade = new Trade(new NormalTrade());
		Inventory inventory;
		Owner owner;
		Borrower me;
		trade.addInventory(inventory);
		trade.initiateTrade(me, owner);
		assertEquals(trade.getTradeStatus(), "DECLINED");
	}


}