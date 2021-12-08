package org.example.completablefuture;

import org.example.domain.Product;
import org.example.service.InventoryService;
import org.example.service.ProductInfoService;
import org.example.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    private ProductInfoService productInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;

    @Test
    void retrieveProductDetailsWithInventoryException() {
        var productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any()))
                .thenThrow(new RuntimeException("Exception Occured"));
        when(inventoryService.retrieveInventory()).thenCallRealMethod();

        Product product =
                productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryWithNonBlocking(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
    }

    @Test
    void retrieveProductDetailsWithInventoryExceptionProductInfoServiceError() {
        var productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("Exception Occured"));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        assertThrows(RuntimeException.class,
                () -> productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryWithNonBlocking(productId));
    }
}
