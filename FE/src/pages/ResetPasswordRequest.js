import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ResetPasswordRequest = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [securityCode, setSecurityCode] = useState('');
    const [isCodeSent, setIsCodeSent] = useState(false);
    const [verificationSuccess, setVerificationSuccess] = useState(false);
    const navigate = useNavigate();

    const handleSendCode = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/user/resetPasswordRequest`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username })
            });
            if (response.ok) {
                alert("보안 코드가 이메일로 전송되었습니다.");
                setIsCodeSent(true);
                const data = await response.json();
                setEmail(data.email); // 서버에서 받은 email을 상태로 저장
            } else {
                alert("보안 코드 요청 실패");
            }
        } catch (error) {
            console.log("보안 코드 요청 중 오류:", error);
        }
    };

    const handleVerifyCode = async () => {
        try {
            const params = new URLSearchParams();
            params.append("username", username);
            params.append("email", email);
            params.append("code", securityCode);

            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/verifySecurityCode`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                    // 'Content-Type': 'application/json'
                },
                body: params.toString()
                // body: JSON.stringify({ username, email, code: securityCode })
            });
            if (response.ok) {
                setVerificationSuccess(true);
                alert("보안 코드가 확인되었습니다.");
                navigate("/reset-password");
            } else {
                alert("보안 코드 확인 실패");
            }
        } catch (error) {
            console.log("보안 코드 확인 중 오류:", error);
        }
    };

    return (
        <div className="reset-password">
            <h1>비밀번호 재설정</h1>
            <p><span className='label'>사용자 이름</span>
                <input
                    type="text"
                    className='input-class'
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </p>
            <button onClick={handleSendCode} className="form-btn">코드 요청</button>

            {isCodeSent && (
                <>
                    <p>
                        <span className='label'>이메일</span>
                        <input
                            type="text"
                            className='input-class'
                            value={email}
                            readOnly
                        />
                    </p>
                    <p><span className='label'>보안 코드</span>
                        <input
                            type="text"
                            className='input-class'
                            value={securityCode}
                            onChange={(e) => setSecurityCode(e.target.value)}
                        />
                    </p>
                    <button onClick={handleVerifyCode} className="form-btn">코드 확인</button>
                </>
            )}
        </div>
    );
}

export default ResetPasswordRequest;
