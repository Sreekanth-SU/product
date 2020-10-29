package demo.product.api;

import com.google.common.collect.ImmutableMap;
import demo.product.application.ProductService;
import demo.product.domain.Data;
import demo.product.domain.Product;
import demo.product.domain.ProductType;
import demo.product.domain.PropertyType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping
    private ResponseEntity getProducts(@RequestParam(required = false) ProductType type,
                                             @RequestParam(name = "min_price",required = false) Integer minPrice,
                                             @RequestParam(name = "max_price",required = false) Integer maxPrice,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) PropertyType propertyType,
                                             @RequestParam(required = false) String color,
                                             @RequestParam(name = "gb_limit_min",required = false) Integer minGbLimit,
                                             @RequestParam(name = "gb_limit_max",required = false) Integer maxGbLimit
    ) {
        List<Product> products = productService.getProducts(type, minPrice, maxPrice, city, propertyType,
                color, minGbLimit, maxGbLimit);

        return ResponseEntity.ok().body(new Data(products));
    }
}