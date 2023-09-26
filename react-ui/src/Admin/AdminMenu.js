import { useNavigate} from "react-router-dom";
import React from "react";
import Button from "../UI/Button";
import Background from "../UI/Background";
import '../UI/style.css'
import Header from "../UI/Header";

export default function AdminMenu() {
  const navigate = useNavigate();

  const showCourseCatalogueHandler = () => {
    navigate("/admin/courseCatalog");
  };

  const showInstructorsHandler = () => {
    navigate("/admin/instructors");
  };
  
  const addCourseHandler= () => {
    navigate("/admin/addCourse");
  };
  
  return (
    <React.Fragment >
      <Background>
        <Header />
      </Background>
        <h1 className='heading text-center'>Welcome Admin!</h1>
        <Button type="button" onClick={showCourseCatalogueHandler} text="Course Catalogue" />
        <Button type="button" onClick={showInstructorsHandler} text="Instructors" />
        <Button type="button" onClick={addCourseHandler} text="Add Course" />
        <Button type="button" text="Add Professor" />
        <Button type="button" text="Approve Student(s)" />
        <Button type="button" onClick={addCourseHandler} text="Course Assignment to professor" />
        <Button type="button" onClick={addCourseHandler} text="Course Assignment Requests" />
    </React.Fragment>
  );
}
