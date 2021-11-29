package org.example.thread;

import org.example.domain.Product;
import org.example.domain.ProductInfo;
import org.example.domain.Review;
import org.example.service.ProductInfoService;
import org.example.service.ReviewService;

import static org.example.util.CommonUtil.stopWatch;
import static org.example.util.LoggerUtil.log;

public record ProductServiceUsingThread(ProductInfoService productInfoService,
                                        ReviewService reviewService) {

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
        var productInfoRunnable = new ProductInfoRunnable(productId);
        var productInfoThread = new Thread(productInfoRunnable);

        var reviewRunnable = new ReviewRunnable(productId);
        var reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    private class ProductInfoRunnable implements Runnable {
        private final String productId;
        private ProductInfo productInfo;

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {
        private final String productId;
        private Review review;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}
