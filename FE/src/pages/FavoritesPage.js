import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const FavoritesPage = () => {
    const [favoritePosts, setFavoritePosts] = useState([]);
    const navigate = useNavigate();

    // 즐겨찾기된 게시글 목록 가져오기
    useEffect(() => {
        fetchFavoritePosts();
    }, []);

    const fetchFavoritePosts = async () => {
        try {
            const token = localStorage.getItem('access');
            const response = await fetch('http://localhost:8080/api/favorites', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const data = await response.json();
                setFavoritePosts(data);
            } else {
                alert('즐겨찾기한 게시글을 불러올 수 없습니다.');
            }
        } catch (error) {
            console.error('즐겨찾기 게시글 로드 중 오류:', error);
        }
    };

    return (
        <div className="favorites-page">
            <h2>내가 저장한 게시글</h2>
            <div className="post-grid">
                {favoritePosts.map((post) => (
                    <div
                        key={post.postId}
                        className="post-thumbnail"
                        onClick={() => navigate(`/post/${post.postId}`)}
                    >
                        <img src={post.mediaUrl || 'default-image.png'} alt="post-thumbnail" />
                    </div>
                ))}
            </div>
        </div>
    );
};

export default FavoritesPage;
