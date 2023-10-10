import React from "react";

const Score = (props) => (
  <div class="score-date">
    <div class="score">
      <h4>Course: {props.score.courseId}</h4>
    </div>
    <div class="date">
      <h4>GPA: {props.score.gpa}</h4>
    </div>
  </div>
);

export default Score;
