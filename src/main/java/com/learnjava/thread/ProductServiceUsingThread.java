package com.learnjava.thread;

import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.Review;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingThread {
    /* so here I am using threads and you can see the tie taken */
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();
       /* Without thread
        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call
        */

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        ReviewRunnable reviewRunnable =  new ReviewRunnable(productId);
        Thread pInfoThread = new Thread(productInfoRunnable);
        Thread reviewThread = new Thread(reviewRunnable);
        pInfoThread.start();
        reviewThread.start();

        pInfoThread.join();
        reviewThread.join();

        // Now to get the review and product details
        ProductInfo productInfo = productInfoRunnable.getpInfo();
        Review review = reviewRunnable.getReview();
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }


    // ProductInfo thread class
    private class ProductInfoRunnable implements Runnable {
        /* So we have created a inner class and we want the ProductInfo class type variable that we have created
        and since we have are implementing Runnable we implement run() so in  this we want to call the
        retrieveProductInfo() in ProductInfoSerive
        so If you see on top we already have  object of ProductInfoService and we call
        like obj.retrieveProductInfo() and it returns a ProductInfo that we store in pInfo */
        private ProductInfo pInfo;
        private String productId;
        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            pInfo = productInfoService.retrieveProductInfo(productId);
        }

        public ProductInfo getpInfo() {
            return pInfo;
        }
    }

    // ReviewRunnable thread class
    private class ReviewRunnable implements Runnable {

        private  Review review;
        private String productId;
        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }

        public Review getReview() {
            return review;
        }
    }
}
