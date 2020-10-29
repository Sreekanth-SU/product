package demo.product.application;

import com.querydsl.core.BooleanBuilder;
import demo.product.domain.Product;
import demo.product.domain.ProductType;
import demo.product.domain.PropertyType;
import demo.product.infrastructure.entity.ProductEntity;
import demo.product.infrastructure.entity.QProductEntity;
import demo.product.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts(ProductType type, Integer minPrice, Integer maxPrice,
                                     String city, PropertyType propertyType, String color,
                                     Integer minGbLimit, Integer maxGbLimit) {

        var qProductEntity = QProductEntity.productEntity;
        var predicate = new BooleanBuilder();

        if (nonNull(type)) {
            predicate.and(qProductEntity.type.eq(type.name()));
        }
        if (nonNull(minPrice)) {
            predicate.and(qProductEntity.price.eq(BigDecimal.valueOf(minPrice))
                    .or(qProductEntity.price.gt(BigDecimal.valueOf(minPrice))));
        }
        if (nonNull(maxPrice)) {
            predicate.and(qProductEntity.price.eq(BigDecimal.valueOf(maxPrice))
                    .or(qProductEntity.price.lt(BigDecimal.valueOf(maxPrice))));
        }
        if (nonNull(propertyType)) {
            predicate.and(qProductEntity.properties.contains(propertyType.name()));
        }
        if (nonNull(color)) {
            predicate.and(qProductEntity.properties.contains(color));
        }
        if (nonNull(city)) {
            predicate.and(qProductEntity.storeAddress.contains(city));
        }
        List<ProductEntity> productEntityList = new ArrayList<>();

        productRepository.findAll(predicate).forEach(productEntityList::add);

        if (nonNull(minGbLimit)) {
            productEntityList = productEntityList.stream().filter(productEntity ->
                    productEntity.getProperties().startsWith("gb_limit:") &&
                            (Integer.parseInt(productEntity.getProperties().split("gb_limit:")[1]) >= minGbLimit))
                    .collect(Collectors.toList());
        }

        if (nonNull(maxGbLimit)) {
            productEntityList = productEntityList.stream().filter(productEntity ->
                    productEntity.getProperties().startsWith("gb_limit:") &&
                            (Integer.parseInt(productEntity.getProperties().split("gb_limit:")[1]) <= maxGbLimit))
                    .collect(Collectors.toList());
        }

        return productEntityList.stream().map(productEntity ->
                new Product(productEntity.getType(),
                        productEntity.getProperties(),
                        productEntity.getPrice(),
                        productEntity.getStoreAddress()))
                .collect(Collectors.toList());
    }
}
