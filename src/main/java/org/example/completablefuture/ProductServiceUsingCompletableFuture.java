package org.example.completablefuture;


import org.example.domain.*;
import org.example.service.InventoryService;
import org.example.service.ProductInfoService;
import org.example.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;


public class ProductServiceUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService,
                                                ReviewService reviewService,
                                                InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        return getProduct(productId, productInfoCompletableFuture);
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });
        return getProduct(productId, productInfoCompletableFuture);
    }

    private Product getProduct(String productId, CompletableFuture<ProductInfo> productInfoCompletableFuture) {
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally(throwable -> {
                    log("Handled the Exception in reviewService : " +throwable.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0.0)
                            .build();
                });

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo,
                        review))
                .join(); //block the thread

        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {

        return productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory();
                    productOption.setInventory(inventory);
                    return productOption;
                })
                .toList();
    }


    private List<ProductOption> updateInventoryNonBlocking(ProductInfo productInfo) {

        return productInfo.getProductOptions()
                .stream()
                .map(productOption -> CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory())
                        .thenApply(inventory -> {
                            productOption.setInventory(inventory);
                            return productOption;
                        }))
                .toList()
                .stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public CompletableFuture<Product> retrieveCompletableFutureProduct(String productId) {
        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));

        return productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo,
                        review));
    }
    public Product retrieveProductDetailsWithInventoryWithNonBlocking(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventoryNonBlocking(productInfo));
                    return productInfo;
                });
        return getProduct(productId, productInfoCompletableFuture);
    }
    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService =
                new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
