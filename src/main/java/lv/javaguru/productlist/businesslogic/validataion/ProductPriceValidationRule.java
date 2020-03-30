package lv.javaguru.productlist.businesslogic.validataion;

import java.math.BigDecimal;

import lv.javaguru.productlist.domain.Product;

public class ProductPriceValidationRule implements ProductValidationRule{

    @Override
    public boolean isValid(Product product) {
        return product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String errorMessage() {
        return "Incorrect product price!";
    }
}