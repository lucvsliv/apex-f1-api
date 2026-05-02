package com.lucvs.apex_f1_api.infrastructure.ai.tool

import com.lucvs.apex_f1_api.application.port.`in`.CreatePostCommand
import com.lucvs.apex_f1_api.application.port.`in`.CreatePostUseCase
import com.lucvs.apex_f1_api.application.port.`in`.CreateSubscriptionUseCase
import com.lucvs.apex_f1_api.application.port.`in`.GetUserInfoUseCase
import com.lucvs.apex_f1_api.application.port.`in`.PurchaseGoodsUseCase
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import java.util.function.Function
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
class ApexActionToolProvider(
        private val createPostUseCase: CreatePostUseCase,
        private val createSubscriptionUseCase: CreateSubscriptionUseCase,
        private val purchaseGoodsUseCase: PurchaseGoodsUseCase,
        private val getUserInfoUseCase: GetUserInfoUseCase
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication?.name?.toLong()
                ?: throw IllegalStateException("User not authenticated")
    }

    // 1. 게시글 작성 툴
    data class CreatePostRequest(val title: String, val content: String)
    data class ActionResponse(val message: String)

    @Bean
    @Description("게시판에 새로운 게시글을 작성합니다. 제목(title)과 내용(content)이 필요합니다.")
    fun createPostTool(): Function<CreatePostRequest, ActionResponse> {
        return Function { request ->
            val userId = getCurrentUserId()
            log.info("[*] AI Agent가 유저 {}를 대신하여 게시글을 작성합니다: {}", userId, request.title)

            createPostUseCase.createPost(
                    CreatePostCommand(
                            authorId = userId,
                            title = request.title,
                            content = request.content
                    )
            )

            ActionResponse("게시글 '${request.title}' 작성이 완료되었습니다.")
        }
    }

    // 2. 멤버십 가입 툴
    data class JoinMembershipRequest(val tier: String)

    @Bean
    @Description("사용자의 멤버십 등급을 변경하거나 가입합니다. 등급은 ROOKIE, PADDOCK, GARAGE, PITWALL 중 하나여야 합니다.")
    fun joinMembershipTool(): Function<JoinMembershipRequest, ActionResponse> {
        return Function { request ->
            val userId = getCurrentUserId()
            val tier =
                    try {
                        MembershipTier.valueOf(request.tier.uppercase())
                    } catch (e: Exception) {
                        return@Function ActionResponse(
                                "잘못된 등급입니다. ROOKIE, PADDOCK, GARAGE, PITWALL 중에서 선택해주세요."
                        )
                    }

            log.info("[*] AI Agent가 유저 {}를 {} 멤버십에 가입시킵니다.", userId, tier)

            try {
                // AI Agent를 통한 가입이므로 내부적으로 사용할 키를 전달
                createSubscriptionUseCase.subscribe(userId, "AI_AGENT_AUTH", "AI_AGENT_CUST", tier)
                ActionResponse("${tier} 멤버십 가입이 성공적으로 완료되었습니다!")
            } catch (e: Exception) {
                log.error("[!] 멤버십 가입 중 오류 발생", e)
                ActionResponse("멤버십 가입에 실패했습니다: ${e.message}")
            }
        }
    }

    // 3. 내 멤버십 조회 툴
    data class GetMyMembershipRequest(val dummy: String? = null)

    @Bean
    @Description("현재 로그인한 사용자의 멤버십 등급 정보를 조회합니다.")
    fun getMyMembershipTool(): Function<GetMyMembershipRequest, ActionResponse> {
        return Function { _ ->
            val userId = getCurrentUserId()
            val user = getUserInfoUseCase.getCurrentUser(userId)
            ActionResponse("귀하의 현재 멤버십 등급은 ${user.tier}입니다.")
        }
    }

    // 3. 굿즈 구매 툴
    data class PurchaseGoodsRequest(val marketItemId: Long)

    @Bean
    @Description("상점(Market)에서 특정 상품을 구매합니다. 상품 ID(marketItemId)가 필요합니다.")
    fun purchaseGoodsTool(): Function<PurchaseGoodsRequest, ActionResponse> {
        return Function { request ->
            val userId = getCurrentUserId()
            log.info("[*] AI Agent가 유저 {}를 위해 상품 {}을 구매합니다.", userId, request.marketItemId)

            try {
                purchaseGoodsUseCase.purchase(userId, request.marketItemId)
                ActionResponse("상품(ID: ${request.marketItemId}) 구매가 완료되었습니다.")
            } catch (e: Exception) {
                ActionResponse("상품 구매에 실패했습니다: ${e.message}")
            }
        }
    }

    // 4. 멤버십 결제 요청 툴
    data class PaymentRequest(val tier: String)

    @Bean
    @Description(
            "사용자에게 특정 멤버십 등급에 대한 결제창을 보여달라고 요청합니다. 유료 등급(PADDOCK, GARAGE, PITWALL) 가입/변경 시 사용하세요."
    )
    fun requestMembershipPaymentTool(): Function<PaymentRequest, ActionResponse> {
        return Function { request ->
            log.info("[*] AI Agent가 {} 등급 결제창 표시를 요청합니다.", request.tier)
            ActionResponse("[ACTION_TOSS_PAYMENT:${request.tier.uppercase()}]")
        }
    }
}
