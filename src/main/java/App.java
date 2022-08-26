
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        StringBuilder sb = new StringBuilder();
        String title = "============= Order details =============\n";
        sb.append(title);

        List<Item> menu = itemRepository.findAll();
        Map<String, Integer> recordOrder = new HashMap<>();
        Map<String, Item> mp = new HashMap<>();
        for (Item cook : menu) {
            mp.put(cook.getId(), cook);
        }
        for (String item : inputs) {
            String[] data = item.split(" x ");
            String itemId = data[0];
            recordOrder.put(itemId, Integer.parseInt(data[1]));
            if (mp.containsKey(itemId)) {
                Item currItem = mp.get(itemId);
                String record = currItem.getName() + " x " + recordOrder.get(itemId) + " = " + totalPrice(currItem.getPrice()*recordOrder.get(itemId)) + " yuan\n";
                sb.append(record);
            }
        }
        sb.append("-----------------------------------\n");


        List<SalesPromotion> promotions = salesPromotionRepository.findAll();
        double count = 0;
        for (Map.Entry<String, Integer> e : recordOrder.entrySet()) {
            count += mp.get(e.getKey()).getPrice() * e.getValue();
        }
        SalesPromotion halfPromote = null;
        for (SalesPromotion promotion : promotions) {
            if (promotion.getType().equals(SALE_PROMOTION_TYPE.HALF_PRICE_FOR_SPECIFIED_ITEMS)) {
                halfPromote = promotion;
                break;
            }
        }

        double discountTot = 0;
        String halfItemName = "";
        if (halfPromote != null) {
            List<String> halfPromoteItemsIds = halfPromote.getRelatedItems();
            halfItemName += "(";
            for (String itemId : halfPromoteItemsIds) {
                if (recordOrder.containsKey(itemId)) {
                    Item item = mp.get(itemId);
                    double currCount = item.getPrice() * (int)recordOrder.get(itemId);
                    discountTot += currCount / 2;
                    halfItemName += item.getName();
                    if (!itemId.equals(halfPromoteItemsIds.get(halfPromoteItemsIds.size() - 1))) {
                        halfItemName += " and ";
                    } else halfItemName += ")";
                }
            }
        }

        double total = count;
        double halfPromotePrice = count - discountTot;
        if (count >= 30 && count - 6 <= halfPromotePrice) {
            sb.append("Promotion used:\n");
            sb.append("Deduct 6 yuan when the order reaches 30 yuan");
            sb.append(", saving 6 yuan\n");
            sb.append("-----------------------------------\n");
            total -= 6;
        } else {
            if (count == halfPromotePrice) {
            } else {
                sb.append("Promotion used:\n");
                sb.append("Half price for certain dishes ");
                sb.append(halfItemName);
                sb.append(", saving " + totalPrice(discountTot) + " yuan\n");
                sb.append("-----------------------------------\n");
                total = halfPromotePrice;
            }
        }
        sb.append("Total:" + totalPrice(total) + " yuan\n");
        sb.append("===================================");
        return sb.toString();
    }

    public String totalPrice(double price) {
        String totalPrice = String.valueOf(price);
        totalPrice = totalPrice.replaceAll("0+?$", "");
        totalPrice = totalPrice.replaceAll("[.]$", "");
        return totalPrice;
    }

    interface SALE_PROMOTION_TYPE {
        String BUY_30_SAVE_6_YUAN = "BUY_30_SAVE_6_YUAN";
        String HALF_PRICE_FOR_SPECIFIED_ITEMS = "50%_DISCOUNT_ON_SPECIFIED_ITEMS";
    }
}
