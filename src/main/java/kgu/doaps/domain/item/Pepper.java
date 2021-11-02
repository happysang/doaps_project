package kgu.doaps.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.YearMonth;

@Entity
@DiscriminatorValue("Pepper")
@Getter @Setter
public class Pepper extends Item{
//    수입 일자, 가공일, 원산지, 품종, 색깔, 맵기정도(스코빌지수)
//    importDate / processDate / origin / variety/ color / spicy
    private YearMonth importDate;
    private YearMonth processDate;
    private String origin;
    private String variety;
    private String color;
    private int spicy;  //스코빌 지수 (SHU)

}
