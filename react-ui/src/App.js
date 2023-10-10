
import './App.css';
import AdminMenu from './Admin/AdminMenu';
import CourseCatalogComponent from './Admin/CourseCatalogComponent';
import Instructors from './Admin/Instructors';
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from './HomePage';
import AddCourse from './Admin/AddCourse';
import ReportCard from './Admin/ReportCard';

function App() {
  return (
    <React.Fragment>
       <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/admin" element={<AdminMenu />} />
          <Route path="/admin/courseCatalog" element={<CourseCatalogComponent />} />
          <Route path="/admin/instructors" element={<Instructors />} />
          <Route path="/admin/addCourse" element={<AddCourse />} />
          <Route path="/admin/generateReportCard" element={<ReportCard />} />
          
        </Routes>
      </Router>
    </React.Fragment>
  );
}

export default App;
