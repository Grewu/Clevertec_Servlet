package org.example.mapper;

import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductMapper {
    /**
     * Маппит DTO в продукт без UUID
     *
     * @param productDto - DTO для маппинга
     * @return новый продукт
     */
    Product toProduct(ProductDto productDto);

    /**
     * Маппит текущий продукт в DTO без даты
     *
     * @param product - существующий продукт
     * @return DTO с идентификатором
     */
    InfoProductDto toInfoProductDto(Product product);

    /**
     * Сливает существующий продукт с информацией из DTO
     * не меняет дату создания и идентификатор
     *
     * @param product    существующий продукт
     * @param productDto информация для обновления
     * @return обновлённый продукт
     */

    Product merge(@MappingTarget Product product, ProductDto productDto);

    /**
     * Маппит текущий продукт в DTO без даты
     *
     * @param productDto - существующий продукт
     * @return DTO с идентификатором
     */
    InfoProductDto toInfoProductDto(ProductDto productDto);

    /**
     * Маппит текущий продукт в DTO без даты
     *
     * @param product - существующий продукт
     * @return DTO с идентификатором
     */
    ProductDto toProductDto(Product product);

}
