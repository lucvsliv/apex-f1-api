package com.lucvs.apex_f1_api.infrastructure.persistence.entity.store

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "goods")
class GoodsEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(nullable = false)
    var price: BigDecimal,

    @Column(name = "stock_quantity", nullable = false)
    var stockQuantity: Int,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun decreaseStock(quantity: Int) {
        require(this.stockQuantity - quantity >= 0) { "재고가 부족하여 상품을 구매할 수 없습니다." }
        this.stockQuantity -= quantity
    }
}