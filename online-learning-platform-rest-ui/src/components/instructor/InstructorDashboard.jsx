import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function InstructorDashboard() {
  const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [instructorCourses, setInstructorCourses] = useState([]);

  const [view, setView] = useState("courses");
  const [filterStatus, setFilterStatus] = useState("all");
  const [searchName, setSearchName] = useState("");
  const [editCourseId, setEditCourseId] = useState(null);
  const [editAssignId, setEditAssignId] = useState(null);
  const [assignId, setAssignId] = useState(null);
  const [message, setMessage] = useState('');

  const [assignment, setAssignment] = useState({ title: "", description: "", created_date: "", due_date: "", grade: "" });
  const [editAssignmentForm, setEditAssignmentForm] = useState({ title: "", description: "", created_date: "", due_date: "", grade: "" });
  const [newAssignment, setNewAssignment] = useState({ title: "", description: "", created_date: "", due_date: "", grade: "" });
  const [showAddForm, setShowAddForm] = useState(false);
  const [editForm, setEditForm] = useState({
    title: "",
    description: "",
    price: "",
    start_At: "",
    end_At: "",
    status: "",
    category: "",
  });

  useEffect(() => {
    const token = localStorage.getItem("token");
    const header = { headers: { Authorization: `Bearer ${token}` } };

    const fetchCourses = async () => {
      try {
        const res = await axios.get("http://localhost:8084/api/course/all", header);
        setCourses(res.data);
      } catch (err) {
        console.error("Error fetching courses:", err);
      }
    };

    const fetchAssignments = async () => {
      try {
        const res = await axios.get("http://localhost:8084/api/assignment/all", header);
        setAssignments(res.data);
      } catch (err) {
        console.error("Error fetching assignments:", err);
      }
    };

    const fetchInstructorCourses = async () => {
      try {
        const res = await axios.get("http://localhost:8084/api/course/instructor/all", header);
        setInstructorCourses(res.data);
      } catch (err) {
        console.error("Error fetching instructor courses:", err);
      }
    };


    fetchCourses();
    fetchAssignments();
    fetchInstructorCourses();
  }, []);

  const handleDelete = async () => {
    if (!assignId) {
      setMessage('Please enter a valid Assignment ID');
      return;
    }

    try {
      const response = await axios.delete(`http://localhost:8084/deletebyassignid/${assignId}`);
      setMessage(response.data);
      const res = await axios.get("http://localhost:8084/api/assignment/all", header);
      setAssignments(res.data);
    } catch (error) {
      console.error('Delete error:', error);
      setMessage('Failed to delete assignment');
    }
  };
  const handleEditCourse = (courseId) => {
    const course = instructorCourses.find((c) => c.course_id === courseId);
    if (course) {
      setEditForm({
        title: course.title,
        description: course.description,
        price: course.price,
        category: course.category,
        status: course.status,
        start_At: course.start_At,
        end_At: course.end_At,
      });
      setEditCourseId(courseId);
    }
  };

  const handleEditSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const header = { headers: { Authorization: `Bearer ${token}` } };

    try {
      await axios.put(`http://localhost:8084/api/course/update/${editCourseId}`, editForm, header);
      const res = await axios.get("http://localhost:8084/api/course/instructor/all", header);
      setInstructorCourses(res.data);
      setEditCourseId(null);
      alert("Course updated successfully.");
    } catch (err) {
      console.error("Update failed:", err);
      alert("Failed to update course.");
    }
  };



  const handleEnrollCourse = async (courseId) => {
    const token = localStorage.getItem("token");
    const header = { headers: { Authorization: `Bearer ${token}` } };

    try {
      const response = await axios.post(`http://localhost:8084/api/course/assign/${courseId}`, {}, header);
      alert("Enrolled successfully!");


      const res = await axios.get("http://localhost:8084/api/course/instructor/all", header);
      setInstructorCourses(res.data);
    } catch (error) {
      console.error("Enrollment failed:", error);
      alert("Failed to enroll. Please try again.");
    }
  };



  const handleUnassignCourse = async (courseId) => {
    const token = localStorage.getItem("token");
    const header = { headers: { Authorization: `Bearer ${token}` } };

    try {
      await axios.put(`http://localhost:8084/api/course/unassign/${courseId}`, {}, header);

      const res = await axios.get("http://localhost:8084/api/course/instructor/all", header);
      setInstructorCourses(res.data);
      alert("Course unassigned successfully.");
    } catch (error) {
      console.error("Unassignment failed:", error);
      alert("Failed to unassign course. Please try again.");
    }
  };

  const headers = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`
    }
  };

  const handleEditAssignment = (assignId) => {
    const assign = assignments.find(a => a.assignid === assignId);
    if (assign) {
      setEditAssignmentForm({ ...assign });
      setEditAssignId(assignId);
    }
  };

  const handleAssignmentUpdate = async (e) => {
    e.preventDefault();
    try {
      await axios.put(
        `http://localhost:8084/api/assignment/update/${editAssignId}`,
        editAssignmentForm,
        headers
      );
      const res = await axios.get("http://localhost:8084/api/assignment/all", headers);
      setAssignments(res.data);
      setEditAssignId(null);
      alert("Assignment updated successfully.");
    } catch (err) {
      console.error("Assignment update failed:", err);
      alert("Failed to update assignment.");
    }
  };

  const handleAddAssignment = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8084/api/assignment/add", newAssignment, headers);
      setShowAddForm(false);
      setNewAssignment({
        title: "",
        description: "",
        created_date: "",
        due_date: "",
        grade: ""
      });
      const res = await axios.get("http://localhost:8084/api/assignment/all", headers);
      setAssignments(res.data);
      alert("Assignment added successfully!");
    } catch (error) {
      console.error("Failed to add assignment:", error);
      alert("Failed to add assignment.");
    }
  };


  const filteredCourses = courses.filter((course) => {
    const matchesStatus = filterStatus === "all" || course.status.toLowerCase() === filterStatus.toLowerCase();
    const matchesName = course.title?.toLowerCase().includes(searchName.toLowerCase());
    return matchesStatus && matchesName;
  });

  return (
    <div className="dashboard-container">
      <h1>Instructor Dashboard</h1>
      <nav className="dashboard-nav">
        <button className={view === "courses" ? "nav-btn active" : "nav-btn"} onClick={() => setView("courses")}>Courses</button>
        <button className={view === "enrolled" ? "nav-btn active" : "nav-btn"} onClick={() => setView("enrolled")}>Enrolled Courses</button>
        <button className={view === "assignments" ? "nav-btn active" : "nav-btn"} onClick={() => setView("assignments")}>Assignments</button>
        <button className="nav-btn" onClick={() => navigate("/profile")}>Profile</button>
        <button className="nav-btn" onClick={() => navigate("/")}>Logout</button>
      </nav>

      {view === "courses" && (
        <div>
          <div className="filter-container" style={{ display: 'flex', gap: '20px', marginBottom: '20px' }}>
            <label className="form-label">
              Filter by Status:
              <select className="form-control" value={filterStatus} onChange={(e) => setFilterStatus(e.target.value)}>
                <option value="all">All</option>
                <option value="active">Active</option>
                <option value="inactive">Inactive</option>
              </select>
            </label>
            <label className="form-label">
              Search by Name:
              <input className="form-control" type="text" value={searchName} onChange={(e) => setSearchName(e.target.value)} />
            </label>
          </div>
          <div className="cards-container">
            {filteredCourses.length === 0 ? (
              <p>No courses available.</p>
            ) : (
              filteredCourses.map((course) => (
                <div key={course.course_id} className="course-card">
                  <h2>{course.title}</h2>
                  <p><strong>Description:</strong> {course.description}</p>
                  <p><strong>Status:</strong> {course.status}</p>
                  <p><strong>Category:</strong> {course.category}</p>
                  <p><strong>Price:</strong> ${course.price?.toFixed(2) || "0.00"}</p>
                  <p><strong>Start Date:</strong> {course.start_At}</p>
                  <p><strong>End Date:</strong> {course.end_At}</p>
                  <button className="enroll-btn" onClick={() => handleEnrollCourse(course.course_id)}>Enroll</button>
                </div>
              ))
            )}
          </div>
        </div>
      )}

      {view === "enrolled" && (
        <div className="cards-container">
          {instructorCourses.length === 0 ? (
            <p>No courses assigned to you.</p>
          ) : (
            instructorCourses.map((course) => (
              <div key={course.course_id} className="course-card">
                <h2>{course.title}</h2>
                <p><strong>Description:</strong> {course.description}</p>
                <p><strong>Status:</strong> {course.status}</p>
                <p><strong>Category:</strong> {course.category}</p>
                <p><strong>Price:</strong> ${course.price?.toFixed(2) || "0.00"}</p>
                <p><strong>Start Date:</strong> {course.start_At}</p>
                <p><strong>End Date:</strong> {course.end_At}</p>

                {editCourseId === course.course_id ? (
                  <form onSubmit={handleEditSubmit} className="edit-form">
                    <input
                      className="form-control"
                      type="text"
                      value={editForm.title}
                      onChange={(e) => setEditForm({ ...editForm, title: e.target.value })}
                      required
                      placeholder="Title"
                    />
                    <textarea
                      className="form-control"
                      value={editForm.description}
                      onChange={(e) => setEditForm({ ...editForm, description: e.target.value })}
                      required
                      placeholder="Description"
                    />
                    <input
                      className="form-control"
                      type="number"
                      value={editForm.price}
                      onChange={(e) => setEditForm({ ...editForm, price: e.target.value })}
                      required
                      placeholder="Price"
                      step="0.01"
                      min="0"
                    />
                    <input
                      className="form-control"
                      type="date"
                      value={editForm.start_At}
                      onChange={(e) => setEditForm({ ...editForm, start_At: e.target.value })}
                      required
                    />
                    <input
                      className="form-control"
                      type="date"
                      value={editForm.end_At}
                      onChange={(e) => setEditForm({ ...editForm, end_At: e.target.value })}
                      required
                    />
                    <select
                      className="form-control"
                      value={editForm.status}
                      onChange={(e) => setEditForm({ ...editForm, status: e.target.value })}
                      required
                    >
                      <option value="ACTIVE">ACTIVE</option>
                      <option value="INACTIVE">INACTIVE</option>
                    </select>
                    <select
                      className="form-control"
                      value={editForm.category}
                      onChange={(e) => setEditForm({ ...editForm, category: e.target.value })}
                      required
                    >
                      <option value="" disabled>Select Category</option>
                      <option value="PROGRAMMING">Programming</option>
                      <option value="APTITUDE">Aptitude</option>
                      <option value="COMMUNICATION">Communication</option>
                      <option value="GENERAL">General</option>
                    </select>

                    <div className="button-group" style={{ display: 'flex', gap: '10px', marginTop: '12px' }}>
                      <button className="enroll-btn" type="submit">Save</button>
                      <button className="view-btn" type="button" onClick={() => setEditCourseId(null)}>Cancel</button>
                    </div>
                  </form>
                ) : (
                  <div className="button-group" style={{ display: 'flex', gap: '10px', marginTop: '12px' }}>
                    <button className="view-btn" onClick={() => handleEditCourse(course.course_id)}>Edit</button>
                    <button className="enroll-btn" onClick={() => handleUnassignCourse(course.course_id)}>Unenroll</button>
                  </div>
                )}
              </div>
            ))
          )}
        </div>
      )}

      {view === "assignments" && (
        <div className="assignments-list">
          <button
            className="view-btn toggle-btn"
            onClick={() => setShowAddForm(!showAddForm)}
          >
            {showAddForm ? "Close Form" : "Add Assignment"}
          </button>

          {showAddForm && (
            <form onSubmit={handleAddAssignment} className="course-card">
              <h3>Add Assignment</h3>
              <input
                className="form-control"
                type="text"
                name="title"
                value={newAssignment.title}
                onChange={(e) =>
                  setNewAssignment({ ...newAssignment, title: e.target.value })
                }
                required
                placeholder="Title"
              />
              <textarea
                className="form-control"
                name="description"
                value={newAssignment.description}
                onChange={(e) =>
                  setNewAssignment({ ...newAssignment, description: e.target.value })
                }
                required
                placeholder="Description"
              />
              <input
                className="form-control"
                type="date"
                name="created_date"
                value={newAssignment.created_date}
                onChange={(e) =>
                  setNewAssignment({
                    ...newAssignment,
                    created_date: e.target.value,
                  })
                }
                required
              />
              <input
                className="form-control"
                type="date"
                name="due_date"
                value={newAssignment.due_date}
                onChange={(e) =>
                  setNewAssignment({ ...newAssignment, due_date: e.target.value })
                }
                required
              />
              <select
                className="form-control"
                name="grade"
                value={newAssignment.grade}
                onChange={(e) =>
                  setNewAssignment({ ...newAssignment, grade: e.target.value })
                }
                required
              >
                <option value="" disabled>
                  Select Grade
                </option>
                <option value="PENDING">Pending</option>
                <option value="GOOD">Good</option>
                <option value="SATISFACTORY">Satisfactory</option>
                <option value="BAD">Bad</option>
              </select>
              <button className="enroll-btn" type="submit">
                Add
              </button>
            </form>
          )}

          <div className="cards-container">
            {assignments.length === 0 ? (
              <p>No assignments available.</p>
            ) : (
              assignments.map((assign) => (
                <div key={assign.assignid} className="course-card">
                  <h3>{assign.title}</h3>
                  <p>
                    <strong>Description:</strong> {assign.description}
                  </p>
                  <p>
                    <strong>Created:</strong> {assign.created_date}
                  </p>
                  <p>
                    <strong>Due:</strong> {assign.due_date}
                  </p>
                  <p>
                    <strong>Grade:</strong>{" "}
                    <span className={`badge badge-${assign.grade.toLowerCase()}`}>
                      {assign.grade}
                    </span>
                  </p>

                  {editAssignId === assign.assignid ? (
                    <form onSubmit={handleAssignmentUpdate}>
                      <input
                        className="form-control"
                        type="text"
                        value={editAssignmentForm.title}
                        onChange={(e) =>
                          setEditAssignmentForm({
                            ...editAssignmentForm,
                            title: e.target.value,
                          })
                        }
                        required
                        placeholder="Title"
                      />
                      <textarea
                        className="form-control"
                        value={editAssignmentForm.description}
                        onChange={(e) =>
                          setEditAssignmentForm({
                            ...editAssignmentForm,
                            description: e.target.value,
                          })
                        }
                        required
                        placeholder="Description"
                      />
                      <input
                        className="form-control"
                        type="date"
                        value={editAssignmentForm.created_date}
                        onChange={(e) =>
                          setEditAssignmentForm({
                            ...editAssignmentForm,
                            created_date: e.target.value,
                          })
                        }
                        required
                      />
                      <input
                        className="form-control"
                        type="date"
                        value={editAssignmentForm.due_date}
                        onChange={(e) =>
                          setEditAssignmentForm({
                            ...editAssignmentForm,
                            due_date: e.target.value,
                          })
                        }
                        required
                      />
                      <select
                        className="form-control"
                        value={editAssignmentForm.grade}
                        onChange={(e) =>
                          setEditAssignmentForm({
                            ...editAssignmentForm,
                            grade: e.target.value,
                          })
                        }
                        required
                      >
                        <option value="" disabled>
                          Select Grade
                        </option>
                        <option value="PENDING">Pending</option>
                        <option value="GOOD">Good</option>
                        <option value="SATISFACTORY">Satisfactory</option>
                        <option value="BAD">Bad</option>
                      </select>
                      <div className="dashboard-nav">
                        <button className="view-btn" type="submit">
                          Save
                        </button>
                        <button
                          className="enroll-btn"
                          type="button"
                          onClick={() => setEditAssignId(null)}
                        >
                          Cancel
                        </button>
                      </div>
                    </form>
                  ) : (
                    <div className="dashboard-nav">
                      <button
                        className="view-btn"
                        onClick={() => handleEditAssignment(assign)}
                      >
                        Edit
                      </button>
                      <button onClick={() => handleDelete(assign.assignid)} className="delete-btn">
                        Delete Assignment
                      </button>

                    </div>
                  )}
                </div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
}



export default InstructorDashboard;
