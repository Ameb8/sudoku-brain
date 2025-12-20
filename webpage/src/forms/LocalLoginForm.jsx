import { useState } from 'react';

import { Eye, EyeSlash } from 'react-bootstrap-icons'; // Bootstrap icons

import { useUser } from '../components/UserProvider.jsx';

const LocalLoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const { setUser } = useUser();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try { // Attempt login using form data
            const loginResponse = await fetch(
                'http://localhost:8080/api/login',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        username,
                        password,
                    }),
                    credentials: 'include',
                }
            );

            if (!loginResponse.ok) {
                throw new Error('Login failed');
            }

            // Fetch user data and update context
            const meResponse = await fetch('http://localhost:8080/api/users/secured/me', {
                credentials: 'include',
            });
            if (!meResponse.ok) throw new Error('Failed to fetch user data');

            const userData = await meResponse.json();
            setUser(userData);

            window.location.href = '/'; // Return to home

        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };


    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleSubmit(e);
        }
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
            <form
                className="p-4 shadow rounded bg-white"
                style={{ width: '350px' }}
                onSubmit={handleSubmit}
            >
                <h3 className="mb-3 text-center">Register</h3>

                {error && <div className="alert alert-danger">{error}</div>}

                <div className="mb-3">
                    <label className="form-label">Username</label>
                    <input
                        type="text"
                        className="form-control"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        onKeyDown={handleKeyDown}
                        required
                    />
                </div>

                <div className="mb-3 position-relative">
                    <label className="form-label">Password</label>
                    <input
                        type={showPassword ? 'text' : 'password'}
                        className="form-control"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onKeyDown={handleKeyDown}
                        required
                    />
                    <span
                        className="position-absolute top-50 end-0 translate-middle-y me-3"
                        style={{ cursor: 'pointer' }}
                        onClick={() => setShowPassword(!showPassword)}
                    >
                        {showPassword ? <EyeSlash /> : <Eye />}
                    </span>
                </div>

                <button
                    type="submit"
                    className="btn btn-primary w-100"
                    disabled={loading}
                >
                    {loading ? 'Loading...' : 'Register'}
                </button>
            </form>
        </div>
    );
};

export default LocalLoginForm;
