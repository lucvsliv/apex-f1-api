package com.lucvs.apex_f1_api.application.port.out

interface RequestBillingKeyPort {
    /**
     * PG사(토스 페이먼츠)에 빌링키 발급 요청
     * @return 발급된 빌링키 문자열
     */
    fun issueBillingKey(authKey: String, customerKey: String): String
}
