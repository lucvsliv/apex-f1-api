package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Subscription

interface SaveSubscriptionPort {
    fun saveSubscription(subscription: Subscription): Subscription
}