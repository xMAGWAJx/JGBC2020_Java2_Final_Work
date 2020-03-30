package lv.javaguru.productlist.businesslogic.validataion;

import lv.javaguru.productlist.domain.Category;
import lv.javaguru.productlist.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductNameValidationRuleTest {

    private ProductNameValidationRule rule = new ProductNameValidationRule();

    @Test
    public void returnFalseWhenNameIsEmpty() {
        Product product = new Product(null, null, BigDecimal.valueOf(10), BigDecimal.valueOf(10), Category.FRUIT);
        assertFalse(rule.isValid(product));
    }
}