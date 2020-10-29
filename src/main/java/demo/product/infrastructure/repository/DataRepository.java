package demo.product.infrastructure.repository;

import com.querydsl.core.types.Predicate;
import demo.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface DataRepository<T, S> extends JpaRepository<T, S>, Repository<T, S>
{
    List<ProductEntity> findAll(Predicate predicate);}
