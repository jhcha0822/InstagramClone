import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useLogin } from '../contexts/AuthContext';
import './NavBar.css'; // 스타일을 분리하여 적용

const NavBar = () => {
    const { isLoggedIn } = useLogin();
    const [searchNickname, setSearchNickname] = useState('');
    const navigate = useNavigate();

    // 닉네임 검색 후 프로필 페이지로 이동
    const handleSearch = (e) => {
        e.preventDefault();
        if (searchNickname.trim()) {
            navigate(`/profile/${searchNickname}`);
            setSearchNickname('');  // 검색 후 입력창 초기화
        }
    };

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <div className="navbar-links">
                    <Link to="/">Home</Link>
                    {!isLoggedIn && <Link to="/join">Join</Link>}
                    {!isLoggedIn && <Link to="/login">Login</Link>}
                    {isLoggedIn && <Link to="/logout">Logout</Link>}
                    {isLoggedIn && <Link to="/follow">Follow</Link>}
                    {isLoggedIn && <Link to="/post">Post</Link>}
                    {isLoggedIn && <Link to="/edit-account">Account</Link>}
                    {isLoggedIn && <Link to="/change-password">Password</Link>}
                    {isLoggedIn && <Link to="/my-posts">My Post</Link>}
                </div>

                {/* 로그인된 상태에서만 검색 폼을 출력 */}
                {isLoggedIn && (
                    <form onSubmit={handleSearch} className="navbar-search-form">
                        <input
                            type="text"
                            placeholder="닉네임 검색"
                            value={searchNickname}
                            onChange={(e) => setSearchNickname(e.target.value)}
                            className="navbar-search-input"
                        />
                        <button type="submit" className="navbar-search-button">검색</button>
                    </form>
                )}
            </div>
        </nav>
    );
}

export default NavBar;
