package org.example.service;

import org.example.domain.checkout.Cart;
import org.example.domain.checkout.CheckoutResponse;
import org.example.domain.checkout.CheckoutStatus;
import org.example.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {
    private final PriceValidatorService priceValidatorService = new PriceValidatorService();
    private final CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void checkout() {
        Cart cart = DataSet.createCart(6);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void checkout23items() {
        Cart cart = DataSet.createCart(23);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }


}
