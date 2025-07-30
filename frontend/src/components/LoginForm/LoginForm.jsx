import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { AuthContext } from '../../context/AuthContext'; // Make sure this path matches your setup
import EditProfileForm from '../EditProfileForm/EditProfileForm'; // Adjust path if needed
import './LoginForm.css';




export default function LoginForm() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [err, setErr] = useState('');
  const [showEditForm, setShowEditForm] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const { login } = useAuth();
  // const user = useContext(AuthContext); not using now
  const nav = useNavigate();

  const handle = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const resetForm = () => {
    setErr('');
    setForm({ email: '', password: '' });
  };

  const submit = async (e) => {
    e.preventDefault();
    try {
      await login(form);
      nav('/');
    } catch (error) {
      const message =
        error?.message?.includes('401') || error?.message?.includes('Unauthorized')
          ? 'Incorrect credentials. Please try again or sign up.'
          : 'Login failed. Please try again.';
      setErr(message);
    }
  };

const handleUpdateClick = async () => {
  try {
    const loggedInUser = await login(form); // Assuming login returns user data
    setEditingUser(loggedInUser); // Save user info for prefill
    setShowEditForm(true);
  } catch (error) {
    console.error('Update profile failed:', error); // Uses 'error'
    setErr('Unable to load profile for update. Please double-check credentials.');
  }
};

return (
  <div className="form-wrapper">
  
  <div className="choose-path-panel">
    <h2 className="choose-header">Choose Your Path</h2>
    <p className="choose-subtext">Welcome to the control room. You have options:</p>
    <ul>
  <li>
    <strong>Log In:</strong><br />
    <span>Start your session and access your dashboard</span>
  </li>
  <li>
    <strong>Update Info:</strong><br />
    <span>Edit your email and password if needed</span>
  </li>
  <li>
    <strong>Sign Up:</strong><br />
    <span>Create an account to begin your journey</span>
  </li>
</ul>

  </div>
  <div className="login-form-container">
    <form onSubmit={submit}>
      <h2>Welcome</h2>

      {err && (
        <div className="login-error">
          <p style={{ color: 'red' }}>{err}</p>
          <button type="button" onClick={resetForm} className="retry-button">
            🔁 Try Again
          </button>
        </div>
      )}

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

      <button type="submit">Log In</button>

      <button
        type="button"
        onClick={handleUpdateClick}
        className="update-button"
      >
        ✏️ *Update Info Instead--Click after email and password*
      </button>

      <p>
        Don’t have an account yet?{' '}
        <button
          type="button"
          onClick={() => nav('/signup')}
          className="link-button"
        >
          ✍️ Sign Up Instead
        </button>
      </p>
    </form>

    {/* ✅ Clean conditional */}
    {showEditForm && editingUser && (
      <>
        <p>You can update your profile details below.</p>
        <EditProfileForm user={editingUser} />
      </>
    )}
  </div>
  </div>
);
}