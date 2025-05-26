


import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router";
import fetchProfile from "../Store/Actions/profileAction";

function Profile() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const profileData = useSelector((state) => state.profile.profile);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        contact: ""
    });

    useEffect(() => {
        dispatch(fetchProfile());
    }, [dispatch]);

    useEffect(() => {
        if (profileData) {
            setFormData({
                name: profileData.name || "",
                email: profileData.email || "",
                contact: profileData.contact ? profileData.contact.toString() : ""
            });
        }
    }, [profileData]);

    return (
        <div className="login-container" style={{ background: "linear-gradient(to right, #46a145, #f1f8e9)" }}>
            <div className="card">
                <div className="card-body">
                    <h3>User Profile</h3>
                    <p className="text-muted">Welcome, {profileData?.name ?? "User"}</p>

                    <div style={{ marginBottom: "15px" }}>
                        <p><strong>Name:</strong> {profileData?.name ?? "N/A"}</p>
                        <p><strong>Email:</strong> {profileData?.email ?? "N/A"}</p>
                        <p><strong>Contact:</strong> {profileData?.contact ?? "N/A"}</p>
                    </div>

                    

                    <button className="enroll-btn" onClick={() => navigate("/instructor")}>
                        Back to Dashboard
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Profile;

