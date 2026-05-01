package com.lucvs.apex_f1_api.application.port.`in`

interface PurchaseGoodsUseCase {
    fun purchase(userId: Long, marketItemId: Long)
}
