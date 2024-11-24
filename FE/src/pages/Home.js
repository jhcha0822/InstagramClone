import { useLogin } from "../contexts/AuthContext";
import React, { useEffect, useState } from 'react';

const Home = () => {
    const { isLoggedIn, loginUser, setLoginUser } = useLogin();
    const [posts, setPosts] = useState([]);
    const [comments, setComments] = useState({});  // 댓글 데이터 저장
    const [commentInputs, setCommentInputs] = useState({});  // 댓글 입력값 저장
    const [replyInputs, setReplyInputs] = useState({});  // 대댓글 입력값 저장
    const [activeReplyId, setActiveReplyId] = useState(null);  // 활성화된 대댓글 입력란 ID

    // 댓글 입력 핸들러
    const handleCommentChange = (postId, value) => {
        setCommentInputs(prev => ({
            ...prev,
            [postId]: value
        }));
    };

    // 대댓글 입력 핸들러
    const handleReplyChange = (commentId, value) => {
        setReplyInputs(prev => ({
            ...prev,
            [commentId]: value
        }));
    };

    // 댓글 작성 함수
    const handleCommentSubmit = async (postId) => {
        const token = localStorage.getItem('access');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/comment/${postId}?comment=${commentInputs[postId]}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                alert('댓글이 작성되었습니다.');
                setCommentInputs(prev => ({ ...prev, [postId]: '' }));
                fetchComments(postId);  // 댓글 갱신
            } else {
                console.error('Error creating comment');
            }
        } catch (error) {
            console.error('Error submitting comment:', error);
        }
    };

    // 대댓글 작성 함수
    const handleReplySubmit = async (postId, parentCommentId) => {
        const token = localStorage.getItem('access');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/reply/${postId}?reply=${replyInputs[parentCommentId]}&parentCommentId=${parentCommentId}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                alert('대댓글이 작성되었습니다.');
                setReplyInputs(prev => ({ ...prev, [parentCommentId]: '' }));
                setActiveReplyId(null);  // 대댓글 입력창 닫기
                fetchComments(postId);  // 댓글 갱신
            } else {
                console.error('Error creating reply');
            }
        } catch (error) {
            console.error('Error submitting reply:', error);
        }
    };

    // 댓글 및 대댓글 조회
    const fetchComments = async (postId) => {
        const token = localStorage.getItem('access');
        try {
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/comments/${postId}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const data = await response.json();
                setComments(prevComments => ({
                    ...prevComments,
                    [postId]: data,  // 댓글 데이터를 상태에 저장
                }));
            } else {
                console.error('Error fetching comments');
            }
        } catch (error) {
            console.error('Error fetching comments:', error);
        }
    };

    // 게시글 목록 조회
    const fetchPosts = async () => {
        try {
            const token = localStorage.getItem('access');
            const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/api/v1/posts`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const text = await response.text();
            const data = JSON.parse(text);
            setPosts(data);
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    };

    // 로그인 상태와 게시글 데이터를 초기화
    useEffect(() => {
        const storedNickname = window.localStorage.getItem("nickname");
        if (storedNickname) {
            setLoginUser(storedNickname);
        }

        if (isLoggedIn) {
            fetchPosts();
        }
    }, [isLoggedIn]);

    // 게시글에 대한 댓글 및 대댓글 조회
    useEffect(() => {
        if (isLoggedIn) {
            posts.forEach((post) => {
                fetchComments(post.postId);
            });
        }
    }, [posts, isLoggedIn]);

    return (
        <div className="container">
            <h1>Home</h1>
            {isLoggedIn && <span>{loginUser}님 환영합니다.</span>}
            <hr />
            {isLoggedIn && posts.length > 0 ? (
                <div className="posts">
                    {posts.map((post) => (
                        <div key={post.postId} className="post">
                            <p>작성자: {post.writer}</p>
                            <p>{post.content}</p>
                            <div className="media-container">
                                {post.mediaUrls?.map((url, idx) => {
                                    const isVideo = url.endsWith('.mp4') || url.endsWith('.mov');
                                    const s3Url = `${url}`;

                                    return isVideo ? (
                                        <video key={`${post.postId}-video-${idx}`} controls style={{ width: '200px', height: '200px', objectFit: 'contain' }}>
                                            <source src={s3Url} type="video/mp4" />
                                            Your browser does not support the video tag.
                                        </video>
                                    ) : (
                                        <img key={`${post.postId}-img-${idx}`} src={s3Url} alt={`Media ${idx}`} style={{ width: '200px', height: '200px', objectFit: 'contain' }} />
                                    );
                                })}
                            </div>

                            {/* 댓글 입력 */}
                            <div className="comment-input">
                                <input
                                    type="text"
                                    value={commentInputs[post.postId] || ''}
                                    onChange={(e) => handleCommentChange(post.postId, e.target.value)}
                                    placeholder="댓글을 입력하세요."
                                />
                                <button onClick={() => handleCommentSubmit(post.postId)}>
                                    댓글 작성
                                </button>
                            </div>

                            {/* 댓글 목록 */}
                            <div className="comments">
                                {comments[post.postId]?.map((comment) => (
                                    <div key={comment.id} className="comment">
                                        <p>{comment.nickname}: {comment.comment}</p>
                                        
                                        {/* 대댓글 목록 */}
                                        <div className="replies" style={{ marginLeft: '20px' }}>
                                            {comment.children?.map((reply) => (
                                                <div key={reply.id} className="reply">
                                                    <p>{reply.nickname}: {reply.comment}</p>
                                                </div>
                                            ))}
                                        </div>
                                        
                                        {/* 대댓글 입력 */}
                                        <div className="reply-input">
                                            {activeReplyId === comment.id ? (
                                                <div>
                                                    <input
                                                        type="text"
                                                        value={replyInputs[comment.id] || ''}
                                                        onChange={(e) => handleReplyChange(comment.id, e.target.value)}
                                                        placeholder="대댓글을 입력하세요"
                                                    />
                                                    <button onClick={() => handleReplySubmit(post.postId, comment.id)}>
                                                        작성
                                                    </button>
                                                    <button onClick={() => setActiveReplyId(null)}>
                                                        취소
                                                    </button>
                                                </div>
                                            ) : (
                                                <button onClick={() => setActiveReplyId(comment.id)}>
                                                    답글 달기
                                                </button>
                                            )}
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <hr />
                        </div>
                    ))}
                </div>
            ) : (
                isLoggedIn && <p>업로드된 게시글이 없습니다.</p>
            )}
        </div>
    );
};

export default Home;
