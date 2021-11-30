package org.example.service;


import org.example.domain.checkout.Cart;
import org.example.domain.checkout.CartItem;
import org.example.domain.checkout.CheckoutResponse;
import org.example.domain.checkout.CheckoutStatus;

import java.util.List;

import static org.example.util.CommonUtil.startTimer;
import static org.example.util.CommonUtil.timeTaken;

public class CheckoutService {
    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList()
                .stream()
                .parallel()
                .map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .toList();
        timeTaken();
        if (!priceValidationList.isEmpty()) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }

        return new CheckoutResponse(CheckoutStatus.SUCCESS, priceValidationList);
    }

}
