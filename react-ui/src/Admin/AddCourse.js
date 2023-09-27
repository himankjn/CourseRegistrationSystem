import axios from "axios";
import React from "react";
import Background from "../UI/Background";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header from "../UI/Header";
import "../UI/style.css";
import AddCourseForm from "./AddCourseForm";

function AddCourse() {
  const adminAddCourseUrl = "http://localhost:8081/admin/addCourse";

  const showSuccessToast = () => {
    toast.success("Added New Course to Catalog !", {
      position: toast.POSITION.TOP_RIGHT,
    });
  };

  const showErrorToast = () => {
    toast.error("Unable to add course. Try again!", {
      position: toast.POSITION.TOP_RIGHT,
    });
  };

  function addNewCourse(event) {
    const courseId = event.target.elements.courseId.value;
    const courseName = event.target.elements.courseName.value;
    const courseFee = +event.target.elements.courseFee.value;
    const seats = +event.target.elements.seats.value;
    const sem = +event.target.elements.sem.value;
    const instructorId = event.target.elements.instructorId.value;

    const authToken = localStorage.getItem("jwtToken");
    const adminAddCourseConfig = {
      headers: {
        Authorization: "Bearer ".concat(authToken),
      },
    };
    axios
      .post(
        adminAddCourseUrl,
        {
          courseFee: courseFee,
          courseId: courseId,
          courseName: courseName,
          instructorId: instructorId,
          seats: seats,
          sem: sem,
        },
        adminAddCourseConfig
      )
      .then((response) => {
        if (response.status === 200) {
          showSuccessToast();
        }
      })
      .catch((err) => {
        showErrorToast();
      });
  }

  return (
    <React.Fragment>
      <Background>
        <Header />
      </Background>

      <h2 className="heading text-center"> Add Course</h2>
      <AddCourseForm addNewCourse={addNewCourse} />
      <ToastContainer />
    </React.Fragment>
  );
}

export default AddCourse;
