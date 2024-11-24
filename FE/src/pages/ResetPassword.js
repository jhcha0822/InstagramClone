import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ResetPassword = () => {
    const [step, setStep] = useState(1); // Step: 1 (username 입력), 2 (코드 입력), 3 (비밀번호 재설정)
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [securityCode, setSecurityCode] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
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
                setStep(2); // 다음 단계로 이동
                const data = await response.json();
                setEmail(data.email); // 서버에서 받은 email 저장
            } else {
                alert("보안 코드 요청 실패");
            }
        } catch (error) {
            console.log("보안 코드 요청 중 오류:", error);
        }
    };

    const handleVerifyCode = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/verifySecurityCode`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    username,
                    email,
                    code: securityCode
                }).toString()
            });

            if (response.ok) {
                alert("보안 코드가 확인되었습니다.");
                setStep(3); // 다음 단계로 이동
            } else {
                alert("보안 코드 확인 실패");
            }
        } catch (error) {
            console.log("보안 코드 확인 중 오류:", error);
        }
    };

    const handleResetPassword = async () => {
        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/user/resetPassword`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                alert("비밀번호가 성공적으로 재설정되었습니다.");
                navigate("/login");
            } else {
                alert("비밀번호 재설정 실패");
            }
        } catch (error) {
            console.log("비밀번호 재설정 중 오류:", error);
        }
    };

    return (
        <div className="reset-password">
            <h1>비밀번호 재설정</h1>

            {step === 1 && (
                <div>
                    <p><span className='label'>사용자 이름</span>
                        <input
                            type="text"
                            className='input-class'
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </p>
                    <button onClick={handleSendCode} className="form-btn">코드 요청</button>
                </div>
            )}

            {step === 2 && (
                <div>
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
                </div>
            )}

            {step === 3 && (
                <div>
                    <p><span className='label'>새 비밀번호</span>
                        <input
                            type="password"
                            className='input-class'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </p>
                    <p><span className='label'>새 비밀번호 확인</span>
                        <input
                            type="password"
                            className='input-class'
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </p>
                    <button onClick={handleResetPassword} className="form-btn">비밀번호 재설정</button>
                </div>
            )}
        </div>
    );
}

export default ResetPassword;
