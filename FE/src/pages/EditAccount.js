import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const EditAccount = () => {
    const [nickname, setNickname] = useState('');
    const [profilePic, setProfilePic] = useState('');
    const [bio, setBio] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        // 기본 사용자 정보를 로딩 (예: API를 통해 가져옴)
        const token = window.localStorage.getItem("access");
        fetch("http://localhost:8080/api/v1/user/me", {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                setNickname(data.nickname);
                setProfilePic(data.profilePic);
                setBio(data.bio);
            })
            .catch(error => console.log("계정 정보를 로드하는 중 오류 발생:", error));
    }, []);

    const handleUpdateAccount = async (e) => {
        e.preventDefault();
        const token = window.localStorage.getItem("access");
        try {
            const response = await fetch("http://localhost:8080/api/v1/user/update", {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nickname, profilePic, bio })
            });
            if (response.ok) {
                alert("계정 정보가 성공적으로 업데이트되었습니다.");
                navigate("/profile"); // 프로필 페이지로 리디렉션
            } else {
                alert("계정 정보 업데이트 실패");
            }
        } catch (error) {
            console.log("계정 정보 업데이트 중 오류:", error);
        }
    }

    // 인라인 스타일링 객체
    const styles = {
        container: {
            maxWidth: '400px',
            margin: '0 auto',
            padding: '20px',
            border: '1px solid #ccc',
            borderRadius: '8px',
            backgroundColor: '#f9f9f9',
            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
        },
        formGroup: {
            marginBottom: '15px',
        },
        label: {
            marginBottom: '5px',
            fontWeight: 'bold',
            display: 'block',
        },
        input: {
            width: '100%',
            padding: '10px',
            border: '1px solid #ccc',
            borderRadius: '4px',
            boxSizing: 'border-box',
            fontSize: '14px',
        },
        button: {
            padding: '10px',
            backgroundColor: '#4a90e2',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '16px',
        },
        buttonHover: {
            backgroundColor: '#357ab8',
        },
    };

    return (
        <div className="edit-account" style={styles.container}>
            <h1>내 계정 정보 수정</h1>
            <form onSubmit={handleUpdateAccount} className="edit-form" style={styles.form}>
                <div className="form-group" style={styles.formGroup}>
                    <label style={styles.label}>닉네임</label>
                    <input style={styles.input} className='input-class' type="text" value={nickname} onChange={(e) => setNickname(e.target.value)} />
                </div>
                <div className="form-group" style={styles.formGroup}>
                    <label style={styles.label}>프로필 사진 URL</label>
                    <input style={styles.input} className='input-class' type="text" value={profilePic} onChange={(e) => setProfilePic(e.target.value)} />
                </div>
                <div className="form-group" style={styles.formGroup}>
                    <label style={styles.label}>소개글</label>
                    <textarea style={styles.input} className='input-class' value={bio} onChange={(e) => setBio(e.target.value)} />
                </div>
                <input type="submit" value="Update Account" style={styles.button} className="form-btn" />
            </form>
        </div>
    );
}

export default EditAccount;
