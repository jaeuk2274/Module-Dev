package me.jaeuk.hr_module.domain;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter @ToString
public class Product {

    private Long prodId;
    @NonNull private String prodName;
    @NonNull private int prodPrice;
}