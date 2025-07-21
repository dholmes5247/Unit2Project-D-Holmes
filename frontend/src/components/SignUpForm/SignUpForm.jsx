
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from "../../hooks/useAuth";

export default function SignUpForm() {
  const [form, setForm] = useState({
    name: '',
    username: '',
    email: '',
    schoolName: '',
    password: ''
  });
  const [err, setErr] = useState('');
  const { signup } = useAuth();
  const nav = useNavigate();

  const handle = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    try {
      await signup(form);
      nav('/login');
    } catch (error) {
      setErr(error.message);
    }
  };

  return (
    <form onSubmit={submit}>
      <h2>Sign Up</h2>
      {err && <p style={{ color: 'red' }}>{err}</p>}
      <label>
        Name
        <input name="name" required value={form.name} onChange={handle} />
      </label>
      <br />
      <label>
        Email
        <input
          name="email"
          type="email"
          required
          value={form.email}
          onChange={handle}
        />
      </label>
      <br />
      <label>
        School
        <input
          name="school"
          required
          value={form.school}
          onChange={handle}
        />
      </label>
            <br />
      <label>
        Username
        <input
          name="username"
          required
          value={form.username}
          onChange={handle}
        />
      </label>
      <br />
      <label>
        Password
        <input
          name="password"
          type="password"
          required
          value={form.password}
          onChange={handle}
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