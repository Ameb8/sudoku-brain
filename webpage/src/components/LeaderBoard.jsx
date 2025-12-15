import { useState, useEffect } from "react";
import { Container, Table, Alert, Spinner, Row, Col } from "react-bootstrap";
import PageSizeSelector from "./PageSizeSelector";
import PageSelector from "./PageSelector";
import Leader from "../models/Leader";
import "./LeaderBoard.css";

const Leaderboard = () => {
    const [leaders, setLeaders] = useState([]);
    const [pageSize, setPageSize] = useState(10);
    const [currentPage, setCurrentPage] = useState(1);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchLeaders = async (page, size) => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await fetch(
                `http://localhost:8080/api/users/leaderboard/${page - 1}/${size}`
            );
            if (!response.ok) throw new Error(`Error: ${response.status}`);
            const data = await response.json();
            setLeaders(
                data.map(
                    (item) =>
                        new Leader(
                            item.username,
                            item.puzzlesSolved,
                            item.avgSolveTime,
                            item.avgHintsUsed
                        )
                )
            );
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchLeaders(currentPage, pageSize);
    }, [currentPage, pageSize]);

    return (
        <Container className="leaderboard-container py-4">
            <h2 className="leaderboard-title text-center mb-4">
                🏆 Leaderboard
            </h2>

            {isLoading && <Spinner animation="border" className="d-block mx-auto" />}

            {error && (
                <Alert variant="danger" className="text-center">
                    {error}
                </Alert>
            )}

            {!isLoading && !error && (
                <div className="leaderboard-card">
                    <Table responsive className="leaderboard-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Player</th>
                                <th>Puzzles</th>
                                <th>Avg Time</th>
                                <th>Hints</th>
                            </tr>
                        </thead>
                        <tbody>
                            {leaders.length === 0 ? (
                                <tr>
                                    <td colSpan="5" className="text-center py-4">
                                        No data available
                                    </td>
                                </tr>
                            ) : (
                                leaders.map((leader, index) => (
                                    <tr key={index}>
                                        <td className="rank">
                                            {(currentPage - 1) * pageSize + index + 1}
                                        </td>
                                        <td className="username">{leader.username}</td>
                                        <td>{leader.puzzlesSolved}</td>
                                        <td>{leader.avgSolveTime}</td>
                                        <td>{leader.avgHintsUsed}</td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </Table>

                    <Row className="mt-3 align-items-center">
                        <Col md={6}>
                            <PageSizeSelector onChange={setPageSize} />
                        </Col>
                        <Col md={6} className="text-end">
                            <PageSelector
                                pageSize={pageSize}
                                onPageChange={setCurrentPage}
                            />
                        </Col>
                    </Row>
                </div>
            )}
        </Container>
    );
};

export default Leaderboard;

