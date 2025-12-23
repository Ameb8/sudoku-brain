import { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Home from "./pages/Home";
import QuickPlay from "./pages/QuickPlay";
import LeaderBoardPage from "./pages/LeaderBoardPage.jsx";
import DailyChallenge from "./pages/DailyChallenge";
import ProfilePage from "./pages/ProfilePage.jsx";
import AttemptedPuzzles from "./pages/AttemptedPuzzles.jsx";
import PlayPuzzle from "./pages/PlayPuzzle.jsx";
import Navbar from "./components/Navbar.jsx";

import {UserProvider} from "./components/UserProvider.jsx";

import './App.css'

function App() {
    const [theme, setTheme] = useState(
        localStorage.getItem("theme") || "light"
    );

    useEffect(() => {
        document.documentElement.setAttribute("data-theme", theme);
        localStorage.setItem("theme", theme);
    }, [theme]);

    return (
        <UserProvider>
            <Router>
                <Navbar />

                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/QuickPlay" element={<QuickPlay />} />
                    <Route path="/LeaderBoardPage" element={<LeaderBoardPage />} />
                    <Route path="/AttemptedPuzzles" element={<AttemptedPuzzles />} />
                    <Route path="/PlayPuzzle" element={<PlayPuzzle />} />
                    <Route path="/DailyChallenge" element={<DailyChallenge />} />
                    <Route
                        path="/Profile"
                        element={
                            <ProfilePage
                                theme={theme}
                                setTheme={setTheme}
                            />
                        }
                    />
                </Routes>
            </Router>
        </UserProvider>
    );
}

export default App
