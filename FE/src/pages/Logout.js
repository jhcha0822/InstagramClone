import { useNavigate } from "react-router-dom";
import { useLogin } from "../contexts/AuthContext";

const Logout = () => {
    const navigate = useNavigate();
    const { setIsLoggedIn, setLoginUser } = useLogin();
    const fetchLogout = async () => {
        try {
            const accessToken = window.localStorage.getItem("access"); // Access Token 가져오기

            // 로그아웃 요청 시 백엔드에서 refresh token 블랙리스트 처리 (혹은 refresh 토큰 DB 에서 삭제)
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/logout`, {
                method: "POST",
                // credentials: "include", // 쿠키 전달
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${accessToken}` // Authorization 헤더 추가
                }
            });

            if (response.ok) {
                alert("logout successful");
                // access token 삭제 (로컬 스토리지)
                window.localStorage.removeItem("access");
                window.localStorage.removeItem("nickname");

                setIsLoggedIn(false);
                setLoginUser(null);
            } else {
                alert("logout failed");
            }
            navigate("/", { replace: true });
        } catch (error) {
            console.log("error: ", error);
        }
    }
    fetchLogout();
    return;
}

export default Logout;