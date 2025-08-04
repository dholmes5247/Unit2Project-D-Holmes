import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/UseAuth"; // Custom auth hook
// will come back to this -import Toast from "../../components/Toast/Toast"; //  Use modular Toast
import "./SignUpForm.css";

export default function SignUpForm() {
  // Initialize form state with empty strings (avoids uncontrolled input warnings)
  const [form, setForm] = useState({
    name: "",
    email: "",
    username: "",
    password: "",
    school: "",
  });

  const [err, setErr] = useState(""); // Holds any error messages
  
  const { signup } = useAuth(); // Signup function from auth context
  const navigate = useNavigate(); // Used to redirect after signup

  // Handle input changes and update form state
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Submit signup form to backend
  const handleSubmit = async (e) => {
    e.preventDefault();
    setErr(""); // Clear existing errors
    try {
      await signup(form); // Call signup from auth context

      // redirect after delay
      
      setTimeout(() => {     
          
        navigate("/login"); // Redirect to login after success
      }, 2500);
      
    } catch (error) {
      setErr(error.message); // Display error from backend
    }
  };

  return (
    <div className="signup-form-container">
      <form onSubmit={handleSubmit}>
        <h2>Sign Up</h2>

        {err && <p className="error">{err}</p>}

        {/* Form Fields */}
        <label>
          Name:
          <input
            name="name"
            value={form.name}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Email:
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Username:
          <input
            name="username"
            value={form.username}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Password:
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          School:
          <input
            name="school"
            value={form.school}
            onChange={handleChange}
            required
          />
        </label>

        <button type="submit">Sign Up</button>
      </form>

      
    </div>
  );
}
