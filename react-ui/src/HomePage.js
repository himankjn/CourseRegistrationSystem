import React from "react";
import Button from "./UI/Button";
import { useNavigate } from "react-router-dom";
import "./UI/style.css";
import Header from "./UI/Header";
import Background from "./UI/Background";

export default function HomePage() {
  const navigate = useNavigate();

  const showAdminMenuHandler = () => {
    navigate("/admin");
  };

  return (
    <React.Fragment>
        <Background>
            <Header />
        </Background>
      
      <h1 className="heading text-center">Wibmo CRS</h1>
      <div>
        <Button type="button" onClick={showAdminMenuHandler} text="Admin Menu" />
        <Button type="button"  text="Professor Menu" />
        <Button type="button"  text="Student Menu" />
      </div>
    </React.Fragment>
  );
}
