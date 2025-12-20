import "./LoginButton.css";

const LoginButton = () => {
    const handleLogin = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
        <button
            onClick={handleLogin}
            className="google-login-btn d-flex align-items-center gap-2"
        >
            <img
                src="https://developers.google.com/identity/images/g-logo.png"
                alt="Google logo"
                className="google-logo"
            />
            <span>Sign in with Google</span>
        </button>
    );
};

export default LoginButton;
