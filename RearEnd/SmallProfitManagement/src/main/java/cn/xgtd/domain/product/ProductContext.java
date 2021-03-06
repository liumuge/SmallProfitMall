package cn.xgtd.domain.product;

import java.io.Serializable;

/**
 * 配置详细信息
 * @author Kite
 * @date 2020/5/24
 */
public class ProductContext implements Serializable {
    /**配置id**/
    private Integer attributeId;
    /**商品id**/
    private Integer productId;
    /**配置名称**/
    private String attributeContent;
    /**商品类型**/
    private String attributeType;
    /**商品类型id**/
    private Integer titleId;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeContent() {
        return attributeContent;
    }

    public void setAttributeContent(String attributeContent) {
        this.attributeContent = attributeContent;
    }
}
