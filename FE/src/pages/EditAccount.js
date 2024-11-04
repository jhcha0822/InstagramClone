import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const EditAccount = () => {
    const [nickname, setNickname] = useState('');
    const [profilePic, setProfilePic] = useState('');
    const [bio, setBio] = useState('');
    const [previewUrl, setPreviewUrl] = useState(''); // 미리보기 URL
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

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setProfilePic(file);
        setPreviewUrl(URL.createObjectURL(file)); // 이미지 미리보기 설정
    };

    const handleUpdateAccount = async (e) => {
        e.preventDefault();
        const token = window.localStorage.getItem("access");

        const formData = new FormData();
        formData.append('nickname', nickname);
        formData.append('bio', bio);
        if (profilePic) {
            formData.append('profilePicFile', profilePic); // 프로필 사진 추가
        }

        // FormData 내용 확인
        for (let pair of formData.entries()) {
            console.log(`${pair[0]}, ${pair[1]}`);
        }

        try {
            const response = await fetch("http://localhost:8080/api/v1/user/update", {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    // 'Content-Type': 'application/json'
                },
                body: formData
                // body: JSON.stringify({ nickname, profilePic, bio })
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

    return (
        <div className="edit-account" style={{ maxWidth: '400px', margin: '0 auto', padding: '20px', border: '1px solid #ccc', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
            <h1>내 계정 정보 수정</h1>
            <form onSubmit={handleUpdateAccount} className="edit-form">
                <div className="form-group">
                    <label>닉네임</label>
                    <input type="text" value={nickname} onChange={(e) => setNickname(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>프로필 사진</label>
                    <input type="file" accept="image/*" onChange={handleFileChange} />
                    {previewUrl && <img src={previewUrl} alt="Profile Preview" width="100" />}
                </div>
                <div className="form-group">
                    <label>소개글</label>
                    <textarea value={bio} onChange={(e) => setBio(e.target.value)} />
                </div>
                <input type="submit" value="Update Account" />
            </form>
        </div>
    );
}

export default EditAccount;
