import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import './SignUpForm.css';

const SignUpForm = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    schoolName: ''
  });

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required.';
    } else if (formData.name.length < 2) {
      newErrors.name = 'Name must be at least 2 characters.';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email is required.';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Email is not valid.';
    }

    if (!formData.schoolName.trim()) {
      newErrors.schoolName = 'School Name is required.';
    } else if (formData.schoolName.length < 3) {
      newErrors.schoolName = 'School Name must have at least 3 letters.';
    } else if (formData.schoolName.length > 20) {
      newErrors.schoolName = 'School Name must be no more than 15 characters.';
    }

    return newErrors;
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: '' });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      login(formData.name, formData.schoolName);
      navigate('/quiz');
    }
  };

  return (
    <div className="signup-form-container">
      <form className="signup-form" onSubmit={handleSubmit} noValidate>
        <h2>Sign In</h2>

        <div className="form-group">
          <input
            type="text"
            name="name"
            placeholder="Name"
            value={formData.name}
            onChange={handleChange}
          />
          {errors.name && <span className="error">{errors.name}</span>}
        </div>

        <div className="form-group">
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>

        <div className="form-group">
          <input
            type="text"
            name="schoolName"
            placeholder="Name of School"
            value={formData.schoolName}
            onChange={handleChange}
          />
          {errors.schoolName && <span className="error">{errors.schoolName}</span>}
        </div>

        <button type="submit" className="submit-button">Sign In</button>
      </form>
    </div>
  );
};

export default SignUpForm;




