import axios from "axios";
import { useEffect, useState } from "react";
import React from "react";
import { DataGridComponent } from "./DataGridComponent";
import Background from "../UI/Background";
import Header from "../UI/Header";
import '../UI/style.css'

const adminLoginUrl = "http://localhost:8081/admin/login";
const adminCourseCatalogUrl = "http://localhost:8081/admin/courseCatalog";

function CourseCatalogComponent() {
  const [data, setData] = useState([]);
  let columns = [
    {
      field: "courseId",
      headerName: "Course Code",
      width: 150,
    },
    {
      field: "courseName",
      headerName: "Course Name",
      width: 150,
    },
    {
      field: "instructorId",
      headerName: "InstructorId",
      width: 150,
    },
    {
      field: "seats",
      headerName: "Available Sets",
      width: 150,
    },
    {
      field: "courseFee",
      headerName: "Course Fee",
      width: 150,
    },
    {
      field: "sem",
      headerName: "Sem",
      width: 150,
    },
  ];
  const fetchData = () => {
    axios
      .post(adminLoginUrl, {
        userName: "admin123@gmail.com",
        password: "admin",
      })
      .then(function (response) {
        const authToken = response.data;
        const adminCourseCatalogConfig = {
          headers: {
            Authorization: "Bearer ".concat(authToken),
          },
        };

        axios
          .get(adminCourseCatalogUrl, adminCourseCatalogConfig)
          .then((response) => {
            setData(response.data);
          });
      })
      .catch((err) => {
        console.log(err.message);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <React.Fragment>
      <Background>
        <Header />
      </Background>

      <h2 className='heading text-center'> Course Catalogue</h2>
      <DataGridComponent rows={data} columns={columns} rowId="courseId" />
    </React.Fragment>
  );
}

export default CourseCatalogComponent;
