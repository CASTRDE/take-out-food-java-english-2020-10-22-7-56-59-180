import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List< String > inputs1 = Arrays.asList("ITEM0001 x 1", "ITEM0013 x 2", "ITEM0022 x 1");
        List< String > inputs2 = Arrays.asList("ITEM0013 x 4", "ITEM0022 x 1");
        List< String > inputs3 = Arrays.asList("ITEM0013 x 4");

        App test1 = new App(new ItemRepositoryTestImpl(), new SalesPromotionRepositoryTestImpl());
        App test2 = new App(new ItemRepositoryTestImpl(), new SalesPromotionRepositoryTestImpl());
        App test3 = new App(new ItemRepositoryTestImpl(), new SalesPromotionRepositoryTestImpl());

        System.out.println(test1.bestCharge(inputs1)+"\n"); //buy_30_save_6_sales_promotion
        System.out.println(test2.bestCharge(inputs2)+"\n"); //use_50_percentage_sales_promotion
        System.out.println(test3.bestCharge(inputs3)+"\n"); //no_promotion

    }
}
