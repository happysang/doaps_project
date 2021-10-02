package kgu.doaps.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("Potato")
@Getter @Setter
public class Potato {

    @Id @GeneratedValue
    private Long id;
}
