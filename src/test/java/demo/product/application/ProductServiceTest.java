package demo.product.application;

import com.querydsl.core.BooleanBuilder;
import demo.product.domain.Product;
import demo.product.domain.ProductType;
import demo.product.infrastructure.entity.ProductEntity;
import demo.product.infrastructure.entity.QProductEntity;
import demo.product.infrastructure.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@NoArgsConstructor
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    public void getProductsTest() {
        ProductEntity entity = new ProductEntity();
        entity.setId("XXX");
        entity.setType("phone");
        entity.setProperties("color:blue");
        entity.setPrice(BigDecimal.valueOf(100));
        entity.setStoreAddress("Stockholm");

        List<ProductEntity> entities = new ArrayList<>();
        entities.add(entity);

        var qProductEntity = QProductEntity.productEntity;
        var predicate = new BooleanBuilder().and(qProductEntity.type.eq(entity.getType()))
                .and(qProductEntity.storeAddress.contains(entity.getStoreAddress()));

        ProductService productService = new ProductService(productRepository);

        given(productRepository.findAll(predicate)).willReturn(entities);

        List<Product> products = productService.getProducts(ProductType.phone,null,null,
                "Stockholm",null,null,null,null);

        assertThat(products.get(0).getType()).isEqualTo(ProductType.phone.toString());
        assertThat(products.get(0).getStore_address()).isEqualTo("Stockholm");

    }
}