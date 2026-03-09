package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.SubscriptionHistory

interface RecordSubscriptionHistoryPort {
    fun record(history: SubscriptionHistory): SubscriptionHistory
}