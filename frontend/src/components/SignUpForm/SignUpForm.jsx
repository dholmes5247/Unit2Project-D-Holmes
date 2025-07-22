import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth'; // Custom auth hook

export default function SignUpForm() {
  // Initialize form state with empty strings (avoids uncontrolled input warnings)
  const [form, setForm] = useState({
    name: '',
    email: '',
    username: '',
    password: '',
    school: ''
  });

  const [err, setErr] = useState(''); // Holds any error messages
  const { signup } = useAuth();       // Signup function from auth context
  const navigate = useNavigate();     // Used to redirect after signup

  // Handle input changes and update form state
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // Submit signup form to backend
  const handleSubmit = async (e) => {
    e.preventDefault();
    setErr(''); // Clear existing errors
    try {
      await signup(form);    // Call signup from auth context
      navigate('/login');    // Redirect to login after success
    } catch (error) {
      setErr(error.message); // Display error from backend
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Sign Up</h2>

      {/* Display error if present */}
      {err && <p style={{ color: 'red' }}>{err}</p>}

      <label>
        Name:
        <input
          name="name"
          value={form.name}
          onChange={handleChange}
          required
        />
      </label>
      <br />

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
      <br />

      <label>
        Username:
        <input
          name="username"
          value={form.username}
          onChange={handleChange}
          required
        />
      </label>
      <br />

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
      <br />

      <label>
        School:
        <input
          name="school"
          value={form.school}
          onChange={handleChange}
          required
        />
      </label>
      <br />

      <button type="submit">Sign Up</button>
    </form>
  );
}



/*import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '/hooks/useAuth';

export default function SignUpForm() {
  const [form, setForm] = useState({
    name: '',
    email: '',
    schoolName: '',
    password: ''
  });
  const [error, setError] = useState('');
  const { signup } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await signup(form);
      navigate('/login');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Sign Up</h2>
      {error && <p className="error">{error}</p>}
      <label>
        Name:
        <input
          name="name"
          value={form.name}
          onChange={handleChange}
          required
        />
      </label>
      <br />
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
      <br />
      <label>
        School:
        <input
          name="schoolName"
          value={form.schoolName}
          onChange={handleChange}
          required
        />
      </label>
      <br />
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
      <br />
      <button type="submit">Sign Up</button>
    </form>
  );
}
*/