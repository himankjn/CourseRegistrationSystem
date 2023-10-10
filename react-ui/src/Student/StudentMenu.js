import { useNavigate} from "react-router-dom";
import React from "react";
import Button from "../UI/Button";
import '../UI/style.css'

export default function AdminMenu(props) {
  const navigate = useNavigate();

  const showAvailableCoursesHandler = () => {
    //navigate("/student/availableCourses");
  };

  return (
    <React.Fragment >
        <h1 className='heading text-center'>Welcome Student</h1>
        <Button type="button" onClick={showAvailableCoursesHandler} text="Available Courses" />

        <Button className="logout-btn" type="button" onClick={props.onLogout} text="Logout" />
    </React.Fragment>
  );
}
