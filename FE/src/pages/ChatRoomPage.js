import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const ChatRoomPage = () => {
    const [chatRooms, setChatRooms] = useState([]);
    const [nickname, setNickname] = useState('');
    const [messages, setMessages] = useState([]);
    const [currentRoomId, setCurrentRoomId] = useState(null);
    const [error, setError] = useState('');
    const [stompClient, setStompClient] = useState(null);

    const accessToken = localStorage.getItem('access');

    useEffect(() => {
        const fetchChatRooms = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/chat/rooms`, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
                setChatRooms(response.data);
            } catch (err) {
                console.error(err);
                setError('채팅방을 가져오는 데 오류가 발생했습니다.');
            }
        };
        fetchChatRooms();
    }, [accessToken]);

    const createChatRoom = async () => {
        if (nickname) {
            try {
                const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/chat/rooms`, [nickname], {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });
                setChatRooms([...chatRooms, response.data]);
                setNickname('');
                setError('');
            } catch (err) {
                console.error(err);
                if (err.response && err.response.status === 409) {
                    setError('이미 채팅방이 생성되어 있습니다.');
                } else {
                    setError('채팅방 생성에 실패했습니다.');
                }
            }
        }
    };

    useEffect(() => {
        if (stompClient) {
            stompClient.connect(
                { Authorization: `Bearer ${accessToken}` },
                () => {
                    console.log('WebSocket 연결 성공');
                    if (currentRoomId) {
                        stompClient.subscribe(`/topic/messages/${currentRoomId}`, (message) => {
                            const newMessage = JSON.parse(message.body);
                            setMessages((prevMessages) => [...prevMessages, newMessage]);
                        });
                    }
                },
                (error) => {
                    console.error('WebSocket 연결 실패:', error);
                    setError('WebSocket 연결에 실패했습니다.');
                }
            );

            return () => stompClient.disconnect(); // 컴포넌트 언마운트 시 연결 해제
        }
    }, [stompClient, currentRoomId, accessToken]);

    const fetchMessages = async (roomId) => {
        try {
            const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/messages/${roomId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            setMessages(response.data);
            setCurrentRoomId(roomId);

            // WebSocket 연결 설정
            if (stompClient) {
                stompClient.disconnect(); // 기존 연결 해제
            }

            const socket = new SockJS(`${process.env.REACT_APP_API_BASE_URL}/ws`);
            const client = Stomp.over(socket);
            setStompClient(client); // stompClient 상태 업데이트
        } catch (err) {
            console.error(err);
            setError('메시지를 가져오는 데 오류가 발생했습니다.');
        }
    };

    const sendMessage = (content) => {
        if (currentRoomId && stompClient && stompClient.connected) {
            const message = {
                roomId: currentRoomId,
                sender: window.localStorage.getItem("nickname"),
                content: content,
            };
            stompClient.send('/app/chat', {}, JSON.stringify(message));
        }
    };

    return (
        <div>
            <h1>채팅방 목록</h1>
            <input
                type="text"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                placeholder="닉네임 검색"
            />
            <button onClick={createChatRoom}>채팅방 생성</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {chatRooms.length === 0 ? (
                    <li>채팅방이 없습니다.</li>
                ) : (
                    chatRooms.map(room => (
                        <li key={room.id} onClick={() => fetchMessages(room.id)}>
                            {room.users.join(', ')}
                        </li>
                    ))
                )}
            </ul>

            {currentRoomId && (
                <div>
                    <h2>{currentRoomId} 채팅방</h2>
                    <ul>
                        {messages.map((msg, index) => (
                            <li key={index}>
                                <strong>{msg.sender}</strong>: {msg.content}
                            </li>
                        ))}
                    </ul>
                    <input
                        type="text"
                        onKeyPress={e => {
                            if (e.key === 'Enter') {
                                sendMessage(e.target.value);
                                e.target.value = '';
                            }
                        }}
                    />
                </div>
            )}
        </div>
    );
};

export default ChatRoomPage;
