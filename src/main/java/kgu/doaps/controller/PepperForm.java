package kgu.doaps.controller;

import kgu.doaps.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PepperForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Member member;
    private String imgUrl;
}