import { useNavigate, useSearchParams } from "react-router-dom";
import { useLogin } from "../contexts/AuthContext";

const OAuth2Redirect = () => {
    const navigate = useNavigate();
    const { setIsLoggedIn, setLoginUser } = useLogin();

    const OAuth2JwtHeaderFetch = async () => {
        const [queryParams] = useSearchParams();
        try {
            const response = await fetch("http://localhost:8080/api/v1/oauth2-jwt-header", {
                method: "POST",
                credentials: "include",
            });

            if (response.ok) {
                // local storage access token set
                window.localStorage.setItem("access", response.headers.get("access"));

                // local storage name set
                const nickname = queryParams.get('nickname');
                window.localStorage.setItem("nickname", nickname);

                setIsLoggedIn(true);
                setLoginUser(nickname);
                
                if (nickname === "TempNickname") { // 닉네임이 "TempNickname"이면 닉네임 설정 페이지로 리다이렉트
                    navigate('/set-nickname');
                } else {                           // 아니면 Home 으로 리다이렉트
                    navigate('/', { replace: true });
                }
            } else {
                alert('접근할 수 없는 페이지입니다.');
            }
        } catch (error) {
            console.log("error: ", error);
        }
    }

    // request access token in header using httpOnly cookie, and set access token to local storage
    OAuth2JwtHeaderFetch();
    return;
};

export default OAuth2Redirect;