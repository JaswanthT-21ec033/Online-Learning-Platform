import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function StudentDashboard() {
  const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [enrolled, setEnrolled] = useState([]);
  const [view, setView] = useState("courses");
  const [selectedInstructor, setSelectedInstructor] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const header = { headers: { Authorization: `Bearer ${token}` } };

    const getCourseList = async () => {
      try {
        const response = await axios.get("http://localhost:8084/api/course/active", header);
        setCourses(response.data);
      } catch (err) {
        console.error(err);
      }
    };

    const getAssignmentList = async () => {
      try {
        const response = await axios.get("http://localhost:8084/api/assignment/student/all", header);
        setAssignments(response.data);
      } catch (err) {
        console.error(err);
      }
    };

    const getEnrolledList = async () => {
      try {
        const response = await axios.get("http://localhost:8084/api/enrollment/student/all", header);
        setEnrolled(response.data);
      } catch (err) {
        console.error(err);
      }
    };

    getCourseList();
    getAssignmentList();
    getEnrolledList();
  }, []);

  const handleEnroll = async (courseId) => {
    const token = localStorage.getItem("token");
    const header = {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    };

    const enrollmentPayload = {
      enroll_date: new Date().toISOString().split("T")[0],
      status: "PENDING",
    };

    try {
      const response = await axios.post(
        `http://localhost:8084/api/enrollment/assign/${courseId}`,
        enrollmentPayload,
        header
      );
      alert("Enrolled successfully!");
      console.log("Enrollment response:", response.data);

      const enrolledResponse = await axios.get("http://localhost:8084/api/enrollment/student/all", header);
      setEnrolled(enrolledResponse.data);
    } catch (err) {
      console.error("Enrollment failed:", err);
      alert("Enrollment failed. Please try again.");
    }
  };

  return (
    <div className="dashboard-container">
      <h1>Student Dashboard</h1>
      <nav className="dashboard-nav">
        <button
          className={view === "courses" ? "nav-btn active" : "nav-btn"}
          onClick={() => setView("courses")}
        >
          Courses
        </button>
        <button
          className={view === "enrolled" ? "nav-btn active" : "nav-btn"}
          onClick={() => setView("enrolled")}
        >
          Enrolled Courses
        </button>
        <button
          className={view === "assignments" ? "nav-btn active" : "nav-btn"}
          onClick={() => setView("assignments")}
        >
          Assignments
        </button>
        <button className="nav-btn" onClick={() => navigate("/profile")}>Profile</button>
        <button
          className="nav-btn"
          onClick={() => {
            localStorage.removeItem("token");
            navigate("/");
          }}
        >
          Logout
        </button>
      </nav>

      <div className="content-area">
        {view === "courses" && (
          <div className="cards-container">
            {courses.length === 0 && <p>No active courses available.</p>}
            {courses.map((course) => (
              <div className="course-card" key={course.course_id}>
                <h3>{course.title}</h3>
                <p><strong>Description:</strong> {course.description}</p>
                <p><strong>Category:</strong> {course.category}</p>
                <p><strong>Status:</strong> {course.status}</p>
                <p>
                  <strong>Duration:</strong> {course.start_At} to {course.end_At}
                </p>
                <p><strong>Price:</strong> ${course.price}</p>

                <button
                  className="view-btn"
                  onClick={() => {
                    console.log("Instructor clicked:", course.instructor);
                    setSelectedInstructor(course.instructor || null);
                  }}
                >
                  View Instructor
                </button>
                <button
                  className="enroll-btn"
                  onClick={() => handleEnroll(course.course_id)}
                >
                  Enroll
                </button>
              </div>
            ))}
          </div>
        )}

        {view === "enrolled" && (
          <div className="cards-container">
            {enrolled.length === 0 && <p>No enrolled courses found.</p>}
            {enrolled.map((enroll) => (
              <div className="course-card" key={enroll.enroll_id}>
                <h3>{enroll.course.title}</h3>
                <p><b>Description:</b> {enroll.course.description}</p>
                <p><b>Enrolled Date:</b> {enroll.enroll_date}</p>
                <p><b>Status:</b> {enroll.status}</p>
                <p><b>Instructor:</b> {enroll.course?.instructor?.name || "N/A"}</p>
                <p><b>Bio:</b> {enroll.course?.instructor?.bio || "N/A"}</p>
              </div>
            ))}
          </div>
        )}

        {view === "assignments" && (
          <div className="assignments-list">
            {assignments.length === 0 && <p>No assignments available.</p>}
            {assignments.map((a) => (
              <div key={a.assign_id} className="assignment-item">
                <h4>{a.title}</h4>
                <p><b>Description:</b> {a.description}</p>
                <p><b>Course:</b> {a.course?.title}</p>
                <p><b>Instructor:</b> {a.course?.instructor?.name}</p>
                <p><b>Created Date:</b> {a.created_date}</p>
                <p><b>Due Date:</b> {a.due_date}</p>
                <p><b>Grade:</b> {a.grade}</p>
              </div>
            ))}
          </div>
        )}
      </div>

      {selectedInstructor && (
        <div
          className="modal-overlay"
          onClick={() => setSelectedInstructor(null)}
        >
          <div
            className="modal-content"
            onClick={(e) => e.stopPropagation()}
          >
            <h2>Instructor Details</h2>
            <p><strong>Name:</strong> {selectedInstructor.name || "N/A"}</p>
            <p><strong>Email:</strong> {selectedInstructor.email || "N/A"}</p>
            <p><strong>Joined At:</strong> {selectedInstructor.joined_At || "N/A"}</p>
            <p><strong>Bio:</strong> {selectedInstructor.bio || "N/A"}</p>
            <button
              className="close-btn"
              onClick={() => setSelectedInstructor(null)}
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default StudentDashboard;
