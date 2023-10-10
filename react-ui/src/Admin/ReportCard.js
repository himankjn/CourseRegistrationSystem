import Score from './Score'
import {useEffect,useState} from 'react'
import axios from 'axios';
import Background from '../UI/Background';
import Header from '../UI/Header';
import Button from '../UI/Button';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function ReportCard(){
    const [cgpa,setCgpa]=useState(0)
    const [stdName,setstdName]=useState("")
    const [listOfScores, setListOfScores]= useState([])
    const [studentId, setStudentId]= useState("")

    const showErrorToast = () => {
        toast.error("Unable to fetch report card. Please check Student Id", {
          position: toast.POSITION.TOP_RIGHT,
        });
      };

    const fetchData = () => {
        const adminReportCardUrl="http://localhost:8081/admin/generateReportCard/"+studentId
        console.log(adminReportCardUrl)
        const authToken=localStorage.getItem('jwtToken')
        const adminReportCardConfig = {
          headers: {
            Authorization: "Bearer ".concat(authToken),
          },
        };
        axios
          .get(adminReportCardUrl, adminReportCardConfig)
          .then((response) => {
            console.log(response.data)
            let newList=[]
            response.data.regList.map((regItem=>{
                newList.push({
                    gpa:regItem.grade,
                    courseId:regItem.registeredCourseId.courseId
                })
                setstdName(response.data.studentId)
                setCgpa(response.data.cgpa)
                setListOfScores(newList)
            }))
          })
          .catch((err) => {
            showErrorToast();
          });
      };
    
      
      const studentIdSubmitHandler=(event)=>{
        event.preventDefault();
        setStudentId(document.getElementById("studentIdForReportCard").value)
      }

      useEffect(() => {
        if(studentId)
            fetchData();
      }, [studentId,studentIdSubmitHandler]);

    return(
            <>
            <Background>
                <Header/>
            </Background>
            <label htmlFor='studentIdForReportCard'>Student ID</label>
            <input type="text" id="studentIdForReportCard" />
            <Button type="button" className="" onClick={studentIdSubmitHandler} text={"Generate Report"}/>
              <div className="student">
                <div className="student-name"> {stdName} </div>
                <div className="student-name"> CGPA: {cgpa} </div>
                <div>
                  {listOfScores.map((score, idx) => (
                    <Score score={score} key={idx} />
                  ))}
                </div>
              </div>
              <ToastContainer />
            </>
          );
}
