import { useState } from "react";
import { useNavigate } from "react-router-dom";

const JoinForm = () => {
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');

    const [code, setCode] = useState(''); // 보안 코드 입력 필드

    const sendSecurityCode = async () => {
        await fetch(`http://localhost:8080/api/v1/sendSecurityCode?email=${email}`, {
            method: "POST",
        });
        alert("입력한 이메일로 보안코드가 전송되었습니다.");
    }

    const fetchJoin = async (credentials) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/join?code=${code}`, {
                method: "POST",
                credentials: 'include',
                headers: {
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                    'Content-Type': 'application/json',
                },
                // body: new URLSearchParams(credentials),
                body: JSON.stringify(credentials),
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
        const response = await fetch(`http://localhost:8080/api/v1/verifySecurityCode?username=${username}&email=${email}&code=${code}`, {
            method: "POST",
        });

        if (response.ok) {
            const credentials = { username, password, nickname, email };
            await fetchJoin(credentials);
        } else {
            alert("보안 코드가 올바르지 않습니다.");
        }
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
                <p><span className='label'>Security Code</span><input type="text" value={code}
                                                                      onChange={(e) => setCode(e.target.value)}/>
                   <button type="button" onClick={sendSecurityCode}>Send Security Code</button>
                </p>

                <input type="submit" value="Join" className="form-btn"/>
            </form>
        </div>
    );
}

export default JoinForm;