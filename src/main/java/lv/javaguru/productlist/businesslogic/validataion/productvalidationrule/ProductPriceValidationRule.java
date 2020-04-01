package lv.javaguru.productlist.businesslogic.validataion.productvalidationrule;

import lv.javaguru.productlist.businesslogic.validataion.ProductValidationRuleInterface;
import lv.javaguru.productlist.domain.Product;

import java.math.BigDecimal;

public class ProductPriceValidationRule implements ProductValidationRuleInterface {

    @Override
    public boolean isValid(Product product) {
        return product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String errorMessage() {
        return "Incorrect product price!";
    }
}