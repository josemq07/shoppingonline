    package com.onlineshop.productsservice.model;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;

    @Entity

    @AllArgsConstructor
    @NoArgsConstructor
    public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long product_id;
        private String product_brand;
        private String product_name;
        private double product_price;

        public boolean isValid(){

            return (this.product_price>=0 && this.product_id>=1);
        }
        public Long getProduct_id() {
            return product_id;
        }

        public void setProduct_id(Long product_id) {
            this.product_id = product_id;
        }

        public String getProduct_brand() {
            return product_brand;
        }

        public void setProduct_brand(String product_brand) {
            this.product_brand = product_brand;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public double getProduct_price() {
            return product_price;
        }

        public void setProduct_price(double product_price) {
            this.product_price = product_price;
        }


    }
