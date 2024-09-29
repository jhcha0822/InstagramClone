import { useState } from "react";
import { useNavigate } from "react-router-dom";

const JoinForm = () => {
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');

    const fetchJoin = async (credentials) => {
        try {
            const response = await fetch("http://localhost:8080/api/v1/join", {
                method: "POST",
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams(credentials),
            });
            if (response.ok) {
                alert("Join Successful");
                navigate("/login", {replace: true});
            } else {
                alert("Join Failed");
            }
        } catch (error) {
            console.log("Error: ", error);
        }
    }

    const joinHandler = async (e) => {
        e.preventDefault();
        const credentials = { username, password, nickname, email };  // 닉네임 추가
        fetchJoin(credentials);
    }

    return (
        <div className="join">
            <h1>Join</h1>
            <form onSubmit={joinHandler}>
                <p><span className='label'>Username</span><input className='input-class' type="text" name="username"
                                                                 value={username} placeholder="username"
                                                                 onChange={(e) => setUsername(e.target.value)}/></p>
                <p><span className='label'>Password</span><input className='input-class' type="password"
                                                                 autoComplete="off" name="password"
                                                                 placeholder="password"
                                                                 onChange={(e) => setPassword(e.target.value)}/></p>
                <p><span className='label'>Nickname</span><input className='input-class' type="text" name="nickname"
                                                                 value={nickname} placeholder="nickname"
                                                                 onChange={(e) => setNickname(e.target.value)}/></p>
                <p><span className='label'>Email</span><input className='input-class' type="text" name="email"
                                                                 value={email} placeholder="email"
                                                                 onChange={(e) => setEmail(e.target.value)}/></p>
                <input type="submit" value="Join" className="form-btn"/>
            </form>
        </div>
    );
}

export default JoinForm;