import React, { useEffect, useState } from 'react';

const MyPosts = () => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        fetchMyPosts();
    }, []);

    const fetchMyPosts = async () => {
        try {
            const token = localStorage.getItem('access');
            const response = await fetch('${process.env.REACT_APP_API_BASE_URL}/api/v1/users/me/posts', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                throw new Error('Failed to fetch posts');
            }
            const data = await response.json();
            setPosts(data);
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    };

    return (
        <div>
            <h1>내 게시글</h1>
            {posts.length > 0 ? (
                posts.map((post, index) => (
                    <div key={index} className="post">
                        <p>{post.content}</p>
                        {post.mediaUrls && post.mediaUrls.map((url, idx) => (
                            <img key={idx} src={`${process.env.REACT_APP_API_BASE_URL}/images/${url}`} alt={`media-${idx}`} />
                        ))}
                        <p>작성일: {post.createdAt}</p>
                    </div>
                ))
            ) : (
                <p>게시글이 없습니다.</p>
            )}
        </div>
    );
}

export default MyPosts;