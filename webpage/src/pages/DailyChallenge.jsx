import { useEffect, useState } from "react";
import Header from "../components/Header";
import Board from "../board/Board.jsx";
import PuzzleMetricsBanner from "../components/PuzzleMetricsBanner";
import axios from "axios";
import PuzzleMetrics from "../models/PuzzleMetrics"; // Import the PuzzleMetrics class
import LoginButton from "../components/LoginButton.jsx";
import Navbar from "../components/Navbar.jsx";

function DailyChallenge() {
    const [puzzle, setPuzzle] = useState(null); // State to store the puzzle
    const [puzzleAttempt, setPuzzleAttempt] = useState(null); // State to store daily challenge attempt
    const [puzzleMetrics, setPuzzleMetrics] = useState(null); // State to store the PuzzleMetrics object
    const [loading, setLoading] = useState(true); // State for loading indicator
    const [error, setError] = useState(null); // State for error handling

    // Fetch random puzzle from the API when the component mounts
    useEffect(() => {
        const fetchPuzzleAndMetrics = async () => {
            try {
                const puzzleResponse = await axios.get("http://localhost:8080/daily-challenge/play", {
                    withCredentials: true,
                });
                const puzzleData = puzzleResponse.data;

                console.log("Full puzzle API response:", puzzleData)


                /*
                const puzzle = puzzleData.dailyPuzzle.puzzle;
                setPuzzleAttempt(puzzleData.dailyAttempt.puzzleAttempt);

                const newPuzzle = {
                    puzzleId: puzzle.puzzleId,
                    puzzleVals: puzzle.puzzleVals,
                    solutionVals: puzzle.solutionVals,
                    difficulty: puzzle.difficulty,
                };
                setPuzzle(newPuzzle);
                */

                const puzzle = puzzleData.dailyPuzzle.puzzle;

                setPuzzle({
                    puzzleId: puzzle.puzzleId,
                    puzzleVals: puzzle.puzzleVals,
                    solutionVals: puzzle.solutionVals,
                    difficulty: puzzle.difficulty,
                });

                setPuzzleAttempt(
                    puzzleData.dailyAttempt?.puzzleAttempt || null
                );

                const metrics = await PuzzleMetrics.fetchMetrics(puzzle.puzzleId)

                setPuzzleMetrics(metrics);
                setLoading(false);

                //const userPuzzle = puzzleData.dailyAttempt.puzzleAttempt;
            } catch (err) {
                console.log("Failed to load metrics...");
                setError("Failed to fetch data");
                setLoading(false);
            }
        };

        fetchPuzzleAndMetrics();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!puzzle) {
        return <div>No puzzle data found.</div>;
    }

    return (
        <div>
            <Navbar />
            <Board puzzle={puzzle} userPuzzleOverride={puzzleAttempt} />
            {puzzleMetrics && <PuzzleMetricsBanner metrics={puzzleMetrics} difficulty={puzzle.difficulty} />}
        </div>
    );
}

export default DailyChallenge;

