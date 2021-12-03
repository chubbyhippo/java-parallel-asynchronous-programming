package org.example.completablefuture;

import org.example.domain.Product;
import org.example.service.InventoryService;
import org.example.service.ProductInfoService;
import org.example.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {
    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();
    private final ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture =
            new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);


    @Test
    void retrieveProductDetails() {
        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveCompletableFutureProduct() {

        String productId = "ABC123";

        CompletableFuture<Product> productCompletableFuture =
                productServiceUsingCompletableFuture.retrieveCompletableFutureProduct(productId);

        productCompletableFuture.thenAccept(product -> {
            assertNotNull(product);
            assertTrue(product.getProductInfo().getProductOptions().size() > 0);
            assertNotNull(product.getReview());
        }).join();
    }

    @Test
    void retrieveProductDetailsWithInventory() {
        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                        .forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }
}
