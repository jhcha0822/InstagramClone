import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios'; // 서버와의 비동기 요청을 위해 추가
import { useLogin } from '../contexts/AuthContext';
import './NavBar.css'; // 스타일을 분리하여 적용

const NavBar = () => {
    const { isLoggedIn } = useLogin();
    const [searchNickname, setSearchNickname] = useState('');
    const [searchResults, setSearchResults] = useState([]); // 검색 결과 저장
    const [showResults, setShowResults] = useState(false); // 검색 결과 표시 여부
    const navigate = useNavigate();

    // 닉네임 검색 후 프로필 페이지로 이동
    const handleSearch = (e) => {
        e.preventDefault(); // 새로고침 방지
        console.log('검색 닉네임:', searchNickname); // 검색어 확인
        if (searchNickname.trim()) {
            console.log(`이동할 경로: /profile/${searchNickname}`); // 이동 경로 확인
            navigate(`/profile/${searchNickname}`);
            setSearchNickname('');  // 검색 후 입력창 초기화
            setShowResults(false);  // 검색 결과 숨김
        }
    };

    // 실시간 검색 요청
    useEffect(() => {
        const fetchResults = async () => {
            if (searchNickname.trim() !== '') {
                const token = window.localStorage.getItem('access'); // 저장된 access 토큰 가져오기
                try {
                    const response = await axios.get('http://localhost:8080/api/v1/user/search', {
                        params: { query: searchNickname },
                        headers: {
                            'Authorization': `Bearer ${token}`, // 헤더에 토큰 추가
                        },
                    });
                    setSearchResults(response.data); // 검색 결과 저장
                    setShowResults(true); // 검색 결과 표시
                } catch (error) {
                    console.error('검색 중 에러 발생:', error);
                }
            } else {
                setSearchResults([]); // 검색어가 없을 경우 결과 초기화
                setShowResults(false); // 결과 숨김
            }
        };

        const delayDebounceFn = setTimeout(() => {
            fetchResults();
        }, 300); // 입력 후 300ms 대기 (디바운스)

        return () => clearTimeout(delayDebounceFn);
    }, [searchNickname]);

    // 검색 결과 클릭 시 프로필 페이지로 이동
    const handleResultClick = (nickname) => {
        navigate(`/profile/${nickname}`);
        setSearchNickname('');  // 검색 후 입력창 초기화
        setShowResults(false);  // 검색 결과 숨김
    };

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <div className="navbar-links">
                    <Link to="/">Home</Link>
                    {!isLoggedIn && <Link to="/join">Join</Link>}
                    {!isLoggedIn && <Link to="/login">Login</Link>}
                    {!isLoggedIn && <Link to="/reset-password">Forgot Password?</Link>}
                    {isLoggedIn && <Link to="/logout">Logout</Link>}
                    {isLoggedIn && <Link to="/follow">Follow</Link>}
                    {isLoggedIn && <Link to="/post">Post</Link>}
                    {isLoggedIn && <Link to="/edit-account">Account</Link>}
                    {isLoggedIn && <Link to="/change-password">Password</Link>}
                    {isLoggedIn && <Link to="/favorites">Favorites</Link>}
                    {isLoggedIn && <Link to="/DMPage">ChatRoom</Link>}
                    {/*{isLoggedIn && <Link to="/my-posts">My Post</Link>}*/}
                </div>

                {/* 로그인된 상태에서만 검색 폼을 출력 */}
                {isLoggedIn && (
                    <div className="navbar-search">
                        <form onSubmit={handleSearch} className="navbar-search-form">
                            <input
                                type="text"
                                placeholder="닉네임 검색"
                                value={searchNickname}
                                onChange={(e) => setSearchNickname(e.target.value)}
                                className="navbar-search-input"
                            />
                            {/*<button type="submit" className="navbar-search-button">검색</button>*/}
                        </form>

                        {/* 검색 결과 목록 표시 */}
                        {showResults && searchResults.length > 0 && (
                            <div className="sidebar-search-results">
                                <div className="list-group">
                                    {searchResults.map((result) => (
                                        <a key={result.id} href={`/profile/${result.nickname}`} className="list-group-item">
                                            <div className="search-title">
                                                {/* 프로필 사진 추가 */}
                                                <img src={result.profilePic || 'default-profile.png'}
                                                     alt={result.nickname} className="profile-image"/>
                                                <strong className="text-light">{result.nickname}</strong>
                                            </div>
                                            <div className="search-path">{result.realName}</div>
                                        </a>
                                    ))}
                                </div>
                            </div>
                        )}
                    </div>
                )}
            </div>
        </nav>
    );
}

export default NavBar;
