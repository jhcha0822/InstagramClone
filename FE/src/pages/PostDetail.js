import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const PostDetail = () => {
    const { postId } = useParams();
    const [post, setPost] = useState(null);

    useEffect(() => {
        fetchPost();
    }, [postId]);

    const fetchPost = async () => {
        try {
            const token = window.localStorage.getItem("access");
            const response = await fetch(`http://localhost:8080/api/v1/post/${postId}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`, // Authorization 헤더 추가
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const data = await response.json();
                setPost(data);
            } else {
                alert('게시물을 불러오는 데 실패했습니다.');
            }
        } catch (error) {
            console.error('게시물 불러오기 중 오류:', error);
        }
    };

    return (
        <div className="post-detail">
            {post ? (
                <>
                    <h2>{post.writer}님의 게시물</h2>
                    {/* 이미지가 여러 개일 수 있으므로 map을 사용하여 모든 이미지를 출력 */}
                    {post.mediaUrls && post.mediaUrls.length > 0 ? (
                        post.mediaUrls.map((url, index) => (
                            <img key={index} src={url} alt={`Post Image ${index + 1}`}/>
                        ))
                    ) : (
                        <p>이미지를 불러올 수 없습니다.</p>
                    )}
                    <p>{post.content}</p>
                    {/* Unix timestamp를 적절히 변환하여 날짜를 표시 */}
                    <span>작성일: {post.regdate ? new Date(post.regdate).toLocaleString() : '날짜 정보 없음'}</span>
                </>
            ) : (
                <p>게시물을 불러오는 중입니다...</p>
            )}
        </div>
    );
};

export default PostDetail;
