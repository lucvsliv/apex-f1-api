package com.lucvs.apex_f1_api.infrastructure.mail.adapter

import com.lucvs.apex_f1_api.application.port.out.SendEmailOtpPort
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailAdapter(
    private val mailSender: JavaMailSender
) : SendEmailOtpPort {

    override fun sendOtp(email: String, otp: String) {
        val message = SimpleMailMessage()
        message.setTo(email)
        message.subject = "[Apex F1] 이메일 인증 번호"
        message.text = """
            안녕하세요, Apex F1입니다.
            
            회원가입을 위한 인증 번호는 다음과 같습니다:
            
            [$otp]
            
            해당 인증 번호를 회원가입 페이지에 입력해 주세요.
            감사합니다.
        """.trimIndent()
        
        mailSender.send(message)
    }
}
