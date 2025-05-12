package com.Gokul.Projects.AirBnb.strategy;

import com.Gokul.Projects.AirBnb.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OccupancyStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory){
        BigDecimal price = wrapped.calculatePrice(inventory);
        double occupancyRate =
                (double)inventory.getBookedCnt()/inventory.getTotalCnt();

        if(occupancyRate > 0.8){
            price = price.add(BigDecimal.valueOf(500));
        }

        return price;
    }
}
