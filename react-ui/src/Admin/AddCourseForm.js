import "../UI/form.css";

export default function AddCourseForm(props) {

    function submitHandler(event){
        event.preventDefault();
        props.addNewCourse(event);
    }

  return (
    <div className="form-style-5">
      <form onSubmit={submitHandler}>
        <fieldset>
          <legend>
            <span className="number">1</span> Course Info
          </legend>
          <input type="text" name="courseId" required placeholder="Course Code *" />
          <input type="text" name="courseName" required placeholder="Course Name *" />
          <input type="number" name="courseFee" required placeholder="Course Fee *"/>
          <label htmlFor="sem">Semester:</label>
          <select id="sem" name="sem">
            <optgroup label="Sem">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
            </optgroup>
          </select>
        </fieldset>
        <fieldset>
          <legend>
            <span className="number">2</span> Additional Info
          </legend>
          <input type="text" name="instructorId" placeholder="InstructorId"/>
          <input type="number" name="seats" placeholder="Seats"/>
        </fieldset>
        <input type="submit" value="Add New Course" />
      </form>
    </div>
  );
}
