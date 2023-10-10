import React from "react";
import Login from "./Login/Login";
import { useState,useEffect } from "react";
import "./UI/style.css";
import Header from "./UI/Header";
import AdminMenu from './Admin/AdminMenu'
import StudentMenu from './Student/StudentMenu'
import Background from "./UI/Background";
import axios from "axios";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

export default function HomePage() {

  const showToastMessage = () => {
    toast.error('Unable to login. Please check credentials!', {
        position: toast.POSITION.TOP_RIGHT
    });
  };

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [selectedRole,setSelectedRole]=useState("ADMIN")

  useEffect(() => {
    const storedUserLoggedIn = localStorage.getItem('isLoggedIn');
    if (storedUserLoggedIn) {
      setIsLoggedIn(true);
    }
  }, []);


  const loginHandler = (username,password,role) => {
    console.log(username+password+role)
    let loginUrl=""
    if(role==="ADMIN"){
      loginUrl="http://localhost:8081/admin/login";
    }
    else if(role==="STUDENT"){
      loginUrl="http://localhost:8082/student/login";
    }
    else{
      loginUrl="http://localhost:8083/professor/login"
    }
    axios
      .post(loginUrl, {
        userName: username,
        password: password,
      })
      .then(function (response) {
        if(response.status===200){
          const authToken = response.data;
          localStorage.setItem('isLoggedIn', true);
          localStorage.setItem('role',role);
          localStorage.setItem('jwtToken',authToken)
          setSelectedRole(role)
          setIsLoggedIn(true)
        }
      })
      .catch((err) => {
        showToastMessage();
      });
  };


  const logoutHandler = () => {
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('role')
    localStorage.removeItem('jwtToken')
    setIsLoggedIn(false);
  };

  return (
    <React.Fragment>
      {/* <MainHeader isAuthenticated={isLoggedIn} onLogout={logoutHandler} /> */}
      <Background>
        <Header />
      </Background>
      <main>
        {!isLoggedIn && <Login onLogin={loginHandler} />}
        {isLoggedIn && selectedRole==="ADMIN" && <AdminMenu onLogout={logoutHandler} />}
        {isLoggedIn && selectedRole==="STUDENT" && <StudentMenu onLogout={logoutHandler} />}
        {/* {isLoggedIn && role==="PROFESSOR" && <ProfessorMenu onLogout={logoutHandler} />} */}

      </main>
      <ToastContainer />
    </React.Fragment>
  );

}
