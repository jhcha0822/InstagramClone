import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useLogin } from "../contexts/AuthContext";
import Follow from './Follow';  // Follow.js 파일에서 가져온 팔로우 관련 기능

const ProfilePage = () => {
    const { nickname } = useParams();
    const { isLoggedIn, loginUser } = useLogin();
    const [profile, setProfile] = useState(null);
    const [isFollowing, setIsFollowing] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if (!isLoggedIn) {
            alert("로그인이 필요합니다.");
            navigate("/login", { replace: true });
        } else {
            fetchProfile();
            checkIfFollowing();
        }
    }, [isLoggedIn]);

    const fetchProfile = async () => {
        try {
            const token = window.localStorage.getItem("access");
            const response = await fetch(`http://localhost:8080/api/v1/${nickname}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const data = await response.json();
                setProfile(data);
            } else {
                alert("프로필 정보를 불러오지 못했습니다.");
            }
        } catch (error) {
            console.error('프로필 정보 불러오기 중 오류:', error);
        }
    };

    const checkIfFollowing = async () => {
        // 팔로우 여부를 체크하는 로직
        const token = window.localStorage.getItem('access');
        const response = await fetch(`http://localhost:8080/api/v1/${loginUser}/following/${nickname}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });
        // 팔로우 중이라면 setIsFollowing(true);
        if (response.ok) {
            setIsFollowing(true);
        } else {
            setIsFollowing(false);
        }
    };

    const handleFollow = async () => {
        // 팔로우 요청을 처리하는 로직
        await Follow.handleFollow(nickname); // Follow.js에 있는 handleFollow 함수 사용
        setIsFollowing(true); // 팔로우 완료 후 상태 업데이트
    };

    const handleUnfollow = async () => {
        await Follow.handleUnfollow(nickname); // Follow.js에 있는 handleUnfollow 함수 사용
        setIsFollowing(false); // 언팔로우 완료 후 상태 업데이트
    };

    const handleDM = () => {
        // DM 기능 구현 (추후 구현 가능)
    };

    return (
        <div className="profile-page">
            {profile && (
                <>
                    <div className="profile-header">
                        <img
                            src={profile.profilePic || 'default-profile-pic.png'}
                            alt="Profile Pic"
                        />
                        <h2>{profile.nickname}</h2>
                        <p>{profile.bio || '소개글이 없습니다.'}</p>
                        <div className="profile-stats">
                            <span>게시물: {profile.postCount}</span>
                            <span>팔로워: {profile.followerCount}</span>
                            <span>팔로잉: {profile.followingCount}</span>
                        </div>
                        {loginUser !== nickname && (
                            <div className="profile-actions">
                                {isFollowing ? (
                                    <button onClick={handleUnfollow}>언팔로우</button>
                                ) : (
                                    <button onClick={handleFollow}>팔로우</button>
                                )}
                                <button onClick={handleDM}>DM 보내기</button>
                            </div>
                        )}
                    </div>

                    <div className="post-grid">
                        {profile.postThumbnails.map((post, index) => (
                            <div
                                key={index}
                                className="post-thumbnail"
                                onClick={() => navigate(`/post/${post.postId}`)}
                            >
                                <img
                                    src={post.mediaUrl}
                                    alt={`post-${index}`}
                                />
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    );
};

export default ProfilePage;
