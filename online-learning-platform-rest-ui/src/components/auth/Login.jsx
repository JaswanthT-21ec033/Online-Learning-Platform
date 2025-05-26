import { useState } from "react";
import {Link,useNavigate } from "react-router-dom";
import axios from "axios";
import"../../App.css";



function Login(){
    const[username,setusername] = useState(null);
    const[password,setpassword] = useState(null);
    const[msgusername,setmsgusername] = useState(null);
    const[msgpassword,setmsgpassword] = useState(null);
    const navigate = useNavigate();

    const login = () => {
        let isCorrect = false;

        if(username === null || username === "" || username === undefined){
            setmsgusername("username can not be blank");
            return;

        }
        else{
            setmsgpassword(null);
        }

        if(password === null || password ==="" || password === undefined){
            setmsgpassword("password can not be blank");
            return;
        }
        else{
            setmsgpassword(null);

        }

        let body = {
            'username': username,
            'password': password
        };

        axios.post('http://localhost:8084/api/auth/token/generate', body).then((response) =>{
            let token = response.data.token;
            localStorage.setItem('token',token);
            localStorage.setItem('username',username);

            axios.get('http://localhost:8084/api/auth/user/details',{
                headers: {
                    "Authorization": `Bearer ${token}`,
                }
            })
            .then((resp) => {
                console.log(resp);
                switch (resp.data.role){
                    case 'INSTRUCTOR':
                        navigate("/instructor");
                        break;
                    case 'STUDENT':
                        navigate("/student")
                        break;
                    default:
                        break;

                }
            })
            .catch((err) => {
                setmsgusername("Invalid Credentials");
                console.log(err);
            });
        })
        .catch((err) => {
            setmsgusername("Invalid Credentials");
            console.log(err);
        });
    };


   
      return (
  <div className="login-container">
      <div className="card shadow">
        <div className="card-body">
          <h3 className="text-center">Welcome to Online Learning Platform</h3>
          <p className="text-center text-muted">Please sign in to continue</p>

          {msgusername && <div className="alert alert-danger">{msgusername}</div>}
          {msgpassword && <div className="alert alert-danger">{msgpassword}</div>}

          <div className="mb-3">
            <label className="form-label">Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter your username"
              onChange={(e) => {
                setusername(e.target.value);
                setmsgusername(null);
              }}
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              onChange={(e) => {
                setpassword(e.target.value);
                setmsgpassword(null);
              }}
            />
          </div>

          <div className="d-grid mb-3">
            <button className="btn btn-primary" onClick={login}>Login</button>
          </div>

         
        </div>
      </div>
    </div>
  );
}




export default Login;