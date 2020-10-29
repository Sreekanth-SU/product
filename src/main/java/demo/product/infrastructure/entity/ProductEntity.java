package demo.product.infrastructure.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private String id;
    @Column(name = "type")
    private String type;
    @Column(name = "properties")
    private String properties;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "Store_address")
    private String storeAddress;
}
