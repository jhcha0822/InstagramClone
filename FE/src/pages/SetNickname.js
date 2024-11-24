import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLogin } from "../contexts/AuthContext";

const SetNickname = () => {
    const [nickname, setNickname] = useState('');
    const navigate = useNavigate();
    const { setIsLoggedIn, setLoginUser } = useLogin();

    const handleSetNickname = async (e) => {
        e.preventDefault();
        try {
            const token = window.localStorage.getItem("access");
            const currentNickname = window.localStorage.getItem("nickname"); // 현재 닉네임 가져오기
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/nickname/set`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ currentNickname, newNickname: nickname })
            });
            if (response.ok) {
                window.localStorage.setItem("nickname", nickname); // 로컬스토리지에 닉네임 저장
                alert("닉네임이 변경되었습니다. 다시 로그인해주세요.");

                // 로그아웃 처리 (Logout.js에서 사용된 로직)
                const logoutResponse = await fetch(`${process.env.REACT_APP_API_BASE_URL}/logout`, {
                    method: "POST",
                    credentials: "include",
                });

                if (logoutResponse.ok) {
                    // 로컬 스토리지에서 토큰 및 사용자 정보 삭제
                    window.localStorage.removeItem("access");
                    window.localStorage.removeItem("nickname");

                    setIsLoggedIn(false);
                    setLoginUser(null);
                    navigate("/login", { replace: true }); // 로그인 페이지로 리다이렉트
                } else {
                    alert("로그아웃 실패");
                }
            } else {
                const errorMessage = await response.text();
                console.log("Error response:", response.status, errorMessage);
                alert("닉네임 설정 실패");
            }
        } catch (error) {
            console.log("닉네임 설정 중 오류:", error);
        }
    }

    return (
        <div className="set-nickname">
            <h1>닉네임 설정</h1>
            <form onSubmit={handleSetNickname}>
                <p><span className='label'>Nickname</span>
                    <input className='input-class' type="text" value={nickname} onChange={(e) => setNickname(e.target.value)} placeholder="nickname" /></p>
                <input type="submit" value="Set Nickname" className="form-btn" />
            </form>
        </div>
    );
}

export default SetNickname;