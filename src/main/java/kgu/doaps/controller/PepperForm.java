package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter @Setter
public class PepperForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Member member;
    private String imgUrl;

    private YearMonth importDate;
    private YearMonth processDate;
    private String origin;
    private String variety;
    private String color;
    private int spicy;  //스코빌 지수 (SHU)
}