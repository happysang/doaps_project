package kgu.doaps.domain.item;

import kgu.doaps.domain.Member;
import kgu.doaps.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;  // 상품 이름
    private int price;  //상품 가격
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    private Member member;

    private String imgUrl;
    
    private int sales; //판매수량도 추가 (굳이 추가해준 이유는 로직이 너무복잡해서 추적번거로움)

    //==비즈니스 로직 ==//
    /*
     * stock 증가
     * */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*
     * stock 감소
     * */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    
    public void addSales(int quantity) { this.sales += quantity; }
    public void removeSales(int quantity) { this.sales -= quantity; }
}
