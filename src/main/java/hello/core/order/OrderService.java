package hello.core.order;

public interface OrderService {
    /**
     * 주문 단건 생성
     * @param memberId
     * @param itemName
     * @param itemPrice
     * @return 주문 단건
     */
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
