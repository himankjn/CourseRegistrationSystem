import axios from "axios";
import { useEffect, useState } from "react";
import React from "react";
import { DataGridComponent } from "./DataGridComponent";
import Background from "../UI/Background";
import Header from "../UI/Header";
import '../UI/style.css'

const adminInstructorsUrl = "http://localhost:8081/admin/instructors";

function CourseCatalogComponent() {
  const [data, setData] = useState([]);
  let columns = [
    {
      field: "userId",
      headerName: "Professor Id",
      width: 150,
    },
    {
      field: "password",
      headerName: "Password",
      width: 150,
    },
    {
      field: "gender",
      headerName: "Gender",
      width: 150,
    },
    {
      field: "role",
      headerName: "Role",
      width: 150,
    },
    {
      field: "address",
      headerName: "Address",
      width: 150,
    },
    {
      field: "name",
      headerName: "Name",
      width: 150,
    },
    {
      field: "professorId",
      headerName: "ProfId",
      width: 150,
    },
    {
      field: "department",
      headerName: "Dept",
      width: 150,
    },
    {
      field: "designation",
      headerName: "Designation",
      width: 150,
    },
  ];

  const fetchData = () => {
    const authToken=localStorage.getItem('jwtToken')
    const adminCourseCatalogConfig = {
      headers: {
        Authorization: "Bearer ".concat(authToken),
      },
    };
    axios
      .get(adminInstructorsUrl, adminCourseCatalogConfig)
      .then((response) => {
        setData(response.data);
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

      <h2 className='heading text-center'> Instructors</h2>
      <DataGridComponent rows={data} columns={columns} rowId={'userId'}/>
    </React.Fragment>
  );
}

export default CourseCatalogComponent;
