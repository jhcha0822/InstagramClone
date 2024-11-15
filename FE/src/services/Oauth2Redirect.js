import { useNavigate, useLocation } from "react-router-dom";
import { useLogin } from "../contexts/AuthContext";

const OAuth2Redirect = () => {
    const navigate = useNavigate();
    const { setIsLoggedIn, setLoginUser } = useLogin();
    const location = useLocation();

    const handleOAuth2Login = () => {
        const params = new URLSearchParams(location.search);
        const accessToken = params.get("accessToken");
        const nickname = params.get("nickname");

        if (accessToken) {
            // Access Token과 사용자 정보 저장
            window.localStorage.setItem("access", accessToken);
            window.localStorage.setItem("nickname", nickname);

            setIsLoggedIn(true);
            setLoginUser(nickname);

            // 닉네임이 "TempNickname"이면 닉네임 설정 페이지로 이동
            if (nickname === "TempNickname") {
                navigate("/set-nickname");
            } else {
                navigate("/", { replace: true });
            }
        } else {
            console.error("Access Token이 없습니다.");
            alert("로그인에 실패했습니다.");
            navigate("/login", { replace: true });
        }
    };

    handleOAuth2Login();
    return null;
};

export default OAuth2Redirect;
